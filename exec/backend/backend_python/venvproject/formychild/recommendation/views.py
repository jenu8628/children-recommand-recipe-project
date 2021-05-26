from django.shortcuts import render

# Create your views here.
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from .models import Child, Recipe, RecipeRating, Recommendation
from .serializers import RecipeSerializer, RecommendationSerializer
from .recommendation import *

# Schoolfeedingdetail
# Schoolschedule

def home(request) :

    # Parent 테이블의 모든 객체 불러와 parent 변수에 저장
    childs = Child.objects.all()

    return render(request, 'home.html', {"childs":childs})

''' child_id로 추천 레시피들 가져오기 '''
@api_view(['GET'])
def getRecommendationRecipeByChildId(request,child_id):
    recommendation = Recommendation.objects.filter(recommendation_childId = child_id)
    recommendationSerializer = RecommendationSerializer(recommendation, many=True)
    recipes_list = []
    for i in range(0,len(recommendationSerializer.data)):
        recipes_list.append(recommendationSerializer.data[i]['recommendation_recipeId'])
    recipes = Recipe.objects.filter(recipe_id__in = recipes_list)
    recipeSerializer = RecipeSerializer(recipes, many=True)
    return Response(recipeSerializer.data)

''' 추천 알고리즘 돌리기 '''
@api_view(['POST'])
def playRecommendationAlgo(request):
    #predict_frame = correlation()
    predict_frame = cosine()
    saveToRecommendationDB(predict_frame, 3.5)
    return Response(status=status.HTTP_201_CREATED)