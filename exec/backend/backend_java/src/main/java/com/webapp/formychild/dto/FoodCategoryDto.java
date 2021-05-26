package com.webapp.formychild.dto;

public class FoodCategoryDto {
    private String foodCategory_id;     // 카테고리 아이디
    private String foodCategory_name;   // 카테고리명

    public FoodCategoryDto() {
    }

    public FoodCategoryDto(String foodCategory_id, String foodCategory_name) {
        this.foodCategory_id = foodCategory_id;
        this.foodCategory_name = foodCategory_name;
    }

    public String getFoodCategory_id() {
        return foodCategory_id;
    }

    public void setFoodCategory_id(String foodCategory_id) {
        this.foodCategory_id = foodCategory_id;
    }

    public String getFoodCategory_name() {
        return foodCategory_name;
    }

    public void setFoodCategory_name(String foodCategory_name) {
        this.foodCategory_name = foodCategory_name;
    }

    @Override
    public String toString() {
        return "FoodCategoryDto{" +
                "foodCategory_id='" + foodCategory_id + '\'' +
                ", foodCategory_name='" + foodCategory_name + '\'' +
                '}';
    }
}
