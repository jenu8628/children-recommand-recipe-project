package com.webapp.formychild.controller;

import com.webapp.formychild.dto.*;
import com.webapp.formychild.service.ChildService;
import com.webapp.formychild.service.NutritionService;
import com.webapp.formychild.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/nutrition")
@Api(value = "Nutrition Controller", description = "Nutrition Controller")
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    @Autowired
    private ChildService childService;

    @Autowired
    private RecipeService recipeService;

    @ApiOperation(value = "[오늘의 영양 정보 받아오기] 아이의 오늘 하루 섭취 영양정보를 조회한다.", notes = "getTodayNutritionalInfo(child_id, today) \n\r ex) today = '20210406'", response = NutritionDto.class)
    @RequestMapping(value = "/getTodayNutritionalInfo", method = RequestMethod.GET)
    public ResponseEntity<NutritionDto> getTodayNutritionalInfo(@RequestParam(value = "child_id") String child_id,
                                                                @RequestParam(value = "today") String today) throws Exception {
        System.out.println("들어옴????0");
        NutritionDto todayNutrition = new NutritionDto();
        System.out.println("들어옴?1");
        HttpStatus status;
        float all_kcal = 0;
        float all_carbo = 0;
        float all_protein = 0;
        float all_fat = 0;
        float all_natrium = 0;
        try {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("child_id", child_id);
            map.put("today", today);

            List<FoodRecordDto> today_foodRecord= nutritionService.getTodayFoodRecord(map);    // 식단 기록 데이터
            System.out.println("식단기록ㄲ!!!!!!!!!!");
            // 우리 아이 학교 가져오기
            ChildDto childInfo = childService.getMyChild(child_id);
            String schoolArea = childInfo.getChild_area();
            String schoolName = childInfo.getChild_schoolName();

            map.put("schoolArea", schoolArea);
            map.put("schoolName", schoolName);

            SchoolFeedingDto schoolFeeding = nutritionService.getTodaySchoolFeeding(map);   // 학교 급식 데이터
            System.out.println(today);
            System.out.println(schoolFeeding);
            int todayRecordCnt = today_foodRecord.size();
            for(int i=0;i<todayRecordCnt;i++){
                // 식단 기록 데이터의 recipe_id로 recipe 정보 가져오기
                String recipe_id = today_foodRecord.get(i).getFoodRecord_recipeId();
                RecipeDto recipe = recipeService.getRecipe(recipe_id);
                all_kcal+= Float.parseFloat(recipe.getRecipe_kcal());
                all_carbo+= Float.parseFloat(recipe.getRecipe_carbohydrate());
                all_protein+= Float.parseFloat(recipe.getRecipe_protein());
                all_fat+= Float.parseFloat(recipe.getRecipe_fat());
                all_natrium+= Float.parseFloat(recipe.getRecipe_natrium());
            }

            // 급식데이터가 있으면 영양정보에 add
            if(schoolFeeding!=null){
                String schoolFeeding_kcal = schoolFeeding.getSchoolFeeding_kcal().replace(" Kcal", "");
                all_kcal+=Float.parseFloat(schoolFeeding_kcal);
                all_carbo+=Float.parseFloat(schoolFeeding.getSchoolFeeding_carbohydrate());
                all_protein+=Float.parseFloat(schoolFeeding.getSchoolFeeding_protein());
                all_fat+=Float.parseFloat(schoolFeeding.getSchoolFeeding_fat());
            }

            todayNutrition.setKcal(all_kcal);
            todayNutrition.setCarbohydrate(all_carbo);
            todayNutrition.setProtein(all_protein);
            todayNutrition.setFat(all_fat);
            todayNutrition.setNatrium(all_natrium*(float)0.001); // 나트륨 단위 mg -> g

            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<NutritionDto>(todayNutrition, status);
    }

    @ApiOperation(value = "[아이의 권장 영양 정보 받아오기] 아이의 하루 권장 섭취 영양정보를 조회한다.", notes = "getNutritionalStandard(child_id)", response = NutritionDto.class)
    @RequestMapping(value = "/getNutritionalStandard", method = RequestMethod.GET)
    public ResponseEntity<NutritionDto> getNutritionalStandard(@RequestParam(value = "child_id") String child_id) throws Exception {
        NutritionDto nutritionalStandard = new NutritionDto();
        HttpStatus status = null;

        try {
            ChildDto childInfo = childService.getMyChild(child_id);
            // 아이 나이 , 성별
            int birth_year = Integer.parseInt(childInfo.getChild_birth().split("-")[0]);
            Date today = new Date();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy");
            String to = transFormat.format(today);
            int today_year = Integer.parseInt(to);

            int child_age = today_year-birth_year+1;
            String child_sex = childInfo.getChild_sex();

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("child_age",child_age);
            map.put("child_sex", child_sex);

            HashMap<String, Object> my_nutrition = nutritionService.getNutritionalStandard(map);
            nutritionalStandard.setKcal((float)my_nutrition.get("nutritionStandard_kcal"));
            nutritionalStandard.setCarbohydrate((float)my_nutrition.get("nutritionStandard_carbohydrate"));
            nutritionalStandard.setProtein((float)my_nutrition.get("nutritionStandard_protein"));
            nutritionalStandard.setFat((float)my_nutrition.get("nutritionStandard_fat"));
            nutritionalStandard.setNatrium((float)my_nutrition.get("nutritionStandard_natrium"));

            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<NutritionDto>(nutritionalStandard, status);
        }



}
