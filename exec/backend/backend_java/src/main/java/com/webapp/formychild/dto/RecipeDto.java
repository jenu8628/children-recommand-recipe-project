package com.webapp.formychild.dto;

public class RecipeDto {
    private String recipe_id;           // 레시피 아이디
    private String recipe_name;         // 메뉴명
    private String recipe_method;       // 조리방법
    private String recipe_kind;         // 요리종류
    private float recipe_difficulty;    // 난이도
    private float recipe_rating;         // 점수
    private String recipe_ingredient;   // 재료
    private String recipe_kcal;         // 열량
    private String recipe_carbohydrate; // 탄수화물
    private String recipe_protein;      // 단백질
    private String recipe_fat;          // 지방
    private String recipe_natrium;      // 나트륨
    private String recipe_thumbnail;    // 완성 썸네일

    public RecipeDto() {
    }

    public RecipeDto(String recipe_id, String recipe_name, String recipe_method, String recipe_kind, float recipe_difficulty, float recipe_rating, String recipe_ingredient, String recipe_kcal, String recipe_carbohydrate, String recipe_protein, String recipe_fat, String recipe_natrium, String recipe_thumbnail) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.recipe_method = recipe_method;
        this.recipe_kind = recipe_kind;
        this.recipe_difficulty = recipe_difficulty;
        this.recipe_rating = recipe_rating;
        this.recipe_ingredient = recipe_ingredient;
        this.recipe_kcal = recipe_kcal;
        this.recipe_carbohydrate = recipe_carbohydrate;
        this.recipe_protein = recipe_protein;
        this.recipe_fat = recipe_fat;
        this.recipe_natrium = recipe_natrium;
        this.recipe_thumbnail = recipe_thumbnail;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getRecipe_method() {
        return recipe_method;
    }

    public void setRecipe_method(String recipe_method) {
        this.recipe_method = recipe_method;
    }

    public String getRecipe_kind() {
        return recipe_kind;
    }

    public void setRecipe_kind(String recipe_kind) {
        this.recipe_kind = recipe_kind;
    }

    public float getRecipe_difficulty() {
        return recipe_difficulty;
    }

    public void setRecipe_difficulty(float recipe_difficulty) {
        this.recipe_difficulty = recipe_difficulty;
    }

    public float getRecipe_rating() {
        return recipe_rating;
    }

    public void setRecipe_rating(float recipe_rating) {
        this.recipe_rating = recipe_rating;
    }

    public String getRecipe_ingredient() {
        return recipe_ingredient;
    }

    public void setRecipe_ingredient(String recipe_ingredient) {
        this.recipe_ingredient = recipe_ingredient;
    }

    public String getRecipe_kcal() {
        return recipe_kcal;
    }

    public void setRecipe_kcal(String recipe_kcal) {
        this.recipe_kcal = recipe_kcal;
    }

    public String getRecipe_carbohydrate() {
        return recipe_carbohydrate;
    }

    public void setRecipe_carbohydrate(String recipe_carbohydrate) {
        this.recipe_carbohydrate = recipe_carbohydrate;
    }

    public String getRecipe_protein() {
        return recipe_protein;
    }

    public void setRecipe_protein(String recipe_protein) {
        this.recipe_protein = recipe_protein;
    }

    public String getRecipe_fat() {
        return recipe_fat;
    }

    public void setRecipe_fat(String recipe_fat) {
        this.recipe_fat = recipe_fat;
    }

    public String getRecipe_natrium() {
        return recipe_natrium;
    }

    public void setRecipe_natrium(String recipe_natrium) {
        this.recipe_natrium = recipe_natrium;
    }

    public String getRecipe_thumbnail() {
        return recipe_thumbnail;
    }

    public void setRecipe_thumbnail(String recipe_thumbnail) {
        this.recipe_thumbnail = recipe_thumbnail;
    }


    @Override
    public String toString() {
        return "RecipeDto{" +
                "recipe_id='" + recipe_id + '\'' +
                ", recipe_name='" + recipe_name + '\'' +
                ", recipe_method='" + recipe_method + '\'' +
                ", recipe_kind='" + recipe_kind + '\'' +
                ", recipe_difficulty=" + recipe_difficulty +
                ", recipe_rating=" + recipe_rating +
                ", recipe_ingredient='" + recipe_ingredient + '\'' +
                ", recipe_kcal='" + recipe_kcal + '\'' +
                ", recipe_carbohydrate='" + recipe_carbohydrate + '\'' +
                ", recipe_protein='" + recipe_protein + '\'' +
                ", recipe_fat='" + recipe_fat + '\'' +
                ", recipe_natrium='" + recipe_natrium + '\'' +
                ", recipe_thumbnail='" + recipe_thumbnail + '\'' +
                '}';
    }
}