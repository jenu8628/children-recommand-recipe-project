package com.webapp.formychild.dto;

public class AllergyDetailDto {
    private String allergyDetail_id;        // 알러지상세 아이디
    private String allergyDetail_childId; // 아이 아이디
    private String allergyDetail_allergyName;      // 알러지 음식

    public AllergyDetailDto() {
    }

    public AllergyDetailDto(String allergyDetail_id, String allergyDetail_childId, String allergyDetail_allergyName) {
        this.allergyDetail_id = allergyDetail_id;
        this.allergyDetail_childId = allergyDetail_childId;
        this.allergyDetail_allergyName = allergyDetail_allergyName;
    }

    public String getAllergyDetail_id() {
        return allergyDetail_id;
    }

    public void setAllergyDetail_id(String allergyDetail_id) {
        this.allergyDetail_id = allergyDetail_id;
    }

    public String getAllergyDetail_childId() {
        return allergyDetail_childId;
    }

    public void setAllergyDetail_childId(String allergyDetail_childId) {
        this.allergyDetail_childId = allergyDetail_childId;
    }

    public String getAllergyDetail_allergyName() {
        return allergyDetail_allergyName;
    }

    public void setAllergyDetail_allergyName(String allergyDetail_allergyName) {
        this.allergyDetail_allergyName = allergyDetail_allergyName;
    }

    @Override
    public String toString() {
        return "AllergyDetailDto{" +
                "allergyDetail_id='" + allergyDetail_id + '\'' +
                ", allergyDetail_childId='" + allergyDetail_childId + '\'' +
                ", allergyDetail_allergyName='" + allergyDetail_allergyName + '\'' +
                '}';
    }
}
