package com.webapp.formychild.dto;

import java.util.Arrays;

public class ChildDto {
    private String child_id;            // 아이 아이디
    private String child_parentId;      // 보호자 아이디
    private String child_area;          // 학교 지역
    private String child_schoolName;    // 아이 학교명
    private String child_name;          // 아이 이름
    private String child_sex;           // 아이 성별
    private String child_birth;         // 아이 생년월일
    private float child_height;         // 아이 키
    private float child_weight;         // 아이 몸무게
    private byte[] child_profile;       // 아이 프로필 사진
    private float child_BMI;            // 아이 BMI 지수 /* 수정 */

    public ChildDto() {
    }

    public ChildDto(String child_id, String child_parentId, String child_area, String child_schoolName, String child_name, String child_sex, String child_birth, int child_height, int child_weight, byte[] child_profile, float child_BMI) {
        this.child_id = child_id;
        this.child_parentId = child_parentId;
        this.child_area = child_area;
        this.child_schoolName = child_schoolName;
        this.child_name = child_name;
        this.child_sex = child_sex;
        this.child_birth = child_birth;
        this.child_height = child_height;
        this.child_weight = child_weight;
        this.child_profile = child_profile;
        this.child_BMI = child_BMI;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public String getChild_parentId() {
        return child_parentId;
    }

    public void setChild_parentId(String child_parentId) {
        this.child_parentId = child_parentId;
    }

    public String getChild_area() {
        return child_area;
    }

    public void setChild_area(String child_area) {
        this.child_area = child_area;
    }

    public String getChild_schoolName() {
        return child_schoolName;
    }

    public void setChild_schoolName(String child_schoolName) {
        this.child_schoolName = child_schoolName;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    public String getChild_sex() {
        return child_sex;
    }

    public void setChild_sex(String child_sex) {
        this.child_sex = child_sex;
    }

    public String getChild_birth() {
        return child_birth;
    }

    public void setChild_birth(String child_birth) {
        this.child_birth = child_birth;
    }

    public float getChild_height() {
        return child_height;
    }

    public void setChild_height(float child_height) {
        this.child_height = child_height;
    }

    public float getChild_weight() {
        return child_weight;
    }

    public void setChild_weight(float child_weight) {
        this.child_weight = child_weight;
    }

    public byte[] getChild_profile() {
        return child_profile;
    }

    public void setChild_profile(byte[] child_profile) {
        this.child_profile = child_profile;
    }

    public float getChild_BMI() {
        return child_BMI;
    }

    public void setChild_BMI(float child_BMI) {
        this.child_BMI = child_BMI;
    }

    @Override
    public String toString() {
        return "ChildDto{" +
                "child_id='" + child_id + '\'' +
                ", child_parentId='" + child_parentId + '\'' +
                ", child_area='" + child_area + '\'' +
                ", child_schoolName='" + child_schoolName + '\'' +
                ", child_name='" + child_name + '\'' +
                ", child_sex='" + child_sex + '\'' +
                ", child_birth='" + child_birth + '\'' +
                ", child_height=" + child_height +
                ", child_weight=" + child_weight +
                ", child_profile=" + Arrays.toString(child_profile) +
                ", child_BMI=" + child_BMI +
                '}';
    }
}
