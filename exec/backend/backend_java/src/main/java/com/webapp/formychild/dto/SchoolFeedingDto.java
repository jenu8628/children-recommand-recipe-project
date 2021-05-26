package com.webapp.formychild.dto;

public class SchoolFeedingDto {
    private String schoolFeeding_id;
    private String schoolFeeding_area;
    private String schoolFeeding_schoolName;
    private String schoolFeeding_date;
    private String schoolFeeding_type;
    private String schoolFeeding_kcal;
    private String schoolFeeding_carbohydrate;
    private String schoolFeeding_protein;
    private String schoolFeeding_fat;
    private String schoolFeeding_vitaminA;
    private String schoolFeeding_thiamine;
    private String schoolFeeding_riboflavin;
    private String schoolFeeding_vitaminC;
    private String schoolFeeding_calcium;
    private String schoolFeeding_iron;


    public SchoolFeedingDto() {
    }

    public SchoolFeedingDto(String schoolFeeding_id, String schoolFeeding_schoolName, String schoolFeeding_date, String schoolFeeding_type, String schoolFeeding_kcal, String schoolFeeding_carbohydrate, String schoolFeeding_protein, String schoolFeeding_fat) {
        this.schoolFeeding_id = schoolFeeding_id;
        this.schoolFeeding_schoolName = schoolFeeding_schoolName;
        this.schoolFeeding_date = schoolFeeding_date;
        this.schoolFeeding_type = schoolFeeding_type;
        this.schoolFeeding_kcal = schoolFeeding_kcal;
        this.schoolFeeding_carbohydrate = schoolFeeding_carbohydrate;
        this.schoolFeeding_protein = schoolFeeding_protein;
        this.schoolFeeding_fat = schoolFeeding_fat;
    }

    public SchoolFeedingDto(String schoolFeeding_id, String schoolFeeding_area, String schoolFeeding_schoolName, String schoolFeeding_date, String schoolFeeding_type, String schoolFeeding_kcal, String schoolFeeding_carbohydrate, String schoolFeeding_protein, String schoolFeeding_fat, String schoolFeeding_vitaminA, String schoolFeeding_thiamine, String schoolFeeding_riboflavin, String schoolFeeding_vitaminC, String schoolFeeding_calcium, String schoolFeeding_iron) {
        this.schoolFeeding_id = schoolFeeding_id;
        this.schoolFeeding_area = schoolFeeding_area;
        this.schoolFeeding_schoolName = schoolFeeding_schoolName;
        this.schoolFeeding_date = schoolFeeding_date;
        this.schoolFeeding_type = schoolFeeding_type;
        this.schoolFeeding_kcal = schoolFeeding_kcal;
        this.schoolFeeding_carbohydrate = schoolFeeding_carbohydrate;
        this.schoolFeeding_protein = schoolFeeding_protein;
        this.schoolFeeding_fat = schoolFeeding_fat;
        this.schoolFeeding_vitaminA = schoolFeeding_vitaminA;
        this.schoolFeeding_thiamine = schoolFeeding_thiamine;
        this.schoolFeeding_riboflavin = schoolFeeding_riboflavin;
        this.schoolFeeding_vitaminC = schoolFeeding_vitaminC;
        this.schoolFeeding_calcium = schoolFeeding_calcium;
        this.schoolFeeding_iron = schoolFeeding_iron;
    }

    public String getSchoolFeeding_id() {
        return schoolFeeding_id;
    }

    public void setSchoolFeeding_id(String schoolFeeding_id) {
        this.schoolFeeding_id = schoolFeeding_id;
    }

    public String getSchoolFeeding_schoolName() {
        return schoolFeeding_schoolName;
    }

    public void setSchoolFeeding_schoolName(String schoolFeeding_schoolName) {
        this.schoolFeeding_schoolName = schoolFeeding_schoolName;
    }

    public String getSchoolFeeding_date() {
        return schoolFeeding_date;
    }

    public void setSchoolFeeding_date(String schoolFeeding_date) {
        this.schoolFeeding_date = schoolFeeding_date;
    }

    public String getSchoolFeeding_type() {
        return schoolFeeding_type;
    }

    public void setSchoolFeeding_type(String schoolFeeding_type) {
        this.schoolFeeding_type = schoolFeeding_type;
    }

    public String getSchoolFeeding_kcal() {
        return schoolFeeding_kcal;
    }

    public void setSchoolFeeding_kcal(String schoolFeeding_kcal) {
        this.schoolFeeding_kcal = schoolFeeding_kcal;
    }

    public String getSchoolFeeding_carbohydrate() {
        return schoolFeeding_carbohydrate;
    }

    public void setSchoolFeeding_carbohydrate(String schoolFeeding_carbohydrate) {
        this.schoolFeeding_carbohydrate = schoolFeeding_carbohydrate;
    }

    public String getSchoolFeeding_protein() {
        return schoolFeeding_protein;
    }

    public void setSchoolFeeding_protein(String schoolFeeding_protein) {
        this.schoolFeeding_protein = schoolFeeding_protein;
    }

    public String getSchoolFeeding_fat() {
        return schoolFeeding_fat;
    }

    public void setSchoolFeeding_fat(String schoolFeeding_fat) {
        this.schoolFeeding_fat = schoolFeeding_fat;
    }

    @Override
    public String toString() {
        return "SchoolFeedingDto{" +
                "schoolFeeding_id='" + schoolFeeding_id + '\'' +
                ", schoolFeeding_area='" + schoolFeeding_area + '\'' +
                ", schoolFeeding_schoolName='" + schoolFeeding_schoolName + '\'' +
                ", schoolFeeding_date='" + schoolFeeding_date + '\'' +
                ", schoolFeeding_type='" + schoolFeeding_type + '\'' +
                ", schoolFeeding_kcal='" + schoolFeeding_kcal + '\'' +
                ", schoolFeeding_carbohydrate='" + schoolFeeding_carbohydrate + '\'' +
                ", schoolFeeding_protein='" + schoolFeeding_protein + '\'' +
                ", schoolFeeding_fat='" + schoolFeeding_fat + '\'' +
                ", schoolFeeding_vitaminA='" + schoolFeeding_vitaminA + '\'' +
                ", schoolFeeding_thiamine='" + schoolFeeding_thiamine + '\'' +
                ", schoolFeeding_riboflavin='" + schoolFeeding_riboflavin + '\'' +
                ", schoolFeeding_vitaminC='" + schoolFeeding_vitaminC + '\'' +
                ", schoolFeeding_calcium='" + schoolFeeding_calcium + '\'' +
                ", schoolFeeding_iron='" + schoolFeeding_iron + '\'' +
                '}';
    }
}
