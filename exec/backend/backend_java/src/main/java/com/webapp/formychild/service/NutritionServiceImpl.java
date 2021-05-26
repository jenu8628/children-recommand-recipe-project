package com.webapp.formychild.service;

import com.webapp.formychild.dao.NutritionDaoImpl;
import com.webapp.formychild.dto.FoodRecordDto;
import com.webapp.formychild.dto.NutritionDto;
import com.webapp.formychild.dto.SchoolFeedingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class NutritionServiceImpl implements NutritionService{
    @Autowired
    private NutritionDaoImpl nutritionDao;

    @Override
    public List<FoodRecordDto> getTodayFoodRecord(HashMap<String, Object> map) {
        return nutritionDao.getTodayFoodRecord(map);
    }

    @Override
    public SchoolFeedingDto getTodaySchoolFeeding(HashMap<String, Object> map) {
        return nutritionDao.getTodaySchoolFeeding(map);
    }

    @Override
    public HashMap<String, Object> getNutritionalStandard(HashMap<String, Object> map) {
        return nutritionDao.getNutritionalStandard(map);
    }


}
