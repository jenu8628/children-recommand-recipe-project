package com.webapp.formychild.dao;

import com.webapp.formychild.dto.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class RecipeDaoImpl {
    String ns = "com.webapp.formychild.dao.recipeDao.";

    @Autowired
    private SqlSession sqlSession;

    public RecipeDto getRecipe(String recipe_id) {
        return sqlSession.selectOne(ns+"getRecipe",recipe_id);
    }

    public RecipeProcessDto getRecipeProcess(String recipe_id) {
        return sqlSession.selectOne(ns+"getRecipeProcess", recipe_id);
    }

    public List<RecipeDto> searchByRecipeName(String recipe_name) {
        return sqlSession.selectList(ns+"searchByRecipeName", recipe_name);
    }

    public List<RecipeDto> searchByRecipeIngredient(String recipe_ingredient) {
        return sqlSession.selectList(ns+"searchByRecipeIngredient", recipe_ingredient);
    }

    public void scrapToRecipe(ScrapDto scrap) {
        sqlSession.insert(ns+"scrapToRecipe", scrap);
    }

    public void cancelScrapToRecipe(ScrapDto scrap) {
        sqlSession.delete(ns+"cancelScrapToRecipe", scrap);
    }

    public List<RecipeDto> getAllScrapRecipe(String scrap_parentId) {
        return sqlSession.selectList(ns+"getAllScrapRecipe",scrap_parentId);
    }

    public List<RecipeDto> getRecommendationRecipe(String recommendationRecipe_childId) {
        List<String> preferenceIngredient = sqlSession.selectList(ns + "getPreferenceIngredientList", recommendationRecipe_childId);
        return sqlSession.selectList(ns + "getAllRecipeByIngredientList", preferenceIngredient);
    }

    public List<FoodCategoryDto> getAllFoodCategory() {
        return sqlSession.selectList(ns + "getAllFoodCategory");
    }

    public List<FoodIngredientDto> getAllFoodIngredient() {
        return sqlSession.selectList(ns + "getAllFoodIngredient");
    }

    /**** DAO 재료명 리스트에 포함된 모든 레시피 아이디 리스트를 가져온다. ****/
    public List<RecipeDto> getAllRecipeByIngredientList(List<String> foodIngredient_names) {
        return sqlSession.selectList(ns + "getAllRecipeByIngredientList", foodIngredient_names);
    }

    public List<RecipeDto> getRecommendationRecipesByAllergy(HashMap<String, Object> map) {
        return sqlSession.selectList(ns +"getRecommendationRecipesByAllergy", map);
    }

    public List<RecipeDto> getRecommendationRecipes(String child_id) {
        return sqlSession.selectList(ns +"getRecommendationRecipes", child_id);
    }

    public void insertFoodIngredient(FoodIngredientDto foodIngredientDto) {
        sqlSession.insert(ns +"insertFoodIngredient", foodIngredientDto);
    }
}
