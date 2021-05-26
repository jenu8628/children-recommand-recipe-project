package com.webapp.formychild.dto;

public class PreferenceDto {
    private String preference_id;               // 선호 아이디
    private String preference_childId;          // 아이 아이디
    private String preference_foodIngredientId; // 식재료 아이디

    public PreferenceDto() {
    }

    public PreferenceDto(String preference_id, String preference_childId, String preference_foodIngredientId) {
        this.preference_id = preference_id;
        this.preference_childId = preference_childId;
        this.preference_foodIngredientId = preference_foodIngredientId;
    }

    public String getPreference_id() {
        return preference_id;
    }

    public void setPreference_id(String preference_id) {
        this.preference_id = preference_id;
    }

    public String getPreference_childId() {
        return preference_childId;
    }

    public void setPreference_childId(String preference_childId) {
        this.preference_childId = preference_childId;
    }

    public String getPreference_foodIngredientId() {
        return preference_foodIngredientId;
    }

    public void setPreference_foodIngredientId(String preference_foodIngredientId) {
        this.preference_foodIngredientId = preference_foodIngredientId;
    }

    @Override
    public String toString() {
        return "PreferenceDto{" +
                "preference_id='" + preference_id + '\'' +
                ", preference_childId='" + preference_childId + '\'' +
                ", preference_foodIngredientId='" + preference_foodIngredientId + '\'' +
                '}';
    }
}
