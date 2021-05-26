package com.webapp.formychild.dto;

public class RecommendationDto {
    private int recommendation_num;             // 추천 아이디
    private String recommendation_recipeId;     // 추천할 레시피 아이디
    private String recommendation_childId;      // 추천해줄 아이 아이디

    public RecommendationDto() {
    }

    public RecommendationDto(int recommendation_num, String recommendation_recipeId, String recommendation_childId) {
        this.recommendation_num = recommendation_num;
        this.recommendation_recipeId = recommendation_recipeId;
        this.recommendation_childId = recommendation_childId;
    }

    public int getRecommendation_num() {
        return recommendation_num;
    }

    public void setRecommendation_num(int recommendation_num) {
        this.recommendation_num = recommendation_num;
    }

    public String getRecommendation_recipeId() {
        return recommendation_recipeId;
    }

    public void setRecommendation_recipeId(String recommendation_recipeId) {
        this.recommendation_recipeId = recommendation_recipeId;
    }

    public String getRecommendation_childId() {
        return recommendation_childId;
    }

    public void setRecommendation_childId(String recommendation_childId) {
        this.recommendation_childId = recommendation_childId;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "recommendation_num=" + recommendation_num +
                ", recommendation_recipeId='" + recommendation_recipeId + '\'' +
                ", recommendation_childId='" + recommendation_childId + '\'' +
                '}';
    }
}
