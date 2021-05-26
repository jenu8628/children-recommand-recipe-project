package com.webapp.formychild.dto;

public class AllergyDto {
    private String allergy_id;      // 알러지 아이디
    private String allergy_childId; // 아이 아이디

    public AllergyDto() {
    }

    public AllergyDto(String allergy_id, String allergy_childId) {
        this.allergy_id = allergy_id;
        this.allergy_childId = allergy_childId;
    }

    public String getAllergy_id() {
        return allergy_id;
    }

    public void setAllergy_id(String allergy_id) {
        this.allergy_id = allergy_id;
    }

    public String getAllergy_childId() {
        return allergy_childId;
    }

    public void setAllergy_childId(String allergy_childId) {
        this.allergy_childId = allergy_childId;
    }

    @Override
    public String toString() {
        return "AllergyDto{" +
                "allergy_id='" + allergy_id + '\'' +
                ", allergy_childId='" + allergy_childId + '\'' +
                '}';
    }
}
