package com.webapp.formychild.dao;

import com.webapp.formychild.dto.FoodRecordDto;
import com.webapp.formychild.dto.SchoolFeedingDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class NutritionDaoImpl {
    String ns = "com.webapp.formychild.dao.nutritionDao.";

    @Autowired
    private SqlSession sqlSession;

    public List<FoodRecordDto> getTodayFoodRecord(HashMap<String, Object> map) {
        return sqlSession.selectList(ns + "getTodayFoodRecord", map);
    }

    public SchoolFeedingDto getTodaySchoolFeeding(HashMap<String, Object> map) {
        return sqlSession.selectOne(ns + "getTodaySchoolFeeding", map);
    }

    public HashMap<String, Object> getNutritionalStandard(HashMap<String, Object> map) {
        return sqlSession.selectOne(ns + "getNutritionalStandard", map);
    }
}
