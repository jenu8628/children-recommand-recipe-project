package com.webapp.formychild.service;

import com.webapp.formychild.dto.*;

import java.util.HashMap;
import java.util.List;

public interface RecipeService {

    /**** 레시피 정보 받아온다. ****/
    RecipeDto getRecipe(String recipe_id);

    /**** 레시피 조리 과정 정보 받아온다. ****/
    RecipeProcessDto getRecipeProcess(String recipe_id);

    /**** 레시피 이름으로 레시피 목록 받아온다. ****/
    List<RecipeDto> searchByRecipeName(String recipe_name);

    /**** 레시피 재료명으로 레시피 목록 받아온다. ****/
    List<RecipeDto> searchByRecipeIngredient(String recipe_ingredient);

    /**** 레시피를 스크랩한다. ****/
    void scrapToRecipe(ScrapDto scrap);

    /**** 레시피 스크랩을 취소한다. ****/
    void cancelScrapToRecipe(ScrapDto scrap);

    /**** 스크랩한 레시피를 받아온다. ****/
    List<RecipeDto> getAllScrapRecipe(String scrap_parentId);

    /**** 초기 레시피 추천 필터링 ****/
    List<RecipeDto> getRecommendationRecipe(String recommendationRecipe_childId);

    List<FoodCategoryDto> getAllFoodCategory();

    List<FoodIngredientDto> getAllFoodIngredient();

    /**** 재료명 리스트에 포함된 모든 레시피 아이디를 가져온다. ****/
    List<RecipeDto> getAllRecipeByIngredientList(List<String> foodIngredient_names);

    /**** 알러지를 제거한 추천 레시피를 가져온다. ****/
    List<RecipeDto> getRecommendationRecipesByAllergy(HashMap<String, Object> map);

    /**** 추천 레시피를 가져온다. ****/
    List<RecipeDto> getRecommendationRecipes(String child_id);

    void insertFoodIngredient(FoodIngredientDto foodIngredientDto);
}
