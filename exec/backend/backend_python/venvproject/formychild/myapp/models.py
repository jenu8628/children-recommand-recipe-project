# models.py
from django.db import models

# python manage.py inspectdb -> class copy

# models.py 수정 후,
# python manage.py makemigrations
# python manage.py migrate 

class Parent(models.Model):
    parent_num = models.AutoField(primary_key=True)
    parent_id = models.CharField(max_length=32)
    parent_email = models.CharField(unique=True, max_length=100)
    parent_pwd = models.CharField(max_length=32)
    parent_name = models.CharField(max_length=32)
    parent_tel = models.CharField(unique=True, max_length=11)
    parent_birth = models.CharField(max_length=10)

    class Meta:
        managed = False
        db_table = 'parent'
        unique_together = (('parent_num', 'parent_id'),)


class Child(models.Model):
    child_num = models.AutoField(primary_key=True)
    child_id = models.CharField(max_length=32)
    child_parentid = models.ForeignKey(Parent, models.DO_NOTHING, db_column='child_parentId')  # Field name made lowercase.
    child_area = models.CharField(max_length=32)
    child_schoolname = models.CharField(db_column='child_schoolName', max_length=100)  # Field name made lowercase.
    child_name = models.CharField(max_length=32)
    child_sex = models.CharField(max_length=10)
    child_birth = models.CharField(max_length=10)
    child_height = models.FloatField(blank=True, null=True)
    child_weight = models.FloatField(blank=True, null=True)
    child_profile = models.TextField(blank=True, null=True)
    child_bmi = models.FloatField(db_column='child_BMI', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'child'
        unique_together = (('child_num', 'child_id'),)

    class Meta:
        managed = False
        db_table = 'child'
        unique_together = (('child_num', 'child_id'),)

class Schoolfeeding(models.Model):
    schoolfeeding_id = models.CharField(db_column='schoolFeeding_id', primary_key=True, max_length=32)  # Field name made lowercase.
    schoolfeeding_area = models.CharField(db_column='schoolFeeding_area', max_length=10)  # Field name made lowercase.
    schoolfeeding_schoolname = models.CharField(db_column='schoolFeeding_schoolName', max_length=32)  # Field name made lowercase.
    schoolfeeding_date = models.CharField(db_column='schoolFeeding_date', max_length=30, blank=True, null=True)  # Field name made lowercase.
    schoolfeeding_type = models.CharField(db_column='schoolFeeding_type', max_length=30, blank=True, null=True)  # Field name made lowercase.
    schoolfeeding_kcal = models.CharField(db_column='schoolFeeding_kcal', max_length=16, blank=True, null=True)  # Field name made lowercase.
    schoolfeeding_carbohydrate = models.CharField(db_column='schoolFeeding_carbohydrate', max_length=16, blank=True, null=True)  # Field name made lowercase.
    schoolfeeding_protein = models.CharField(db_column='schoolFeeding_protein', max_length=16, blank=True, null=True)  # Field name made lowercase.
    schoolfeeding_fat = models.CharField(db_column='schoolFeeding_fat', max_length=16, blank=True, null=True)  # Field name made lowercase.
    schoolfeeding_vitamina = models.CharField(db_column='schoolFeeding_vitaminA', max_length=16, blank=True, null=True)  # Field name made lowercase.
    schoolfeeding_thiamine = models.CharField(db_column='schoolFeeding_thiamine', max_length=16, blank=True, null=True)  # Field name made lowercase.
    schoolfeeding_riboflavin = models.CharField(db_column='schoolFeeding_riboflavin', max_length=16, blank=True, null=True)  # Field name made lowercase.
    schoolfeeding_vitaminc = models.CharField(db_column='schoolFeeding_vitaminC', max_length=16, blank=True, null=True)  # Field name made lowercase.
    schoolfeeding_calcium = models.CharField(db_column='schoolFeeding_calcium', max_length=16, blank=True, null=True)  # Field name made lowercase.
    schoolfeeding_iron = models.CharField(db_column='schoolFeeding_iron', max_length=16, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'schoolfeeding'


class Schoolfeedingdetail(models.Model):
    schoolfeedingdetail_id = models.CharField(db_column='schoolFeedingDetail_id', primary_key=True, max_length=32)  # Field name made lowercase.
    schoolfeedingdetail_schoolfeedingid = models.ForeignKey(Schoolfeeding, models.DO_NOTHING, db_column='schoolFeedingDetail_schoolFeedingId')  # Field name made lowercase.
    schoolfeedingdetail_foodno1 = models.CharField(db_column='schoolFeedingDetail_foodNo1', max_length=100, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno1allergy = models.CharField(db_column='schoolFeedingDetail_foodNo1Allergy', max_length=50, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno2 = models.CharField(db_column='schoolFeedingDetail_foodNo2', max_length=100, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno2allergy = models.CharField(db_column='schoolFeedingDetail_foodNo2Allergy', max_length=50, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno3 = models.CharField(db_column='schoolFeedingDetail_foodNo3', max_length=100, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno3allergy = models.CharField(db_column='schoolFeedingDetail_foodNo3Allergy', max_length=50, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno4 = models.CharField(db_column='schoolFeedingDetail_foodNo4', max_length=100, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno4allergy = models.CharField(db_column='schoolFeedingDetail_foodNo4Allergy', max_length=50, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno5 = models.CharField(db_column='schoolFeedingDetail_foodNo5', max_length=100, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno5allergy = models.CharField(db_column='schoolFeedingDetail_foodNo5Allergy', max_length=50, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno6 = models.CharField(db_column='schoolFeedingDetail_foodNo6', max_length=100, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno6allergy = models.CharField(db_column='schoolFeedingDetail_foodNo6Allergy', max_length=50, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno7 = models.CharField(db_column='schoolFeedingDetail_foodNo7', max_length=100, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno7allergy = models.CharField(db_column='schoolFeedingDetail_foodNo7Allergy', max_length=50, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno8 = models.CharField(db_column='schoolFeedingDetail_foodNo8', max_length=100, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno8allergy = models.CharField(db_column='schoolFeedingDetail_foodNo8Allergy', max_length=50, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno9 = models.CharField(db_column='schoolFeedingDetail_foodNo9', max_length=100, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno9allergy = models.CharField(db_column='schoolFeedingDetail_foodNo9Allergy', max_length=50, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno10 = models.CharField(db_column='schoolFeedingDetail_foodNo10', max_length=100, blank=True, null=True)  # Field name made lowercase.
    schoolfeedingdetail_foodno10allergy = models.CharField(db_column='schoolFeedingDetail_foodNo10Allergy', max_length=50, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'schoolfeedingdetail'

        
class Schoolschedule(models.Model):
    schoolschedule_num = models.AutoField(db_column='schoolSchedule_num', primary_key=True)  # Field name made lowercase.
    schoolschedule_id = models.CharField(db_column='schoolSchedule_id', max_length=32)  # Field name made lowercase.
    schoolschedule_childid = models.ForeignKey(Child, models.DO_NOTHING, db_column='schoolSchedule_childId')  # Field name made lowercase.
    schoolschedule_date = models.CharField(db_column='schoolSchedule_date', max_length=30, blank=True, null=True)  # Field name made lowercase.
    schoolschedule_content = models.CharField(db_column='schoolSchedule_content', max_length=100, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'schoolschedule'
        unique_together = (('schoolschedule_num', 'schoolschedule_id'),)


