from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from .models import Schoolfeeding, Schoolfeedingdetail, Child
from .serializers import SchoolfeedingSerializer, SchoolfeedingdetailSerializer
from .schoolFeedingData import *

# Schoolfeedingdetail
# Schoolschedule

def home(request) :

    # Parent 테이블의 모든 객체 불러와 parent 변수에 저장
    parents = Parent.objects.all()

    return render(request, 'home.html', {"parents":parents})



@api_view(['GET'])
def schoolfeeding(request,date,school,area):
    '''
    학교 급식 영양정보 조회하는 API
    ## `http://127.0.0.1:8000/myapp/schoolfeeding/<str:date>/<str:school>/<str:area>/`
    ## 내용
        - date : 조회할 날짜(ex: '20200302')
        - school : 아이의 학교이름 (ex: '늘봄초등학교')
        - area : 아이 학교 교육청코드 (ex: 'G10')
    ## 리턴값
        - schoolfeeding_id
        - schoolfeeding_schoolName
        - schoolfeeding_date
        - schoolfeeding_type
        - schoolfeeding_kcal
        - schoolfeeding_protein
        - schoolfeeding_fat
        - schoolfeeding_vitamina
        - schoolfeeding_thiamine
        - schoolfeeding_riboflavin
        - schoolfeeding_vitaminc
        - schoolfeeding_calcium
        - schoolfeeding_iron
    '''
    # 이미 DB에 있으면 추가하지 않고, DB에 없으면 추가한다.
    if not checkMonth(school,area,date[0:6]):
        # DB에 데이터 넣는 함수
        schoolFeeding_db(date[0:4],date[4:6], area, school)
    print(date[4:6])
    schoolfeeding = Schoolfeeding.objects.filter(schoolfeeding_schoolname=school, schoolfeeding_area=area, schoolfeeding_date=date)
    serializer = SchoolfeedingSerializer(schoolfeeding, many=True)
    return Response(serializer.data)


@api_view(['GET'])
# def schoolfeedingdetail(request,date):
def schoolfeedingdetail(request, date, area, school, id):
    '''
    해당 날짜의 학교 급식(음식들) 조회하는 API 
    ---
    ## `http://127.0.0.1:8000/myapp/schoolfeedingdetail/<str:id>/`
    ## 내용
            - date : 조회할 날짜(ex: '20200302')
            - area : 아이 학교 교육청코드 (ex: 'G10')
            - school : 아이의 학교이름 (ex: '늘봄초등학교')
            - id : 조회 날짜와 학교정보를 통해 schoolfeeding정보를 불러오고 그 중 schoolfeedingdetail_schoolfeedingid에 해당하는 정보에 해당한다.
            id 데이터 예시 : schoolfeeding-000
    ## 리턴값
        - schoolfeedingdetail_id
        - schoolfeedingdetail_foodno1
        - schoolfeedingdetail_foodno1allergy
        ... 총 no10까지
    '''
    if checkdetail(id):
            schoolFeedingDetail_db(date[0:4],date[4:6], area, school)
    schoolfeedingdetail = Schoolfeedingdetail.objects.filter(schoolfeedingdetail_schoolfeedingid=id)
    serializer = SchoolfeedingdetailSerializer(schoolfeedingdetail, many=True)
    return Response(serializer.data)