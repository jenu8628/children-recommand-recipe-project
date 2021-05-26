package com.webapp.formychild.service;

import com.webapp.formychild.dao.FoodRecordDaoImpl;
import com.webapp.formychild.dto.FoodRecordDto;
import com.webapp.formychild.dto.RecipeRatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class FoodRecordServiceImpl implements FoodRecordService{

    @Autowired
    private FoodRecordDaoImpl foodRecordDao;

    @Override
    public int finishRecipe(FoodRecordDto foodRecord) throws Exception {
        return foodRecordDao.finishRecipe(foodRecord);
    }

    @Override
    public int updateRecipeRating(String foodRecord_recipeId) {
        return foodRecordDao.updateRecipeRating(foodRecord_recipeId);
    }

    @Override
    public int updateRecipeDifficulty(String foodRecord_recipeId) {
        return foodRecordDao.updateRecipeDifficulty(foodRecord_recipeId);
    }

    @Override
    public List<FoodRecordDto> getMyMonthlyFoodRecord(FoodRecordDto foodRecord) throws Exception{
        return foodRecordDao.getMyMonthlyFoodRecord(foodRecord);
    }

    @Override
    public void removeMyFoodRecord(String foodRecord_id) throws Exception {
        foodRecordDao.removeMyFoodRecord(foodRecord_id);
    }

    @Override
    public RecipeRatingDto getRecipeRating(HashMap<String, Object> map) {
        return foodRecordDao.getRecipeRating(map);
    }

    @Override
    public void addRecipeRatingDB(RecipeRatingDto newRecipeRating) {
        foodRecordDao.addRecipeRatingDB(newRecipeRating);
    }

    @Override
    public void updateRecipeRatingDB(RecipeRatingDto isRecipeRating) {
        foodRecordDao.updateRecipeRatingDB(isRecipeRating);
    }
}
