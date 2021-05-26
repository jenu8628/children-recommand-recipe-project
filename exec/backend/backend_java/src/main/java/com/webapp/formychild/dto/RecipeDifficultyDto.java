package com.webapp.formychild.dto;

public class RecipeDifficultyDto {
    private String recipeDifficulty_id;          // 난이도 아이디
    private String recipeDifficulty_recipeId;    // 레시피 아이디
    private String recipeDifficulty_parentId;    // 보호자 아이디
    private float recipeDifficulty_Difficulty;     // 난이도

    public RecipeDifficultyDto() {
    }

    public RecipeDifficultyDto(String recipeDifficulty_id, String recipeDifficulty_recipeId, String recipeDifficulty_parentId, float recipeDifficulty_Difficulty) {
        this.recipeDifficulty_id = recipeDifficulty_id;
        this.recipeDifficulty_recipeId = recipeDifficulty_recipeId;
        this.recipeDifficulty_parentId = recipeDifficulty_parentId;
        this.recipeDifficulty_Difficulty = recipeDifficulty_Difficulty;
    }

    public String getRecipeDifficulty_id() {
        return recipeDifficulty_id;
    }

    public void setRecipeDifficulty_id(String recipeDifficulty_id) {
        this.recipeDifficulty_id = recipeDifficulty_id;
    }

    public String getRecipeDifficulty_recipeId() {
        return recipeDifficulty_recipeId;
    }

    public void setRecipeDifficulty_recipeId(String recipeDifficulty_recipeId) {
        this.recipeDifficulty_recipeId = recipeDifficulty_recipeId;
    }

    public String getRecipeDifficulty_parentId() {
        return recipeDifficulty_parentId;
    }

    public void setRecipeDifficulty_parentId(String recipeDifficulty_parentId) {
        this.recipeDifficulty_parentId = recipeDifficulty_parentId;
    }

    public float getRecipeDifficulty_Difficulty() {
        return recipeDifficulty_Difficulty;
    }

    public void setRecipeDifficulty_Difficulty(float recipeDifficulty_Difficulty) {
        this.recipeDifficulty_Difficulty = recipeDifficulty_Difficulty;
    }

    @Override
    public String toString() {
        return "RecipeDifficultyDto{" +
                "recipeDifficulty_id='" + recipeDifficulty_id + '\'' +
                ", recipeDifficulty_recipeId='" + recipeDifficulty_recipeId + '\'' +
                ", recipeDifficulty_parentId='" + recipeDifficulty_parentId + '\'' +
                ", recipeDifficulty_Difficulty=" + recipeDifficulty_Difficulty +
                '}';
    }
}
