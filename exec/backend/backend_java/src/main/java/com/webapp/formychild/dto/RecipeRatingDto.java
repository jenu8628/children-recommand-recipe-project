package com.webapp.formychild.dto;

public class RecipeRatingDto {
    private String recipeRating_id;         // 레시피 점수(별점) 아이디
    private String recipeRating_recipeId;   // 레시피 아이디
    private String recipeRating_parentId;   // 부모 아이디
    private String recipeRating_childId;    // 아이 아이디
    private float recipeRating_rating;      // 점수(별점)

    public RecipeRatingDto() {
    }

    public RecipeRatingDto(String recipeRating_id, String recipeRating_recipeId, String recipeRating_parentId, String recipeRating_childId, float recipeRating_rating) {
        this.recipeRating_id = recipeRating_id;
        this.recipeRating_recipeId = recipeRating_recipeId;
        this.recipeRating_parentId = recipeRating_parentId;
        this.recipeRating_childId = recipeRating_childId;
        this.recipeRating_rating = recipeRating_rating;
    }

    public String getRecipeRating_id() {
        return recipeRating_id;
    }

    public void setRecipeRating_id(String recipeRating_id) {
        this.recipeRating_id = recipeRating_id;
    }

    public String getRecipeRating_recipeId() {
        return recipeRating_recipeId;
    }

    public void setRecipeRating_recipeId(String recipeRating_recipeId) {
        this.recipeRating_recipeId = recipeRating_recipeId;
    }

    public String getRecipeRating_parentId() {
        return recipeRating_parentId;
    }

    public void setRecipeRating_parentId(String recipeRating_parentId) {
        this.recipeRating_parentId = recipeRating_parentId;
    }

    public String getRecipeRating_childId() {
        return recipeRating_childId;
    }

    public void setRecipeRating_childId(String recipeRating_childId) {
        this.recipeRating_childId = recipeRating_childId;
    }

    public float getRecipeRating_rating() {
        return recipeRating_rating;
    }

    public void setRecipeRating_rating(float recipeRating_rating) {
        this.recipeRating_rating = recipeRating_rating;
    }

    @Override
    public String toString() {
        return "RecipeRatingDto{" +
                "recipeRating_id='" + recipeRating_id + '\'' +
                ", recipeRating_recipeId='" + recipeRating_recipeId + '\'' +
                ", recipeRating_parentId='" + recipeRating_parentId + '\'' +
                ", recipeRating_childId='" + recipeRating_childId + '\'' +
                ", recipeRating_rating=" + recipeRating_rating +
                '}';
    }
}