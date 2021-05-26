import neispy
import asyncio
import sys
import pandas as pd
import re
from typing import Awaitable

from sqlalchemy import create_engine
import pymysql
import pandas as pd

async def outer(year, month, school_area, school_name):
    # 년
    # year = '2021'
    # # 월
    # month = '03'
    # # 년월
    montly = year + month
    # # 학교명
    # school_name = "늘봄초등학교"
    API_KEY = '2b86f76d17b34031a867e25e85774fbf'
    # 필수 인자
    # 샘플키로 요청
    neis = neispy.Client(KEY = API_KEY)
    print(school_area, " ", school_name)
    # 학교이름으로 학교정보를 요청
    scinfo = await neis.schoolInfo(SCHUL_NM = school_name, ATPT_OFCDC_SC_CODE=school_area)
    # 교육청코드
    AE = scinfo[0].ATPT_OFCDC_SC_CODE
    # 학교코드
    SE = scinfo[0].SD_SCHUL_CODE 
    # 학교코드와 교육청 코드 -> 날짜 정보에 해당하는 급식 정보 요청
    scmeal = await neis.mealServiceDietInfo(AE, SE, MLSV_YMD = montly)
    return scmeal

def schoolFeeding_db(year, month, school_area, school_name):
    # pymysql을 통해 DB에 연결
    connection = pymysql.connect(
    user='ssafy',
    passwd='ssafy',
    host='localhost',
    db='formychild',
    charset='utf8'
    )
    # 연결된 db로부터 cursor 생성
    cursor = connection.cursor()
    # cursor = connection.cursor(pymysql.cursors.DictCursor)
    df = asyncio.run(school(year, month, school_area, school_name))
    schoolFeeding_df = df.loc[:, [
        'schoolFeeding_id',
        'schoolFeeding_area',
        'schoolFeeding_schoolName',
        'schoolFeeding_date',
        'schoolFeeding_type',
        'schoolFeeding_kcal',
        'schoolFeeding_carbohydrate',
        'schoolFeeding_protein',
        'schoolFeeding_fat',
        'schoolFeeding_vitaminA',
        'schoolFeeding_thiamine',
        'schoolFeeding_riboflavin',
        'schoolFeeding_vitaminC',
        'schoolFeeding_calcium',
        'schoolFeeding_iron']]
    cols = "`,`".join([str(i) for i in schoolFeeding_df.columns.tolist()])
    # sql_id = "SELECT COUNT(*) FROM `schoolFeeding`"
    # max_id = cursor.execute(sql_id)
    # print(max_id)
    sql = "SELECT `schoolFeeding_id` FROM `schoolfeeding` ORDER BY schoolFeeding_id DESC LIMIT 1"
    cursor.execute(sql)
    result = cursor.fetchall()
    if result == ():
        for i, row in schoolFeeding_df.iterrows():
                sql = "INSERT INTO `schoolfeeding` (`" + cols + "`) VALUES ("f'"{row.schoolFeeding_id}", "{school_area}", "{school_name}","{row.schoolFeeding_date}","{row.schoolFeeding_type}","{row.schoolFeeding_kcal}","{row.schoolFeeding_carbohydrate}","{row.schoolFeeding_protein}","{row.schoolFeeding_fat}","{row.schoolFeeding_vitaminA}","{row.schoolFeeding_thiamine}","{row.schoolFeeding_riboflavin}","{row.schoolFeeding_vitaminC}","{row.schoolFeeding_calcium}","{row.schoolFeeding_iron}"'")"
                # execute를 사용해 SQL을 실행
                cursor.execute(sql)
                # 결과를 DB에 반영하기
                connection.commit()
    else:
        for i, row in schoolFeeding_df.iterrows():
            num_id = int(result[0][0][14:]) + i + 1
            schoolFeeding_id = str(num_id).zfill(5)
            sql = "INSERT INTO `schoolfeeding` (`" + cols + "`) VALUES ("f'"schoolFeeding-{schoolFeeding_id}", "{school_area}", "{school_name}","{row.schoolFeeding_date}","{row.schoolFeeding_type}","{row.schoolFeeding_kcal}","{row.schoolFeeding_carbohydrate}","{row.schoolFeeding_protein}","{row.schoolFeeding_fat}","{row.schoolFeeding_vitaminA}","{row.schoolFeeding_thiamine}","{row.schoolFeeding_riboflavin}","{row.schoolFeeding_vitaminC}","{row.schoolFeeding_calcium}","{row.schoolFeeding_iron}"'")"
            # execute를 사용해 SQL을 실행
            cursor.execute(sql)
            # 결과를 DB에 반영하기
            connection.commit()
    # 연결된 DB닫기
    connection.close()
    return

def schoolFeedingDetail_db(year, month, school_area, school_name):
    # pymysql을 통해 DB에 연결
    connection = pymysql.connect(
    user='ssafy',
    passwd='ssafy',
    host='localhost',
    db='formychild',
    charset='utf8'
    )
    # 연결된 db로부터 cursor 생성
    cursor = connection.cursor()
    df = asyncio.run(school(year, month, school_area, school_name))
    schoolFeedingDetail_df = df.loc[:, [
        'schoolFeeding_id',
        'schoolFeedingDetail_foodNo1',
        'schoolFeedingDetail_foodNo1Allergy',
        'schoolFeedingDetail_foodNo2',
        'schoolFeedingDetail_foodNo2Allergy',
        'schoolFeedingDetail_foodNo3',
        'schoolFeedingDetail_foodNo3Allergy',
        'schoolFeedingDetail_foodNo4',
        'schoolFeedingDetail_foodNo4Allergy',
        'schoolFeedingDetail_foodNo5',
        'schoolFeedingDetail_foodNo5Allergy',
        'schoolFeedingDetail_foodNo6',
        'schoolFeedingDetail_foodNo6Allergy',
        'schoolFeedingDetail_foodNo7',
        'schoolFeedingDetail_foodNo7Allergy',
        'schoolFeedingDetail_foodNo8',
        'schoolFeedingDetail_foodNo8Allergy',
        'schoolFeedingDetail_foodNo9',
        'schoolFeedingDetail_foodNo9Allergy',
        'schoolFeedingDetail_foodNo10',
        'schoolFeedingDetail_foodNo10Allergy']]
    cols = "schoolFeedingDetail_id`"
    # cols = ""
    print("=======디테일 df============")
    print(schoolFeedingDetail_df)
    for i in range(len(schoolFeedingDetail_df.columns.tolist())):
      # print(str(schoolFeedingDetail_df.columns.tolist()[i]))
      if i == 0:
        cols += ",`schoolFeedingDetail_schoolFeedingId`"
      elif i == len(schoolFeedingDetail_df.columns.tolist()) - 1:
        cols += ",`" + str(schoolFeedingDetail_df.columns.tolist()[i])
      else:
        cols += ",`" + str(schoolFeedingDetail_df.columns.tolist()[i])+"`"
    sql_detail = "SELECT `schoolFeedingDetail_id` FROM `schoolfeedingdetail` ORDER BY schoolFeedingDetail_id DESC LIMIT 1"
    cursor.execute(sql_detail)
    result_detail = cursor.fetchall()
    sql = "SELECT `schoolFeedingDetail_schoolFeedingId` FROM `schoolfeedingdetail` ORDER BY schoolFeedingDetail_id DESC LIMIT 1"
    cursor.execute(sql)
    result = cursor.fetchall()
    if result == ():
        for i, row in schoolFeedingDetail_df.iterrows():
                schoolFeeding_num = str(i).zfill(5)
                sql = "INSERT INTO `schoolfeedingdetail` (`" + cols + "`) VALUES ("f'"schoolFeedingDetail-{schoolFeeding_num}","{row.schoolFeeding_id}","{row.schoolFeedingDetail_foodNo1}", "{row.schoolFeedingDetail_foodNo1Allergy}","{row.schoolFeedingDetail_foodNo2}", "{row.schoolFeedingDetail_foodNo2Allergy}","{row.schoolFeedingDetail_foodNo3}", "{row.schoolFeedingDetail_foodNo3Allergy}","{row.schoolFeedingDetail_foodNo4}", "{row.schoolFeedingDetail_foodNo4Allergy}","{row.schoolFeedingDetail_foodNo5}", "{row.schoolFeedingDetail_foodNo5Allergy}","{row.schoolFeedingDetail_foodNo6}", "{row.schoolFeedingDetail_foodNo6Allergy}","{row.schoolFeedingDetail_foodNo7}", "{row.schoolFeedingDetail_foodNo7Allergy}","{row.schoolFeedingDetail_foodNo8}", "{row.schoolFeedingDetail_foodNo8Allergy}","{row.schoolFeedingDetail_foodNo9}", "{row.schoolFeedingDetail_foodNo9Allergy}","{row.schoolFeedingDetail_foodNo10}", "{row.schoolFeedingDetail_foodNo10Allergy}"'")"
                 # execute를 사용해 SQL을 실행
                cursor.execute(sql)
                # 결과를 DB에 반영하기
                connection.commit()
    else:
        for i, row in schoolFeedingDetail_df.iterrows():
            detailid_num = int(result_detail[0][0][20:]) + i + 1
            id_num = int(result[0][0][14:]) + i + 1
            schoolFeedingDetail_num = str(detailid_num).zfill(5)
            schoolFeeding_num = str(id_num).zfill(5)
            sql = "INSERT INTO `schoolfeedingdetail` (`" + cols + "`) VALUES ("f'"schoolFeedingDetail-{schoolFeedingDetail_num}","schoolFeeding-{schoolFeeding_num}","{row.schoolFeedingDetail_foodNo1}", "{row.schoolFeedingDetail_foodNo1Allergy}","{row.schoolFeedingDetail_foodNo2}", "{row.schoolFeedingDetail_foodNo2Allergy}","{row.schoolFeedingDetail_foodNo3}", "{row.schoolFeedingDetail_foodNo3Allergy}","{row.schoolFeedingDetail_foodNo4}", "{row.schoolFeedingDetail_foodNo4Allergy}","{row.schoolFeedingDetail_foodNo5}", "{row.schoolFeedingDetail_foodNo5Allergy}","{row.schoolFeedingDetail_foodNo6}", "{row.schoolFeedingDetail_foodNo6Allergy}","{row.schoolFeedingDetail_foodNo7}", "{row.schoolFeedingDetail_foodNo7Allergy}","{row.schoolFeedingDetail_foodNo8}", "{row.schoolFeedingDetail_foodNo8Allergy}","{row.schoolFeedingDetail_foodNo9}", "{row.schoolFeedingDetail_foodNo9Allergy}","{row.schoolFeedingDetail_foodNo10}", "{row.schoolFeedingDetail_foodNo10Allergy}"'")"
            # sql = "INSERT INTO `schoolFeedingDetail` (`" + cols + "`) VALUES ("f'"schoolFeedingDetail-{schoolFeedingDetail_num}","{row.schoolFeeding_id}","{row.schoolFeedingDetail_foodNo1}", "{row.schoolFeedingDetail_foodNo1Allergy}","{row.schoolFeedingDetail_foodNo2}", "{row.schoolFeedingDetail_foodNo2Allergy}","{row.schoolFeedingDetail_foodNo3}", "{row.schoolFeedingDetail_foodNo3Allergy}","{row.schoolFeedingDetail_foodNo4}", "{row.schoolFeedingDetail_foodNo4Allergy}","{row.schoolFeedingDetail_foodNo5}", "{row.schoolFeedingDetail_foodNo5Allergy}","{row.schoolFeedingDetail_foodNo6}", "{row.schoolFeedingDetail_foodNo6Allergy}","{row.schoolFeedingDetail_foodNo7}", "{row.schoolFeedingDetail_foodNo7Allergy}","{row.schoolFeedingDetail_foodNo8}", "{row.schoolFeedingDetail_foodNo8Allergy}","{row.schoolFeedingDetail_foodNo9}", "{row.schoolFeedingDetail_foodNo9Allergy}","{row.schoolFeedingDetail_foodNo10}", "{row.schoolFeedingDetail_foodNo10Allergy}"'")"
            # execute를 사용해 SQL을 실행
            cursor.execute(sql)
            # 결과를 DB에 반영하기
            connection.commit()
    # 연결된 DB닫기
    connection.close()
    return


async def school(year, month, school_area, school_name):
    scmeal = await outer(year, month, school_area, school_name)
    scmeal_list = []
    for scmeal_idx in range(len(scmeal)):
        schoolFeeding_date = scmeal[scmeal_idx].MLSV_YMD # 급식 일자
        schoolFeeding_type = scmeal[scmeal_idx].MMEAL_SC_NM # 식사 구분명
        schoolFeeding_kcal = scmeal[scmeal_idx].CAL_INFO #칼로리 정보
        
        tuple_dtk = (schoolFeeding_date, 
                schoolFeeding_type, 
                schoolFeeding_kcal)
        
        ntr_list = []
        ntr_info = (scmeal[scmeal_idx].NTR_INFO).split('<br/>') # 영양 정보
        for ntr_idx in range(len(ntr_info)) :
            ntr_temp = ntr_info[ntr_idx].split(' : ')
            ntr_list.append(ntr_temp[1])   
            tuple_ntr = tuple(ntr_list)
        
        schoolFeeding_num = str(scmeal_idx).zfill(5)
        schoolFeeding_id = 'schoolFeeding-' + schoolFeeding_num
        tuple_info = (schoolFeeding_id, '', '')
        
        dish_list = []
        dish_name = (scmeal[scmeal_idx].DDISH_NM).split('<br/>') # 요리 정보
        dish_max_count = 10
        dish_len = len(dish_name)

        for dish_idx in range(dish_len) :
            dish_list.append(re.findall('[가-힣]+', dish_name[dish_idx])[0])
            allergy_name = re.findall('\d+', dish_name[dish_idx])
            if len(allergy_name) == 0 : # 알레르기  유발 식재료 번호 X -> 0
                allery_num = '0'
            else : # 알레르기  유발 식재료 번호 O (숫자 ',' 구분)
                allery_num = ''
                for i in range(len(allergy_name)) :
                    allery_num = allery_num + allergy_name[i] + ','
                allery_num = allery_num[:-1]
                
            dish_list.append(allery_num)
        
        null_list = []
        for idx in range(dish_len, dish_max_count) : # 빈 열 null로 채우기
            null_list.append('null')
            null_list.append('null')
        
        dish_tuple = tuple(dish_list)
        null_tuple = tuple(null_list)
        
        scmeal_tuple = tuple_info + tuple_dtk + tuple_ntr + dish_tuple + null_tuple
        scmeal_list.append(scmeal_tuple)
    schoolMeal_df = pd.DataFrame() # 전체 DF
    # sol_1
    schoolMeal_df[['schoolFeeding_id',
                    'schoolFeeding_area',
                    'schoolFeeding_schoolName', 
                    'schoolFeeding_date', 
                    'schoolFeeding_type', 
                    'schoolFeeding_kcal',
                    'schoolFeeding_carbohydrate', 
                    'schoolFeeding_protein', 
                    'schoolFeeding_fat',
                    'schoolFeeding_vitaminA',
                    'schoolFeeding_thiamine',
                    'schoolFeeding_riboflavin', 
                    'schoolFeeding_vitaminC', 
                    'schoolFeeding_calcium', 
                    'schoolFeeding_iron',
                    # 'schoolFeedingDetail_id',
                    'schoolFeedingDetail_foodNo1',
                    'schoolFeedingDetail_foodNo1Allergy',
                    'schoolFeedingDetail_foodNo2',
                    'schoolFeedingDetail_foodNo2Allergy',
                    'schoolFeedingDetail_foodNo3',
                    'schoolFeedingDetail_foodNo3Allergy',
                    'schoolFeedingDetail_foodNo4',
                    'schoolFeedingDetail_foodNo4Allergy',
                    'schoolFeedingDetail_foodNo5',
                    'schoolFeedingDetail_foodNo5Allergy',
                    'schoolFeedingDetail_foodNo6',
                    'schoolFeedingDetail_foodNo6Allergy',
                    'schoolFeedingDetail_foodNo7',
                    'schoolFeedingDetail_foodNo7Allergy',
                    'schoolFeedingDetail_foodNo8',
                    'schoolFeedingDetail_foodNo8Allergy',
                    'schoolFeedingDetail_foodNo9',
                    'schoolFeedingDetail_foodNo9Allergy',
                    'schoolFeedingDetail_foodNo10',
                    'schoolFeedingDetail_foodNo10Allergy']] = pd.DataFrame(scmeal_list)

    print(schoolMeal_df)
    return schoolMeal_df


# 그 달에 있는지 확인하는 함수
def checkMonth(school, area, date):
  # pymysql을 통해 DB에 연결
    connection = pymysql.connect(
    user='ssafy',
    passwd='ssafy',
    host='localhost',
    db='formychild',
    charset='utf8'
    )
    # 연결된 db로부터 cursor 생성
    cursor = connection.cursor()
    sql = (
      "SELECT `schoolFeeding_id` "
      "FROM `schoolfeeding` "
      "WHERE schoolFeeding_schoolName = "f'"{school}"'" AND schoolFeeding_area = "f'"{area}"'" AND DATE_FORMAT(schoolFeeding_date,"f'"%Y%m"'") = "f'"{date}"'""
    )
    # sql = "SELECT * FROM `schoolFeeding` WHERE schoolFeeding_childId = 0"
    # sql = "SELECT * FROM `schoolFeeding` WHERE schoolFeeding_childId = "f'{id}'""
    data = cursor.execute(sql)
    print(data)
    print("checkMonth")
    if data==0:
        connection.close()
        print("FALSE")
        return False
    else:
        print("TRUE")
        connection.close()
        return True

def checkdetail(school):
  # pymysql을 통해 DB에 연결
    connection = pymysql.connect(
    user='ssafy',
    passwd='ssafy',
    host='localhost',
    db='formychild',
    charset='utf8'
    )
    # 연결된 db로부터 cursor 생성
    cursor = connection.cursor()
    sql = (
      "SELECT `schoolFeedingDetail_schoolFeedingId` "
      "FROM `schoolfeedingdetail` "
      "WHERE schoolFeedingDetail_schoolFeedingId = "f'"{school}"'""
    )

    if cursor.execute(sql):
        connection.close()
        return False
    else:
        connection.close()
        return True
# 'schoolFeeding_id', 'schoolFeeding_childId','schoolFeeding_date','schoolFeeding_type','schoolFeeding_kcal','schoolFeeding_carbohydrate','schoolFeeding_protein','schoolFeeding_fat','schoolFeeding_vitaminA','schoolFeeding_thiamine','schoolFeeding_riboflavin','schoolFeeding_vitaminC','schoolFeeding_calcium','schoolFeeding_iron'

# `schoolFeeding_id`, `schoolFeeding_c
# hildId`,`schoolFeeding_date`,`schoolFeeding_type`,`schoolFeeding_kcal`,`schoolFeeding_carbohydrate`,`schoolFeeding_protein`,`schoolFeeding_fat`,`schoolFeeding_vitaminA`,`schoolFeeding_thiamine`,`schoolFeeding_riboflavin`,`schoolFeeding_vitaminC`,`schoolFeeding_calcium`,`schoolFeeding_iron`
# schoolFeeding_db('2021','04','늘봄초등학교')
# schoolFeedingDetail_db('2021','04','늘봄초등학교')
#df = asyncio.run(school("2020", "04", "G10", "상지초등학교"))
#print(df)

#print(checkMonth("초등학교", "G10", "202104"))