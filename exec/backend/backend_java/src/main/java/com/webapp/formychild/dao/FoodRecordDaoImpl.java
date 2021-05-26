package com.webapp.formychild.dao;

import com.webapp.formychild.dto.FoodRecordDto;
import com.webapp.formychild.dto.RecipeRatingDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class FoodRecordDaoImpl {
    String ns = "com.webapp.formychild.dao.foodRecordDao.";

    @Autowired
    private SqlSession sqlSession;

    public int finishRecipe(FoodRecordDto foodRecord) {
        return sqlSession.insert(ns+"finishRecipe",foodRecord);
    }

    public int updateRecipeRating(String foodRecord_recipeId) {
        return sqlSession.update(ns+"updateRecipeRating", foodRecord_recipeId);
    }

    public int updateRecipeDifficulty(String foodRecord_recipeId) {
        return sqlSession.update(ns+"updateRecipeDifficulty", foodRecord_recipeId);
    }

    public List<FoodRecordDto> getMyMonthlyFoodRecord(FoodRecordDto foodRecord) {
        return sqlSession.selectList(ns+"getMyMonthlyFoodRecord",foodRecord);
    }

    public void removeMyFoodRecord(String foodRecord_id) {
        sqlSession.delete(ns+"removeMyFoodRecord", foodRecord_id);
    }

    public RecipeRatingDto getRecipeRating(HashMap<String, Object> map) {
        return sqlSession.selectOne(ns + "getRecipeRating", map);
    }

    public void addRecipeRatingDB(RecipeRatingDto newRecipeRating) {
        sqlSession.insert(ns + "addRecipeRatingDB", newRecipeRating);
    }

    public void updateRecipeRatingDB(RecipeRatingDto isRecipeRating) {
        sqlSession.update(ns + "updateRecipeRatingDB", isRecipeRating);
    }
}
