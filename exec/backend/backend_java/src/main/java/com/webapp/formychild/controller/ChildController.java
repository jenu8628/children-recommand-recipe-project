package com.webapp.formychild.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.formychild.dto.*;
import com.webapp.formychild.help.NumberResult;
import com.webapp.formychild.service.ChildService;
import com.webapp.formychild.service.FoodRecordService;
import com.webapp.formychild.service.MessageService;
import com.webapp.formychild.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/child")
@Api(value = "Child", description = "Child")
public class ChildController {

    @Autowired
    private ChildService childService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private FoodRecordService foodRecordService;

    /***** 내 아이 추가 하기 *****/
    @ApiOperation(value = "[내 아이 추가] child_parentId(부모)의 아이를 추가한다.", response = String.class,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            notes = "addChild(child_parentId, child_area, child_schoolName, child_name, child_sex, child_birth, child_height(x), child_weight(x), child_profile(x))")
    @RequestMapping(value = "/addChild", method = RequestMethod.POST)
    public ResponseEntity<String> addChild(@RequestParam(value = "child_parentId") String child_parentId,
                                           @RequestParam(value = "child_area") String child_area,
                                           @RequestParam(value = "child_schoolName") String child_schoolName,
                                           @RequestParam(value = "child_name") String child_name,
                                           @RequestParam(value = "child_sex") String child_sex,
                                           @RequestParam(value = "child_birth") String child_birth,
                                           @RequestParam(value = "child_height", required = false) float child_height,
                                           @RequestParam(value = "child_weight", required = false) float child_weight,
                                           @RequestParam(value = "child_profile", required = false) MultipartFile child_profile) throws Exception {
        HttpStatus status = null;
        NumberResult ns = new NumberResult();
        ChildDto child = new ChildDto();
        String child_id = null;

        try {
            child.setChild_parentId(child_parentId);
            child.setChild_area(child_area);
            child.setChild_schoolName(child_schoolName);
            child.setChild_name(child_name);
            child.setChild_sex(child_sex);
            child.setChild_birth(child_birth);
            child.setChild_height(child_height);
            child.setChild_weight(child_weight);

            if (child_profile != null) {
                child.setChild_profile(child_profile.getBytes());
            }

            if (child.getChild_height() != 0 || child.getChild_weight() != 0) { // 키와 몸무게가 들어왔다면 BMI 계산
                child.setChild_BMI(child.getChild_weight() / ((child.getChild_height() * child.getChild_height()) * 10000));
            }
            childService.addChild(child);
            child_id = childService.getChildId(child_parentId);
            ns.setValue("아이를 추가했습니다.", 1, "succ");
            status = HttpStatus.OK;

            try {
                MessageRoomDto messageRoom = new MessageRoomDto();

                messageRoom.setMessageRoom_hashId(UUID.randomUUID().toString());
                messageRoom.setMessageRoom_parentId(child_parentId);
                messageRoom.setMessageRoom_childId(child_id);

                messageService.addMessageRoom(messageRoom);

                ns.setValue("아이 추가 및 채팅방 개설에 성공했습니다.", 1, "succ");
                status = HttpStatus.OK;
            }catch (Exception e){
                ns.setValue("채팅방 개설에 실패했습니다.", 0, "fail");
                status = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            ns.setValue("아이 추가에 실패했습니다.", 0, "fail");
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<String>(child_id, status);
    }

    /***** 내 아이들 조회하기 *****/
    @ApiOperation(value = "[내 아이들 조회] 해당 child_parentId(부모)의 전체 아이들을 조회한다.", response = List.class, notes = "getMyChildren(child_parentId)")
    @RequestMapping(value = "/getMyChildren", method = RequestMethod.GET)
    public ResponseEntity<List<ChildDto>> getMyChildren(@RequestParam(value = "child_parentId") String child_parentId) throws Exception {
        List<ChildDto> children = null;
        HttpStatus status;
        try {
            children = childService.getMyChildren(child_parentId);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<List<ChildDto>>(children, status);
    }

    /***** 내 아이 한명 조회하기 (아이 채널 선택) *****/
    @ApiOperation(value = "[내 아이 조회] 해당 child_id로 아이 한 명을 조회한다.", response = ChildDto.class, notes = "getMyChild(child_id)")
    @RequestMapping(value = "/getMyChild", method = RequestMethod.GET)
    public ResponseEntity<ChildDto> getMyChild(@RequestParam(value = "child_id") String child_id) throws Exception {
        ChildDto child = null;
        HttpStatus status;
        try {
            child = childService.getMyChild(child_id);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<ChildDto>(child, status);
    }

    /***** 내 아이 정보 수정하기 *****/
    @ApiOperation(value = "[내 아이 수정] child_parentId(부모)의 child_id(아이)를 수정한다.", response = NumberResult.class,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            notes = "updateChild(child_id, child_area, child_schoolName, child_name, child_sex, child_age, child_height, child_weight, child_profile, child_BMI(x))")
    @RequestMapping(value = "/updateChild", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> updateChild(@RequestParam(value = "child_id") String child_id,
                                                    @RequestParam(value = "child_area") String child_area,
                                                    @RequestParam(value = "child_schoolName") String child_schoolName,
                                                    @RequestParam(value = "child_name") String child_name,
                                                    @RequestParam(value = "child_sex") String child_sex,
                                                    @RequestParam(value = "child_birth") String child_birth,
                                                    @RequestParam(value = "child_height") float child_height,
                                                    @RequestParam(value = "child_weight") float child_weight,
                                                    @RequestParam(value = "child_profile", required = false) MultipartFile child_profile) throws Exception {
        HttpStatus status = null;
        NumberResult ns = new NumberResult();
        ChildDto child = new ChildDto();
        try {
            child.setChild_id(child_id);
            child.setChild_area(child_area);
            child.setChild_schoolName(child_schoolName);
            child.setChild_name(child_name);
            child.setChild_sex(child_sex);
            child.setChild_birth(child_birth);
            child.setChild_height(child_height);
            child.setChild_weight(child_weight);

            if (child_profile != null) {
                child.setChild_profile(child_profile.getBytes());
            }

            if (child.getChild_height() != 0 || child.getChild_weight() != 0) { // 키와 몸무게가 들어왔다면 BMI 계산
                child.setChild_BMI(child_weight / ((child_height * child_height) * 10000));
            }

            childService.updateChild(child);
            ns.setValue("아이 정보가 수정되었습니다.", 1, "succ");
            status = HttpStatus.OK;
        } catch (Exception e) {
            System.out.println(e);
            ns.setValue("아이 정보 수정에 실패했습니다.", 0, "fail");

            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<NumberResult>(ns, status);
    }

    /***** 내 아이 정보 삭제하기 *****/
    @ApiOperation(value = "[내 아이 삭제] child_parentId(부모)의 child_id(아이)를 삭제한다.", response = NumberResult.class,
            notes = "deleteChild(child_id)")
    @RequestMapping(value = "/deleteChild", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> deleteChild(@RequestParam(value = "child_id") String child_id) {
        NumberResult ns = new NumberResult();
        HttpStatus status = null;

        try {
            childService.deleteChild(child_id);

            ns.setValue("아이 정보를 삭제했습니다", 1, "succ");

            status = HttpStatus.OK;
        } catch (Exception e) {
            ns.setValue("아이 정보 삭제에 실패했습니다", 0, "fail");

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<NumberResult>(ns, status);
    }

    /***** 내 아이 선호 재료 추가하기 *****/
    @ApiOperation(value = "[아이 선호재료 추가] child_id(아이)의 선호재료를 추가한다.", response = NumberResult.class,
            notes = "addPreferenceForChild(List<<HashMap<String,Object>> preference_foodIngredientList, String preference_childId)" +
                    "\n\r HashMap<String, Object> -> 'foodIngredient_id',rating ")
    @RequestMapping(value = "/addPreferenceForChild", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> addPreferenceForChild(@RequestParam(value = "preference_foodIngredientList") String preference_foodIngredientLists,
                                                              @RequestParam(value = "preference_childId") String preference_childId) throws Exception {
        JSONArray jsonArray = new JSONArray(preference_foodIngredientLists);
        List<HashMap<String, Object>> preference_foodIngredientList = new ArrayList<>();
        HttpStatus status = null;
        NumberResult ns = new NumberResult();
        if (jsonArray != null) {
            for (int i=1;i<jsonArray.length();i++){
                HashMap<String, Object> map = null;
                map = new ObjectMapper().readValue(jsonArray.get(i).toString(), HashMap.class);
                preference_foodIngredientList.add(map);
            }
        }
        try {
            int size = preference_foodIngredientList.size();
            List<HashMap<String,Object>> preferences = new ArrayList<>(); // 재료명, 평점 리스트
            ParentDto parent = childService.getMyParent(preference_childId); // 아이의 부모 가져오기
            // 받아온 선호 재료 리스트가 있으면
            if(size > 0){
                for(int i=0;i<size;i++){
                    // 받아온 선호 재료들 중에서 평점이 0인것은 제외하고 진행
                    float rating = Float.parseFloat(preference_foodIngredientList.get(i).get("rating").toString());
                    if(rating>0){
                        PreferenceDto preferenceDto = new PreferenceDto();
                        String foodIngredientId = (String) preference_foodIngredientList.get(i).get("foodIngredient_id");
                        System.out.println(foodIngredientId);
                        if(rating > 3.5) { // 선호 재료 평점이 3.5이상인 것들만 선호재료에 추가한다.
                            preferenceDto.setPreference_foodIngredientId(foodIngredientId);
                            preferenceDto.setPreference_childId(preference_childId);
                            System.out.println(preferenceDto);
                            childService.addPreferenceForChild(preferenceDto);
                            ns.setValue("아이의 선호재료를 추가했습니다.", 1, "succ"); // 아이 선호 재료 preference DB에 추가
                        }
                        HashMap<String, Object> preference = new HashMap<>();
                        String foodIngredient_name = childService.getFoodIngredientNameById(foodIngredientId); // 재료 이름 가져오기
                        preference.put("foodIngredient_name", foodIngredient_name);
                        preference.put("rating", rating);
                        preferences.add(preference);
                    }
                }
                System.out.println("====================레시피 목록 가져오기================");
                System.out.println(preferences.size());
                for(int i=0;i<preferences.size();i++){
                    HashMap<String, Object> preference = preferences.get(i); // 재료명과 평점
                    List<RecipeDto> recipeList = recipeService.searchByRecipeIngredient((String)preference.get("foodIngredient_name"));   // 재료명으로 검색된 모든 레시피 목록 가져온다.
                    for(int j=0;j<recipeList.size();j++){
                        // recipeRating DB에 child_id가 해당 레시피에 평점을 매겼는지 확인.
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("child_id", preference_childId);
                        map.put("recipe_id", recipeList.get(j).getRecipe_id());
                        System.out.println(map);
                        RecipeRatingDto isRecipeRating = foodRecordService.getRecipeRating(map);
                        if(isRecipeRating == null) { // 평점을 매기지 않았으면 RecipeRating DB에 점수 추가
                            RecipeRatingDto recipeRating = new RecipeRatingDto();

                            recipeRating.setRecipeRating_recipeId(recipeList.get(j).getRecipe_id());
                            recipeRating.setRecipeRating_parentId(parent.getParent_id());
                            recipeRating.setRecipeRating_childId(preference_childId);
                            recipeRating.setRecipeRating_rating((float)preference.get("rating"));
                            childService.addRecipeRating(recipeRating); // 아이 선호 재료 포함된 레시피에 평점 부여
                        }else{  // 이미 평점을 매겼으면 RecipeRating DB에 점수 업데이트
                            float prevRating = isRecipeRating.getRecipeRating_rating();
                            float updateRating = (prevRating + (float)preference.get("rating")) / 2 ; // 이전 점수와 들어온 점수의 평균 값으로 세팅
                            isRecipeRating.setRecipeRating_rating(updateRating);
                            foodRecordService.updateRecipeRatingDB(isRecipeRating);
                            ns.setValue("레시피 점수(RecipeRating) 업데이트에 성공했습니다.", 1, "succ");
                        }
                    }
                }
                ns.setValue("아이의 선호재료를 추가했습니다.", 1, "succ");
                status = HttpStatus.OK;

            }else {
                ns.setValue("아이의 선호재료에 선호도를 추가해주세요.", 0, "fail");
                status = HttpStatus.BAD_REQUEST;
            }

        } catch (Exception e) {
            ns.setValue("아이의 선호재료 추가에 실패했습니다.", 0, "fail");
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<NumberResult>(ns, status);
    }

    /***** 내 아이 선호 재료 수정하기 *****/
    @ApiOperation(value = "[아이 선호재료 수정] child_id(아이)의 선호재료를 수정한다.", response = NumberResult.class,
            notes = "updatePreferenceForChild(List<String> preference_foodIngredientIdList, String preference_childId)" +
                    "\n\r List -> 선호 재료 여러개")
    @RequestMapping(value = "/updatePreferenceForChild", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> updatePreferenceForChild(@RequestParam(value = "preference_foodIngredientIdList") List<String> preference_foodIngredientIdList,
                                                                 @RequestParam(value = "preference_childId") String preference_childId) throws Exception {
        HttpStatus status = null;
        NumberResult ns = new NumberResult();
        try {
            // 아이id에 해당하는 선호 재료 모두 삭제
            if (childService.deleteAllPreferences(preference_childId) >= 0) {
                int size = preference_foodIngredientIdList.size();
                // 아이 선호 재료 수만큼 반복
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        PreferenceDto preference = new PreferenceDto();
                        String foodIngredientId = preference_foodIngredientIdList.get(i);
                        preference.setPreference_foodIngredientId(foodIngredientId);

                        preference.setPreference_childId(preference_childId);
                        childService.addPreferenceForChild(preference);
                        ns.setValue("아이의 선호재료를 수정했습니다.", 1, "succ");
                        status = HttpStatus.OK;
                    }
                }
            }
        } catch (Exception e) {
            ns.setValue("아이의 선호재료 수정에 실패했습니다.", 0, "fail");
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<NumberResult>(ns, status);
    }

    /***** 하루의 영양 정보 받아오기 *****/
    @ApiOperation(value = "[오늘의 영양 정보 받아오기]", response = ChildDto.class)
    @RequestMapping(value = "/getTodayNutritionalInfo", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getTodayNutritionalInfo(@RequestParam(value = "child_id") String child_id) throws Exception {
        HashMap<String, Object> n_info = null;
        HttpStatus status;
        try {
            n_info = childService.getTodayNutritionalInfo(child_id);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<HashMap<String, Object>>(n_info, status);
    }
}
