from django.urls import path
from . import views



urlpatterns = [
    path('getRecommendationRecipeByChildId/<str:child_id>/', views.getRecommendationRecipeByChildId),
    path('playRecommendationAlgo', views.playRecommendationAlgo),
]
