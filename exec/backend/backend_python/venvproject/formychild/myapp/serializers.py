from rest_framework import serializers
from .models import Schoolfeeding, Schoolfeedingdetail, Child

class SchoolfeedingdetailSerializer(serializers.ModelSerializer):

    class Meta:
        model = Schoolfeedingdetail
        fields = '__all__'
        # fields = ('schoolfeedingdetail_num','schoolfeedingdetail_id', 'schoolfeedingdetail_schoolfeedingid','schoolfeedingdetail_foodno1', 'schoolfeedingdetail_foodno1allergy', 'schoolfeedingdetail_foodno2','schoolfeedingdetail_foodno2allergy','schoolfeedingdetail_foodno3','schoolfeedingdetail_foodno3allergy','schoolfeedingdetail_foodno4','schoolfeedingdetail_foodno4allergy','schoolfeedingdetail_foodno5','schoolfeedingdetail_foodno5allergy','schoolfeedingdetail_foodno6','schoolfeedingdetail_foodno6allergy','schoolfeedingdetail_foodno7','schoolfeedingdetail_foodno7allergy','schoolfeedingdetail_foodno8', 'schoolfeedingdetail_foodno8allergy','schoolfeedingdetail_foodno9','schoolfeedingdetail_foodno9allergy','schoolfeedingdetail_foodno10','schoolfeedingdetail_foodno10allergy')

class SchoolfeedingSerializer(serializers.ModelSerializer):

    class Meta:
        model = Schoolfeeding
        fields = '__all__'
        # fields = ('schoolfeeding_num','schoolfeeding_id', 'schoolfeeding_childId','schoolfeeding_num', 'schoolfeeding_date', 'schoolfeeding_type','schoolfeeding_kcal','schoolfeeding_carbohydrate','schoolfeeding_protein','schoolfeeding_fat','schoolfeeding_vitamina','schoolfeeding_thiamine','schoolfeeding_riboflavin','schoolfeeding_vitaminc','schoolfeeding_calcium','schoolfeeding_iron', 'Schoolfeedingdetail')



# class SchoolscheduleSerializer(serializers.ModelSerializer):

#     class Meta:
#         model = Schoolschedule
#         fields = ('schoolschedule_num','schoolschedule_date','schoolschedule_content')
