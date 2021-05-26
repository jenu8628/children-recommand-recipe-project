import neispy
import asyncio
import sys
import pandas as pd
import re
from typing import Awaitable

from sqlalchemy import create_engine
import pymysql
import pandas as pd
import math
from operator import itemgetter
from scipy.spatial import distance
from sklearn.metrics import mean_squared_error
from sklearn.metrics import mean_absolute_error

from .db_connector import connect_db

def getAllChild():
    conn = connect_db()
    children = pd.read_sql('SELECT * FROM child', conn)
    children = children[['child_id']]
    return children

def getAllRecipe():
    conn = connect_db()
    recipes = pd.read_sql('SELECT * FROM recipe', conn)
    return recipes

def mergeRecipeRatingChild():
    conn = connect_db()
    cursor = conn.cursor()
    ratings = pd.read_sql('SELECT * FROM reciperating', conn)
    ratings = ratings[['recipeRating_recipeId', 'recipeRating_childId', 'recipeRating_rating']]
    ratings.rename(columns={'recipeRating_recipeId' : 'recipe_id', 'recipeRating_childId':'child_id', 'recipeRating_rating' : 'rating'}, inplace=True)
    ratings = ratings.drop_duplicates(['recipe_id', 'child_id'])
    recipes = pd.read_sql('SELECT * FROM recipe', conn)
    recipes = recipes[['recipe_id', 'recipe_name']]
    children = getAllChild()

    tempData =  pd.merge(ratings, recipes, on=['recipe_id'], how='outer')
    tempData = tempData.sort_values(by=['recipe_id'], axis=0)

    tempData = pd.merge(tempData, children, on=['child_id'], how='outer')
    tempData = tempData.sort_values(by=['child_id'], axis=0)

    user_num = tempData.child_id.unique().shape[0]
    recipe_num = tempData.recipe_id.unique().shape[0]
    #print("유저의 수는 {}명 이고, \n레시피의 수는 {}개 입니다.".format(user_num, recipe_num))
    conn.close()
    return tempData

#### 피봇 테이블 생성
def um_matrix_ds():
    ratings = mergeRecipeRatingChild()
    UM_matrix_ds = ratings.pivot(index='child_id', columns='recipe_id', values='rating')
    return UM_matrix_ds

def distance_cosine(a, b) :
       return 1-distance.cosine(a, b)

def distance_correlation(a, b) :
       return 1-distance.correlation(a, b)

def distance_euclidean(a, b) :
       return 1/(1+distance.euclidean(a, b))

def nearest_neighbor_user(user, topN, simFunc) :
    UM_matrix_ds = um_matrix_ds()
    u1  =  UM_matrix_ds.loc[user].dropna()
    ratedIndex = u1.index
    nn = { }

    for uid, row in UM_matrix_ds.iterrows():
        interSectionU1 = [ ]
        interSectionU2 = [ ]
        if uid == user: continue

        for i in ratedIndex:
            if not math.isnan(row[i]):
                interSectionU1.append(u1[i])
                interSectionU2.append(row[i])
        interSectionLen = len(interSectionU1)

        if interSectionLen < 3: continue
        sim = simFunc(interSectionU1, interSectionU2)

        if not math.isnan(sim):nn[uid] = sim

    return sorted(nn.items(), key=itemgetter(1))[:-(topN+1):-1]

def predict_rating(userid, nn=100, simFunc=distance_cosine):
    UM_matrix_ds = um_matrix_ds()
    neighbor = nearest_neighbor_user(userid, nn, simFunc)
    neighbor_id = [id for id, sim in neighbor]

    neighbor_recipe = UM_matrix_ds.loc[neighbor_id].dropna(1, how='all', thresh=1)
    neighbor_dict = (dict(neighbor))
    ret = []

    for recipe_id, row in neighbor_recipe.iteritems():
        jsum, wsum = 0, 0
        for v in row.dropna().iteritems():
            sim = neighbor_dict.get(v[0], 0)
            jsum += sim
            wsum += (v[1]*sim)
        ret.append([recipe_id, wsum/jsum])

    return ret

def cosine():
    users = getAllChild()
    ratings = mergeRecipeRatingChild()
    result_list = []
    for i in range(0, len(users)):
        child_id = users.iloc[i].child_id
        predict = predict_rating(child_id, 2, distance_cosine)
        is_users = ratings['child_id'] == child_id
        users_rating = ratings[is_users]
        exist_recipe = users_rating['recipe_id'].tolist()

        for recipe in predict:
            result_list.append([child_id, recipe[0], recipe[1]])

    resultdf = pd.DataFrame(result_list, columns=['child_id','recipe_id','rating'])
    predict = pd.merge(left = ratings[['recipe_id','child_id','rating']], right = resultdf, on=['child_id','recipe_id'])
    return predict

def correlation():
    users = getAllChild()
    ratings = mergeRecipeRatingChild()
    result_list = []
    for i in range(0, len(users)):
        child_id = users.iloc[i].child_id
        predict = predict_rating(child_id, 5, distance_correlation)

        is_users = ratings['child_id'] == child_id
        users_rating = ratings[is_users]
        exist_recipe = users_rating['recipe_id'].tolist()

        for recipe in predict:
            result_list.append([child_id, recipe[0], recipe[1]])

    resultdf = pd.DataFrame(result_list, columns=['child_id','recipe_id','rating'])
    predict = pd.merge(left = ratings[['recipe_id','child_id','rating']], right = resultdf, on=['child_id','recipe_id'])
    return predict

def checkErrorRate(df):
    df['rating_x'] = df['rating_x'].fillna(value=2.5)
    df['rating_y'] = df['rating_y'].fillna(value=2.5)

    realdata_rating = df['rating_x'].tolist()
    resultdata_rating = df['rating_y'].tolist()

    error_rate_absol = mean_absolute_error(realdata_rating, resultdata_rating)
    error_rate_squared = mean_squared_error(realdata_rating, resultdata_rating)

    print("Error Rate(Absolute) : ", error_rate_absol)  # MAE
    print("Error Rate(Squared) : ", error_rate_squared)  # MSE
    print("RMSE : ", math.sqrt(error_rate_squared))  # RMSE


############# recommendation DB에 저장 (ratingFrame: 유사도 구한 프레임, minRating: 레시피 추천에 보여줄 최소 별점 )################
def saveToRecommendationDB(ratingFrame, minRating) :
    recommendationFrame = ratingFrame[ratingFrame['rating_y']>=minRating]
    recommendationFrame = recommendationFrame[['recipe_id', 'child_id']]
    conn = connect_db()
    cursor = conn.cursor()
    ##### recommendation DB 모두 삭제
    sql = "TRUNCATE TABLE recommendation"
    res = cursor.execute(sql)

    #### recommendation DB에 추천 레시피 데이터프레임 삽입
    sql = "INSERT INTO recommendation(recommendation_recipeId, recommendation_childId) VALUES(%s,%s)"
    for row in recommendationFrame.iterrows():
        cursor.execute(sql, (row[1].recipe_id, row[1].child_id))
    conn.commit()
    cursor.close()

############## 해당 사용자의 추천 레시피 아이디 ################
def recommendation(child_id) :
    conn = connect_db()
    ratings = pd.read_sql('SELECT * FROM reciperating', conn)
    recommendationFrame = recommendationRatingFrame[recommendationRatingFrame['child_id']==child_id]
    return recommendationFrame

#predict_frame = cosine()
#predict_frame2 = correlation()

#print(predict_frame)
#saveToRecommendationDB(predict_frame, 3.5)


#recommendationFrame = recommendation(predict_frame, 'child-3', 3)
#recommendationFrameByCorrelation = recommendation(predict_frame2, 'child-3', 4)
#print(len(recommendationFrame))
#print(recommendationFrame)
#checkErrorRate(predict_frame)
#print("===========================================")
#print(len(recommendationFrameByCorrelation))
#print(recommendationFrameByCorrelation)
#checkErrorRate(predict_frame2)

#recipe = getAllRecipe()
#recipes =  pd.merge(left =recommendationFrameByCorrelation[['recipe_id','child_id','rating_y']], right = recipe[['recipe_id', 'recipe_name', 'recipe_ingredient']], on=['recipe_id'], how = "inner")
#print(recipes)