package com.webapp.formychild.service;


import com.webapp.formychild.dto.ChildDto;
import com.webapp.formychild.dto.ParentDto;
import com.webapp.formychild.dto.PreferenceDto;
import com.webapp.formychild.dto.RecipeRatingDto;

import java.util.HashMap;
import java.util.List;

public interface ChildService {

    /***** 내 아이 추가 하기 *****/
    void addChild(ChildDto child);

    /***** 내 아이들 조회하기 *****/
    List<ChildDto> getMyChildren(String child_parentId);

    /***** 내 아이 조회하기 (아이 채널 선택) *****/
    ChildDto getMyChild(String child_id);

    /***** 내 아이 정보 수정하기 *****/
    void updateChild(ChildDto child);

    /***** 내 아이 정보 삭제하기 *****/
    void deleteChild(String child_id);

    /***** 내 아이 선호 재료 추가하기 *****/
    void addPreferenceForChild(PreferenceDto preference);

    /***** 내 아이 선호 재료 모두 삭제하기 *****/
    int deleteAllPreferences(String preference_childId);

    /**** 재료 아이디로 재료명 가져오기 ****/
    String getFoodIngredientNameById(String foodIngredientId);

    /**** 아이 부모 아이디 가져오기 ****/
    ParentDto getMyParent(String child_id);

    /**** 아이 선호 재료 RecipeRating DB에 4점 부여 ****/
    void addRecipeRating(RecipeRatingDto recipeRating);

    HashMap<String, Object> getTodayNutritionalInfo(String child_id);

    /***** 방금 등록한 아이 아이디 가져오기 *****/
    String getChildId(String child_parentId);

}
