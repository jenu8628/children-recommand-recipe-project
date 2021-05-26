package com.webapp.formychild.service;

import com.webapp.formychild.dto.FoodRecordDto;
import com.webapp.formychild.dto.RecipeRatingDto;

import java.util.HashMap;
import java.util.List;

public interface FoodRecordService {

    // 요리 완료(식단 기록 기능)
    int finishRecipe(FoodRecordDto foodRecord) throws Exception;

    // 요리 완료 후 전체 레시피 점수 업데이트
    int updateRecipeRating(String foodRecord_recipeId);

    // 요리 완료 후 전체 레시피 난이도 업데이트
    int updateRecipeDifficulty(String foodRecord_recipeId);

    // 월별 식단기록 받아오기
    List<FoodRecordDto> getMyMonthlyFoodRecord(FoodRecordDto foodRecord) throws Exception;

    // 식단기록 삭제
    void removeMyFoodRecord(String foodRecord_id) throws Exception;

    // 식단 등록한 레시피와 해당 child_id로 RecipeRating DB에서 조회
    RecipeRatingDto getRecipeRating(HashMap<String, Object> map);

    // 요리 완료 후 RecipeRating DB에 데이터 추가
    void addRecipeRatingDB(RecipeRatingDto newRecipeRating);

    // 요리 완료 후 RecipeRating DB에 데이터 업데이트
    void updateRecipeRatingDB(RecipeRatingDto isRecipeRating);
}
