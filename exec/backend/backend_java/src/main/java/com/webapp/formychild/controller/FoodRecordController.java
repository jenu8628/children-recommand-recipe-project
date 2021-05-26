package com.webapp.formychild.controller;

import com.webapp.formychild.dto.FoodRecordDto;
import com.webapp.formychild.dto.RecipeRatingDto;
import com.webapp.formychild.help.NumberResult;
import com.webapp.formychild.service.FoodRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("api/foodRecord")
@Api(value = "FoodRecord Controller", description = "FoodRecord Controller")
public class FoodRecordController {

    @Autowired
    private FoodRecordService foodRecordService;

    public static final Logger logger = LoggerFactory.getLogger(FoodRecordController.class);

    @ApiOperation(value = "요리완료기능, 식단기록에 레시피를 추가한다.",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            notes = "finishRecipe(String foodRecord_parendId, String foodRecord_childId, String foodRecord_recipeId, MultipartFile foodRecord_media," +
                                " float foodRecord_rating, float foodRecord_difficulty, boolean foodRecord_bittersweet, boolean foodRecord_spicy, " +
                                " boolean foodRecord_goso, boolean foodRecord_oily, boolean foodRecord_sweetsour, boolean foodRecord_plain)" +
                    "\n\r 맛(카테고리)들은 체크 되었는지 안되었는지 boolean 형태로 보낸다.")
    @RequestMapping(value = "/finishRecipe", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> finishRecipe(@RequestParam(value = "foodRecord_parentId") String foodRecord_parentId,
                                                     @RequestParam(value = "foodRecord_childId") String foodRecord_childId,
                                                     @RequestParam(value = "foodRecord_recipeId") String foodRecord_recipeId,
                                                     @RequestParam(value = "foodRecord_media", required = false) MultipartFile foodRecord_media,
                                                     @RequestParam(value = "foodRecord_rating") float foodRecord_rating,
                                                     @RequestParam(value = "foodRecord_difficulty") float foodRecord_difficulty) {
        HttpStatus status = null;
        NumberResult ns = new NumberResult();
        FoodRecordDto foodRecord = new FoodRecordDto();

        try {
            foodRecord.setFoodRecord_parentId(foodRecord_parentId);
            foodRecord.setFoodRecord_childId(foodRecord_childId);
            foodRecord.setFoodRecord_recipeId(foodRecord_recipeId);
            if(foodRecord_media != null) {
                foodRecord.setFoodRecord_media(foodRecord_media.getBytes());
            }
            foodRecord.setFoodRecord_rating(foodRecord_rating);
            foodRecord.setFoodRecord_difficulty(foodRecord_difficulty);

            if(foodRecordService.finishRecipe(foodRecord)==1){

                // 식단 등록한 레시피와 해당 child_id가 RecipeRating DB에 있는지 없는지 확인
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("child_id", foodRecord_childId);
                map.put("recipe_id", foodRecord_recipeId);

                RecipeRatingDto isRecipeRating = foodRecordService.getRecipeRating(map);
                if(isRecipeRating == null){ // 없으면 RecipeRating DB에 점수 추가
                    RecipeRatingDto newRecipeRating = new RecipeRatingDto(); // 기록할 데이터
                    newRecipeRating.setRecipeRating_recipeId(foodRecord_recipeId);
                    newRecipeRating.setRecipeRating_parentId(foodRecord_parentId);
                    newRecipeRating.setRecipeRating_childId(foodRecord_childId);
                    newRecipeRating.setRecipeRating_rating(foodRecord_rating);
                    foodRecordService.addRecipeRatingDB(newRecipeRating);
                    ns.setValue("레시피 점수(RecipeRating) 등록에 성공했습니다.", 1, "succ");
                } else{                     // 있으면 RecipeRating DB에 점수 업데이트
                    float prevRating = isRecipeRating.getRecipeRating_rating();
                    float updateRating = (prevRating + foodRecord_rating) / 2 ; // 이전 점수와 들어온 점수의 평균 값으로 세팅
                    isRecipeRating.setRecipeRating_rating(updateRating);
                    foodRecordService.updateRecipeRatingDB(isRecipeRating);
                    ns.setValue("레시피 점수(RecipeRating) 업데이트에 성공했습니다.", 1, "succ");
                }

                // 완료한 레시피의 전체 점수, 난이도 업데이트.
                if(foodRecordService.updateRecipeRating(foodRecord_recipeId)==1){
                    ns.setValue("전체 레시피 점수 업데이트에 성공했습니다.", 1, "succ");
                    if(foodRecordService.updateRecipeDifficulty(foodRecord_recipeId)==1){
                        ns.setValue("전체 레시피 난이도 업데이트에 성공했습니다.", 1, "succ");
                    }else{
                        return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
                    }
                }else{
                    return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
                }

                ns.setValue("식단기록 등록에 성공했습니다.", 1, "succ");
            }
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            ns.setValue("식단기록 등록에 실패했습니다.", 0, "fail");

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ns, status);
    }

    @ApiOperation(value = "아이의 월별 식단기록 리스트를 받아온다.",
            response = List.class,
            notes = "getMyMonthlyFoodRecord(String foodRecord_childId, int year, int month)")
    @RequestMapping(value = "/getMyMonthlyFoodRecord", method = RequestMethod.GET)
    public ResponseEntity<List<FoodRecordDto>> getMyMonthlyFoodRecord(@RequestParam(value = "foodRecord_childId") String foodRecord_childId,
                                                                      @RequestParam(value = "year") int year,
                                                                      @RequestParam(value = "month") int month) {
        List<FoodRecordDto> records = null;
        HttpStatus status = null;
        FoodRecordDto foodRecord = new FoodRecordDto();

        try {
            foodRecord.setFoodRecord_childId(foodRecord_childId);
            String strMonth =  "";
            if(month<10){
                strMonth = "0"+month;
            }else{
                strMonth = ""+month;
            }
            foodRecord.setStart_date(year+"-"+strMonth+"-01");
            if(month == 2){
                foodRecord.setEnd_date(year+"-"+strMonth+"-29");
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                foodRecord.setEnd_date(year+"-"+strMonth+"-30");
            } else {
                foodRecord.setEnd_date(year+"-"+strMonth+"-31");
            }
            records = foodRecordService.getMyMonthlyFoodRecord(foodRecord);
            status = HttpStatus.OK;
        }catch (Exception e){
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(records, status);
    }

    @ApiOperation(value = "식단기록을 삭제한다",
            response = NumberResult.class,
            notes = "removeMyFoodRecord(String foodRecord_id)")
    @RequestMapping(value = "/removeMyFoodRecord", method = RequestMethod.DELETE)
    public ResponseEntity<NumberResult> removeMyFoodRecord(@RequestParam(value = "foodRecord_id") String foodRecord_id){
        NumberResult ns = new NumberResult();
        HttpStatus status = null;

        try {
            foodRecordService.removeMyFoodRecord(foodRecord_id);

            ns.setValue("식단기록 삭제에 성공했습니다.",1,"succ");
            status = HttpStatus.OK;
        }catch (Exception e){
            logger.error("error : {}",e);

            ns.setValue("식단기록 삭제에 실패했습니다.",0,"fail");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ns, status);
    }
}
