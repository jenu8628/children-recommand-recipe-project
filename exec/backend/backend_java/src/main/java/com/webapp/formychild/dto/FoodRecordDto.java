package com.webapp.formychild.dto;

import java.util.Arrays;

public class FoodRecordDto {
    private String foodRecord_id;       // 식단 아이디
    private String foodRecord_parentId;  // 부모 아이디
    private String foodRecord_childId;  // 아이 아이디
    private String foodRecord_recipeId; // 레시피 아이디
    private String foodRecord_date;     // 식단 날짜
    private byte[] foodRecord_media;    // 식단 기록
    private float foodRecord_rating;         // 식단기록 점수
    private float foodRecord_difficulty;    // 식단기록 난이도


    // 월별 데이터를 받아오기 위한 변수
    private String start_date;
    private String end_date;

    public FoodRecordDto() {
    }

    public FoodRecordDto(String foodRecord_id, String foodRecord_childId, String foodRecord_recipeId, String foodRecord_date, byte[] foodRecord_media) {
        this.foodRecord_id = foodRecord_id;
        this.foodRecord_childId = foodRecord_childId;
        this.foodRecord_recipeId = foodRecord_recipeId;
        this.foodRecord_date = foodRecord_date;
        this.foodRecord_media = foodRecord_media;
    }

    public FoodRecordDto(String foodRecord_id, String foodRecord_parentId, String foodRecord_childId, String foodRecord_recipeId, String foodRecord_date, byte[] foodRecord_media, float foodRecord_rating, float foodRecord_difficulty) {
        this.foodRecord_id = foodRecord_id;
        this.foodRecord_parentId = foodRecord_parentId;
        this.foodRecord_childId = foodRecord_childId;
        this.foodRecord_recipeId = foodRecord_recipeId;
        this.foodRecord_date = foodRecord_date;
        this.foodRecord_media = foodRecord_media;
        this.foodRecord_rating = foodRecord_rating;
        this.foodRecord_difficulty = foodRecord_difficulty;
    }

    public String getFoodRecord_id() {
        return foodRecord_id;
    }

    public void setFoodRecord_id(String foodRecord_id) {
        this.foodRecord_id = foodRecord_id;
    }

    public String getFoodRecord_childId() {
        return foodRecord_childId;
    }

    public void setFoodRecord_childId(String foodRecord_childId) {
        this.foodRecord_childId = foodRecord_childId;
    }

    public String getFoodRecord_recipeId() {
        return foodRecord_recipeId;
    }

    public void setFoodRecord_recipeId(String foodRecord_recipeId) {
        this.foodRecord_recipeId = foodRecord_recipeId;
    }

    public String getFoodRecord_date() {
        return foodRecord_date;
    }

    public void setFoodRecord_date(String foodRecord_date) {
        this.foodRecord_date = foodRecord_date;
    }

    public byte[] getFoodRecord_media() {
        return foodRecord_media;
    }

    public void setFoodRecord_media(byte[] foodRecord_media) {
        this.foodRecord_media = foodRecord_media;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getFoodRecord_parentId() {
        return foodRecord_parentId;
    }

    public void setFoodRecord_parentId(String foodRecord_parentId) {
        this.foodRecord_parentId = foodRecord_parentId;
    }

    public float getFoodRecord_rating() {
        return foodRecord_rating;
    }

    public void setFoodRecord_rating(float foodRecord_rating) {
        this.foodRecord_rating = foodRecord_rating;
    }

    public float getFoodRecord_difficulty() {
        return foodRecord_difficulty;
    }

    public void setFoodRecord_difficulty(float foodRecord_difficulty) {
        this.foodRecord_difficulty = foodRecord_difficulty;
    }

    @Override
    public String toString() {
        return "FoodRecordDto{" +
                "foodRecord_id='" + foodRecord_id + '\'' +
                ", foodRecord_parentId='" + foodRecord_parentId + '\'' +
                ", foodRecord_childId='" + foodRecord_childId + '\'' +
                ", foodRecord_recipeId='" + foodRecord_recipeId + '\'' +
                ", foodRecord_date='" + foodRecord_date + '\'' +
                ", foodRecord_media=" + Arrays.toString(foodRecord_media) +
                ", foodRecord_rating=" + foodRecord_rating +
                ", foodRecord_difficulty=" + foodRecord_difficulty +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                '}';
    }
}
