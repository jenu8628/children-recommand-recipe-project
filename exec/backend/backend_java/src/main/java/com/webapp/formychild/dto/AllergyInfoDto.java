package com.webapp.formychild.dto;

public class AllergyInfoDto {
    private String allergyInfo_id;      // 알러지정보 아이디
    private int allergyInfo_category;   // 알러지 카테고리
    private String allergyInfo_name;    // 알러지 이름

    public AllergyInfoDto() {
    }

    public AllergyInfoDto(String allergyInfo_id, int allergyInfo_category, String allergyInfo_name) {
        this.allergyInfo_id = allergyInfo_id;
        this.allergyInfo_category = allergyInfo_category;
        this.allergyInfo_name = allergyInfo_name;
    }

    public String getAllergyInfo_id() {
        return allergyInfo_id;
    }

    public void setAllergyInfo_id(String allergyInfo_id) {
        this.allergyInfo_id = allergyInfo_id;
    }

    public int getAllergyInfo_category() {
        return allergyInfo_category;
    }

    public void setAllergyInfo_category(int allergyInfo_category) {
        this.allergyInfo_category = allergyInfo_category;
    }

    public String getAllergyInfo_name() {
        return allergyInfo_name;
    }

    public void setAllergyInfo_name(String allergyInfo_name) {
        this.allergyInfo_name = allergyInfo_name;
    }

    @Override
    public String toString() {
        return "AllergyInfoDto{" +
                "allergyInfo_id='" + allergyInfo_id + '\'' +
                ", allergyInfo_category=" + allergyInfo_category +
                ", allergyInfo_name='" + allergyInfo_name + '\'' +
                '}';
    }
}
