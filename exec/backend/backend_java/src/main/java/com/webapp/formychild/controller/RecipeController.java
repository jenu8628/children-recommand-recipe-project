package com.webapp.formychild.controller;

import com.webapp.formychild.dto.*;
import com.webapp.formychild.help.NumberResult;
import com.webapp.formychild.service.AllergyService;
import com.webapp.formychild.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/recipe")
@Api(value = "Recipe", description = "Recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private AllergyService allergyService;

    /***** 레시피 정보 받아오기 *****/
    @ApiOperation(value = "해당 recipe_id의 레시피 정보를 조회한다.", response = RecipeDto.class, notes = "getRecipe(String recipe_id)")
    @RequestMapping(value = "/getRecipe/{recipe_id}", method = RequestMethod.GET)
    public ResponseEntity<RecipeDto> getRecipeInfo(@PathVariable("recipe_id") String recipe_id) throws Exception {
        RecipeDto recipe = null;
        HttpStatus status;
        try {
            recipe = recipeService.getRecipe(recipe_id);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<RecipeDto>(recipe, status);
    }

    /***** 레시피 과정 받아오기 *****/
    @ApiOperation(value = "[레시피 과정 조회] 해당 recipe_id의 레시피 과정을 조회한다.", response = RecipeProcessDto.class, notes = "getRecipeProcess(String recipe_id)")
    @RequestMapping(value = "/getRecipeProcess/{recipe_id}", method = RequestMethod.GET)
    public ResponseEntity<RecipeProcessDto> getRecipeProcess(@PathVariable("recipe_id") String recipe_id) throws Exception {
        RecipeProcessDto recipeProcess = null;
        HttpStatus status;
        try {
            recipeProcess = recipeService.getRecipeProcess(recipe_id);         // RecipeProcess를 조회한다.
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<RecipeProcessDto>(recipeProcess, status);
    }

    /**** 레시피 이름으로 레시피 목록 받아오기 ****/
    @ApiOperation(value = "[레시피 이름으로 레시피 검색] 레시피 이름으로 레시피 목록을 조회한다.", response = List.class,
            notes = "searchByRecipeName(String recipe_name)")
    @RequestMapping(value = "/searchByRecipeName", method = RequestMethod.GET)
    public ResponseEntity<List<RecipeDto>> searchByRecipeName(@RequestParam(value="recipe_name") String recipe_name) throws Exception {
        List<RecipeDto> recipes = null;
        HttpStatus status;
        try {
            recipes = recipeService.searchByRecipeName(recipe_name);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<List<RecipeDto>>(recipes, status);
    }

    /**** 레시피 재료명으로 레시피 목록 받아오기 ****/
    @ApiOperation(value = "[레시피 재료명으로 레시피 검색] 레시피 이름으로 레시피 목록을 조회한다.", response = List.class,
            notes = "searchByRecipeIngredient(String recipe_ingredient)")
    @RequestMapping(value = "/searchByRecipeIngredient", method = RequestMethod.GET)
    public ResponseEntity<List<RecipeDto>> searchByRecipeIngredient(@RequestParam(value="recipe_ingredient") String recipe_ingredient) throws Exception {
        List<RecipeDto> recipes = null;
        HttpStatus status;
        try {
            recipes = recipeService.searchByRecipeIngredient(recipe_ingredient);
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<List<RecipeDto>>(recipes, status);
    }

    /**** 레시피 스크랩(즐겨찾기) ****/
    @ApiOperation(value = "[레시피 스크랩]", response = NumberResult.class,
            notes = "scrapToRecipe(String scrap_parentId, String scrap_recipeId)")
    @RequestMapping(value = "/scrapToRecipe", method = RequestMethod.GET)
    public ResponseEntity<NumberResult> scrapToRecipe(@RequestParam(value = "scrap_parentId") String scrap_parentId,
                                                  @RequestParam(value = "scrap_recipeId") String scrap_recipeId) {
        NumberResult ns = new NumberResult();
        HttpStatus status = null;
        ScrapDto scrap = new ScrapDto();

        try {
            scrap.setScrap_parentId(scrap_parentId);
            scrap.setScrap_recipeId(scrap_recipeId);

            recipeService.scrapToRecipe(scrap);

            ns.setValue("레시피를 즐겨찾기했습니다", 1, "succ");
            status = HttpStatus.OK;

        } catch (Exception e) {
            ns.setValue("레시피 즐겨찾기에 실패했습니다.", 0, "fail");
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(ns, status);
    }

    /**** 레시피 스크랩(즐겨찾기) 취소 ****/
    @ApiOperation(value = "[레시피 스크랩 취소]", response = NumberResult.class,
            notes = "cancelScrapToRecipe(String scrap_parentId, String scrap_recipeId)")
    @RequestMapping(value = "/cancelScrapToRecipe", method = RequestMethod.GET)
    public ResponseEntity<NumberResult> cancelScrapToRecipe(@RequestParam(value = "scrap_parentId") String scrap_parentId,
                                                         @RequestParam(value = "scrap_recipeId") String scrap_recipeId) {
        NumberResult ns = new NumberResult();
        HttpStatus status = null;
        ScrapDto scrap = new ScrapDto();

        try {
            scrap.setScrap_parentId(scrap_parentId);
            scrap.setScrap_recipeId(scrap_recipeId);

            recipeService.cancelScrapToRecipe(scrap);

            ns.setValue("레시피 즐겨찾기를 취소했습니다.", 1, "succ");
            status = HttpStatus.OK;

        } catch (Exception e) {
            ns.setValue("레시피 즐겨찾기 취소를 실패했습니다.", 0, "fail");

            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(ns, status);
    }

    /**** 스크랩한 레시피 목록 받아오기 ****/
    @ApiOperation(value = "[스크랩한 레시피 목록 반환] 즐겨찾기한 레시피 목록을 반환한다.", response = List.class,
            notes = "getAllScrapRecipe(String scrap_parentId)")
    @RequestMapping(value = "/getAllScrapRecipe", method = RequestMethod.GET)
    public ResponseEntity<List<RecipeDto>> getAllScrapWork(@RequestParam(value = "scrap_parentId") String scrap_parentId) {
        List<RecipeDto> recipes = null;
        HttpStatus status = null;

        try {
            recipes = recipeService.getAllScrapRecipe(scrap_parentId);

            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<List<RecipeDto>>(recipes, status);
    }


    /** 선호재료 레시피 필터링 -> 추천 레시피 */
    @ApiOperation(value = "[선호 재료 레시피 필터링] 선호 재료 기반 추천 레시피 필터링", response = List.class,
            notes = "getRecommendationRecipe(String recommendationRecipe_childId)")
    @RequestMapping(value = "/getRecommendationRecipe", method = RequestMethod.GET)
    public ResponseEntity<List<RecipeDto>> getRecommendationRecipe(@RequestParam(value = "recommendationRecipe_childId") String recommendationRecipe_childId) {
        List<RecipeDto> recipes = null;
        HttpStatus status = null;

        try {
            recipes = recipeService.getRecommendationRecipe(recommendationRecipe_childId);

            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<List<RecipeDto>>(recipes, status);
    }

    /** food category 조회 */
    @ApiOperation(value = "[FoodCategory DB 조회]", response = List.class)
    @RequestMapping(value = "/getAllFoodCategory", method = RequestMethod.GET)
    public ResponseEntity<List<FoodCategoryDto>> getAllFoodCategory() {
        List<FoodCategoryDto> categoryDtos = null;
        HttpStatus status = null;

        try {
            categoryDtos = recipeService.getAllFoodCategory();

            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<List<FoodCategoryDto>>(categoryDtos, status);
    }

    /** food ingredient 조회 */
    @ApiOperation(value = "[FoodIngredient DB 조회]", response = List.class)
    @RequestMapping(value = "/getAllFoodIngredient", method = RequestMethod.GET)
    public ResponseEntity<List<FoodIngredientDto>> getAllFoodIngredient() {
        List<FoodIngredientDto> ingredientDtos = null;
        HttpStatus status = null;

        try {
            ingredientDtos = recipeService.getAllFoodIngredient();

            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<List<FoodIngredientDto>>(ingredientDtos, status);
    }

    /** food ingredient 추가 */
    @ApiOperation(value = "[FoodIngredient DB insert]", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, response = NumberResult.class)
    @RequestMapping(value = "/insertFoodIngredient", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> insertFoodIngredient(@RequestParam(value = "foodingredient_name") String foodingredient_name,
                                                             @RequestParam(value = "foodingredient_img") MultipartFile foodingredient_img) {
        HttpStatus status = null;
        NumberResult ns = new NumberResult();
        FoodIngredientDto foodIngredientDto = new FoodIngredientDto();

        try {
            foodIngredientDto.setFoodIngredient_name(foodingredient_name);
            foodIngredientDto.setFoodIngredient_img(foodingredient_img.getBytes());

            recipeService.insertFoodIngredient(foodIngredientDto);
            ns.setValue("insertFoodIngredient", 1, "succ");
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            System.out.println(e);
            ns.setValue("insertFoodIngredient", 0, "fail");
        }

        return new ResponseEntity<NumberResult>(ns, status);
    }

    /**** 추천 레시피에서 알러지 제거하고 영양정보에 맞는 순으로 조회 ****/
    @ApiOperation(value = "[추천 레시피 조회] 추천 레시피에서 알러지 제거하고 영양정보에 맞는 순으로 조회한다.", response = List.class,
            notes = "searchByRecipeName(String recipe_name)")
    @RequestMapping(value = "/getRecommnendationRecipes", method = RequestMethod.GET)
    public ResponseEntity<List<RecipeDto>> getRecommnendationRecipes(@RequestParam(value="child_id") String child_id) throws Exception {
        List<RecipeDto> recommendationRecipes = null;
        String allergy_id = null;
        List<String> allergys = null;
        HttpStatus status;
        try {
            allergys = allergyService.getMyChildAllergy(child_id);    // 알러지 이름 목록
            if (allergys.size() > 0) { // 알러지가 있으면
                List<RecipeDto> recipesIncludingAllergy = recipeService.getAllRecipeByIngredientList(allergys); // 알러지 제거한 추천레시피
                HashMap<String, Object> map = new HashMap<>();
                map.put("recipesIncludingAllergy", recipesIncludingAllergy);
                map.put("child_id", child_id);
                recommendationRecipes = recipeService.getRecommendationRecipesByAllergy(map);
            }else{
                recommendationRecipes = recipeService.getRecommendationRecipes(child_id);
        }
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<List<RecipeDto>>(recommendationRecipes, status);
    }
}
