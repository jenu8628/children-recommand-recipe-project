package com.webapp.formychild.service;

import com.webapp.formychild.dao.RecipeDaoImpl;
import com.webapp.formychild.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService{

    @Autowired
    RecipeDaoImpl recipeDao;

    /**** 레시피 정보 받아온다. ****/
    @Override
    public RecipeDto getRecipe(String recipe_id) {
        return recipeDao.getRecipe(recipe_id);
    }

    /**** 레시피 조리 과정을 받아온다. ****/
    @Override
    public RecipeProcessDto getRecipeProcess(String recipe_id) {
        return recipeDao.getRecipeProcess(recipe_id);
    }

    /**** 레시피 이름으로 레시피 목록 받아온다. ****/
    @Override
    public List<RecipeDto> searchByRecipeName(String recipe_name) {
        return recipeDao.searchByRecipeName(recipe_name);
    }

    /**** 레시피 재료명으로 레시피 목록 받아온다. ****/
    @Override
    public List<RecipeDto> searchByRecipeIngredient(String recipe_ingredient) {
        return recipeDao.searchByRecipeIngredient(recipe_ingredient);
    }

    /**** 레시피를 스크랩한다. ****/
    @Override
    public void scrapToRecipe(ScrapDto scrap) {
        recipeDao.scrapToRecipe(scrap);
    }

    /**** 레시피 스크랩을 취소한다. ****/
    @Override
    public void cancelScrapToRecipe(ScrapDto scrap) {
        recipeDao.cancelScrapToRecipe(scrap);
    }

    /**** 스크랩한 레시피를 받아온다. ****/
    @Override
    public List<RecipeDto> getAllScrapRecipe(String scrap_parentId) {
        return recipeDao.getAllScrapRecipe(scrap_parentId);
    }

    @Override
    public List<RecipeDto> getRecommendationRecipe(String recommendationRecipe_childId) {
        return recipeDao.getRecommendationRecipe(recommendationRecipe_childId);
    }

    @Override
    public List<FoodCategoryDto> getAllFoodCategory() {
        return recipeDao.getAllFoodCategory();
    }

    @Override
    public List<FoodIngredientDto> getAllFoodIngredient() {
        return recipeDao.getAllFoodIngredient();
    }

    /**** 재료명 리스트에 포함된 모든 레시피 아이디 리스트를 가져온다. ****/
    @Override
    public List<RecipeDto> getAllRecipeByIngredientList(List<String> foodIngredient_names) {
        return recipeDao.getAllRecipeByIngredientList(foodIngredient_names);
    }

    /**** 알러지를 제거한 추천 레시피를 가져온다. ****/
    @Override
    public List<RecipeDto> getRecommendationRecipesByAllergy(HashMap<String, Object> map) {
        return recipeDao.getRecommendationRecipesByAllergy(map);
    }

    /**** 추천 레시피를 가져온다. ****/
    @Override
    public List<RecipeDto> getRecommendationRecipes(String child_id) {
        return recipeDao.getRecommendationRecipes(child_id);
    }

    @Override
    public void insertFoodIngredient(FoodIngredientDto foodIngredientDto) {
        recipeDao.insertFoodIngredient(foodIngredientDto);
    }

}
