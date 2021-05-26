from django.db import models

# Create your models here.

#### 추천 레시피 알고리즘에 필요한 Models
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
        db_table = 'Parent'
        unique_together = (('parent_num', 'parent_id'),)

class Child(models.Model):
    #child_num = models.AutoField(primary_key=True)
    child_id = models.CharField(max_length=32, primary_key=True)
    child_parentid = models.ForeignKey('Parent', models.DO_NOTHING, db_column='child_parentId')  # Field name made lowercase.
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

class Recipe(models.Model):
    recipe_num = models.AutoField(db_column='recipe_num', primary_key=True)
    recipe_id =  models.CharField(db_column='recipe_id', max_length=32)
    recipe_name = models.CharField(db_column='recipe_name', max_length=100)
    recipe_method = models.CharField(db_column='recipe_method', max_length=50)
    recipe_kind = models.CharField(db_column='recipe_kind', max_length=50)
    recipe_difficulty = models.FloatField(blank=True, null=True)
    recipe_rating = models.FloatField(blank=True, null=True)
    recipe_ingredient = models.CharField(db_column='recipe_ingredient', max_length=600)
    recipe_kcal = models.CharField(db_column='recipe_kcal', max_length=16)
    recipe_carbohydrate =  models.CharField(db_column='recipe_carbohydrate', max_length=16)
    recipe_protein = models.CharField(db_column='recipe_protein', max_length=16)
    recipe_fat = models.CharField(db_column='recipe_fat', max_length=16)
    recipe_natrium = models.CharField(db_column='recipe_natrium', max_length=16)
    recipe_thumbnail = models.CharField(db_column='recipe_thumbnail', max_length=150)

    class Meta:
            managed = False
            db_table = 'recipe'

class RecipeRating(models.Model):
    recipeRating_recipeId = models.ForeignKey(Recipe, models.DO_NOTHING, db_column='recipe_id')
    recipeRating_childId = models.ForeignKey(Child, models.DO_NOTHING, db_column='child_id')
    recipeRating_rating = models.FloatField(blank=True, null=True)

    class Meta:
                managed = False
                db_table = 'reciperating'

class Recommendation(models.Model):
    recommendation_num = models.AutoField(db_column='recommendation_num', primary_key=True)
    recommendation_recipeId = models.ForeignKey(Recipe, models.DO_NOTHING, db_column='recommendation_recipeId')
    recommendation_childId = models.ForeignKey(Child,  models.DO_NOTHING, db_column='recommendation_childId')

    class Meta:
                    managed = False
                    db_table = 'recommendation'