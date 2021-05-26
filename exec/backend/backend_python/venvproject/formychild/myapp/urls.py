from django.urls import path
from . import views



urlpatterns = [
    path('schoolfeeding/<str:date>/<str:school>/<str:area>/', views.schoolfeeding),
    path('schoolfeedingdetail/<str:date>/<str:area>/<str:school>/<str:id>/', views.schoolfeedingdetail),
    # path('dummy/', views.dummy),

]
