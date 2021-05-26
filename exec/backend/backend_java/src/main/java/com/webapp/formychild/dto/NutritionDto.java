package com.webapp.formychild.dto;

public class NutritionDto {
    private float kcal;         // 칼로리
    private float carbohydrate; // 탄수화물
    private float protein;      // 단백질
    private float fat;          // 지방
    private float natrium;      // 나트륨

    public NutritionDto() {
    }

    public NutritionDto(float kcal, float carbohydrate, float protein, float fat, float natrium) {
        this.kcal = kcal;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.natrium = natrium;
    }

    public float getKcal() {
        return kcal;
    }

    public void setKcal(float kcal) {
        this.kcal = kcal;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getNatrium() {
        return natrium;
    }

    public void setNatrium(float natrium) {
        this.natrium = natrium;
    }
}
