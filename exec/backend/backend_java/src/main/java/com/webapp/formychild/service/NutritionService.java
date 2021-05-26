package com.webapp.formychild.service;

import com.webapp.formychild.dto.FoodRecordDto;
import com.webapp.formychild.dto.NutritionDto;
import com.webapp.formychild.dto.SchoolFeedingDto;

import java.util.HashMap;
import java.util.List;

public interface NutritionService {

    List<FoodRecordDto> getTodayFoodRecord(HashMap<String, Object> map);

    SchoolFeedingDto getTodaySchoolFeeding(HashMap<String, Object> map);


    HashMap<String, Object> getNutritionalStandard(HashMap<String, Object> map);
}
