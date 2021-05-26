package com.webapp.formychild.dao;

import com.webapp.formychild.dto.ChildDto;
import com.webapp.formychild.dto.ParentDto;
import com.webapp.formychild.dto.PreferenceDto;
import com.webapp.formychild.dto.RecipeRatingDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ChildDaoImpl {
    String ns = "com.webapp.formychild.dao.childDao.";

    @Autowired
    private SqlSession sqlSession;

    /***** 내 아이 추가 하기 *****/
    public void addChild(ChildDto child) {
        sqlSession.insert(ns+"addChild", child);
    }

    /***** 내 아이들 조회하기 *****/
    public List<ChildDto> getMyChildren(String child_parentId) {
        return sqlSession.selectList(ns+"getMyChildren", child_parentId);
    }

    /***** 내 아이 조회하기 (아이 채널 선택) *****/
    public ChildDto getMyChild(String child_id) {
        return sqlSession.selectOne(ns+"getMyChild", child_id);
    }

    /***** 내 아이 정보 수정하기 *****/
    public void updateChild(ChildDto child) {
        sqlSession.update(ns+"updateChild", child);
    }

    /***** 내 아이 정보 삭제하기 *****/
    public void deleteChild(String child_id) {
        sqlSession.update(ns +"deleteChild", child_id);
    }

    /***** 내 아이 선호 재료 추가하기 *****/
    public void addPreferenceForChild(PreferenceDto preference) {
        sqlSession.insert(ns+"addPreferenceForChild", preference);
    }

    /***** 내 아이 선호 재료 모두 삭제하기 *****/
    public int deleteAllPreferences(String preference_childId) {
        return sqlSession.delete(ns+"deleteAllPreferences", preference_childId);
    }

    public HashMap<String, Object> getTodayNutritionalInfo(String child_id) {
        return (HashMap<String, Object>) sqlSession.selectList(ns + "getTodayNutritionalInfo", child_id);
    }

    public String getChildId(String child_parentId) {
        return sqlSession.selectOne(ns+"getChildId", child_parentId);
    }

    /**** 재료 아이디로 재료명 가져오기 ****/
    public String getFoodIngredientNameById(String foodIngredientId) {
        return sqlSession.selectOne(ns + "getFoodIngredientNameById", foodIngredientId);
    }

    /**** 아이 부모 아이디 가져오기 ****/
    public ParentDto getMyParent(String child_id) {
        return sqlSession.selectOne(ns + "getMyParent", child_id);
    }

    /**** 아이 선호 재료 RecipeRating DB에 4점 부여 ****/
    public void addRecipeRating(RecipeRatingDto recipeRating) {
        sqlSession.insert(ns + "addRecipeRating", recipeRating);
    }
}
