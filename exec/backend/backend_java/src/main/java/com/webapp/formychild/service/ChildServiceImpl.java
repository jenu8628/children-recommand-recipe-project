package com.webapp.formychild.service;

import com.webapp.formychild.dao.ChildDaoImpl;
import com.webapp.formychild.dto.ChildDto;
import com.webapp.formychild.dto.ParentDto;
import com.webapp.formychild.dto.PreferenceDto;
import com.webapp.formychild.dto.RecipeRatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class ChildServiceImpl implements ChildService{

    @Autowired
    ChildDaoImpl childDao;

    /***** 내 아이 추가 하기 *****/
    @Override
    public void addChild(ChildDto child) {
        childDao.addChild(child);
    }

    /***** 내 아이들 조회하기 *****/
    @Override
    public List<ChildDto> getMyChildren(String child_parentId) {
        return childDao.getMyChildren(child_parentId);
    }

    /***** 내 아이 조회하기 (아이 채널 선택) *****/
    @Override
    public ChildDto getMyChild(String child_id) {
        return childDao.getMyChild(child_id);
    }

    /***** 내 아이 정보 수정하기 *****/
    @Override
    public void updateChild(ChildDto child) {
        childDao.updateChild(child);
    }

    /***** 내 아이 정보 삭제하기 *****/
    @Override
    public void deleteChild(String child_id) {
        childDao.deleteChild(child_id);
    }

    /***** 내 아이 선호 재료 추가하기 *****/
    @Override
    public void addPreferenceForChild(PreferenceDto preference) {
        childDao.addPreferenceForChild(preference);
    }

    /***** 내 아이 선호 재료 모두 삭제하기 *****/
    @Override
    public int deleteAllPreferences(String preference_childId) {
        return childDao.deleteAllPreferences(preference_childId);
    }

    /**** 재료 아이디로 재료명 가져오기 ****/
    @Override
    public String getFoodIngredientNameById(String foodIngredientId) {
        return childDao.getFoodIngredientNameById(foodIngredientId);
    }

    /**** 아이 부모 아이디 가져오기 ****/
    @Override
    public ParentDto getMyParent(String child_id) {
        return childDao.getMyParent(child_id);
    }

    /**** 아이 선호 재료 RecipeRating DB에 4점 부여 ****/
    @Override
    public void addRecipeRating(RecipeRatingDto recipeRating) {
        childDao.addRecipeRating(recipeRating);
    }

    @Override
    public HashMap<String, Object> getTodayNutritionalInfo(String child_id) {
        return childDao.getTodayNutritionalInfo(child_id);
    }

    @Override
    public String getChildId(String child_parentId) {
        return childDao.getChildId(child_parentId);
    }
}
