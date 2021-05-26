package com.webapp.formychild.dto;

import java.util.Arrays;

public class FoodIngredientDto {
    private String foodIngredient_id;               // 재료 아이디
    private String foodIngredient_name;             // 재료명
    private byte[] foodIngredient_img;              // 재료 이미지

    public FoodIngredientDto() {

    }

    public FoodIngredientDto(String foodIngredient_name, byte[] foodIngredient_img) {
        this.foodIngredient_name = foodIngredient_name;
        this.foodIngredient_img = foodIngredient_img;
    }

    public FoodIngredientDto(String foodIngredient_id, String foodIngredient_name, byte[] foodIngredient_img) {
        this.foodIngredient_id = foodIngredient_id;
        this.foodIngredient_name = foodIngredient_name;
        this.foodIngredient_img = foodIngredient_img;
    }

    public String getFoodIngredient_id() {
        return foodIngredient_id;
    }

    public void setFoodIngredient_id(String foodIngredient_id) {
        this.foodIngredient_id = foodIngredient_id;
    }

    public String getFoodIngredient_name() {
        return foodIngredient_name;
    }

    public void setFoodIngredient_name(String foodIngredient_name) {
        this.foodIngredient_name = foodIngredient_name;
    }

    public byte[] getFoodIngredient_img() {
        return foodIngredient_img;
    }

    public void setFoodIngredient_img(byte[] foodIngredient_img) {
        this.foodIngredient_img = foodIngredient_img;
    }

    @Override
    public String toString() {
        return "FoodIngredientDto{" +
                "foodIngredient_id='" + foodIngredient_id + '\'' +
                ", foodIngredient_name='" + foodIngredient_name + '\'' +
                ", foodIngredient_img=" + Arrays.toString(foodIngredient_img) +
                '}';
    }
}
