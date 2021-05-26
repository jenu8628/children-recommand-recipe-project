package com.webapp.formychild.dto;

public class RefrigeratorDetailDto {
    private String refrigeratorDetail_id;               // 냉장고상세 아이디
    private String refrigeratorDetail_refrigeratorId;   // 냉장고 아이디
    private String refrigeratorDetail_ingredientName;   // 재료명

    public RefrigeratorDetailDto() {
    }

    public RefrigeratorDetailDto(String refrigeratorDetail_id, String refrigeratorDetail_refrigeratorId, String refrigeratorDetail_ingredientName) {
        this.refrigeratorDetail_id = refrigeratorDetail_id;
        this.refrigeratorDetail_refrigeratorId = refrigeratorDetail_refrigeratorId;
        this.refrigeratorDetail_ingredientName = refrigeratorDetail_ingredientName;
    }

    public String getRefrigeratorDetail_id() {
        return refrigeratorDetail_id;
    }

    public void setRefrigeratorDetail_id(String refrigeratorDetail_id) {
        this.refrigeratorDetail_id = refrigeratorDetail_id;
    }

    public String getRefrigeratorDetail_refrigeratorId() {
        return refrigeratorDetail_refrigeratorId;
    }

    public void setRefrigeratorDetail_refrigeratorId(String refrigeratorDetail_refrigeratorId) {
        this.refrigeratorDetail_refrigeratorId = refrigeratorDetail_refrigeratorId;
    }

    public String getRefrigeratorDetail_ingredientName() {
        return refrigeratorDetail_ingredientName;
    }

    public void setRefrigeratorDetail_ingredientName(String refrigeratorDetail_ingredientName) {
        this.refrigeratorDetail_ingredientName = refrigeratorDetail_ingredientName;
    }

    @Override
    public String toString() {
        return "RefrigeratorDetailDto{" +
                "refrigeratorDetail_id='" + refrigeratorDetail_id + '\'' +
                ", refrigeratorDetail_refrigeratorId='" + refrigeratorDetail_refrigeratorId + '\'' +
                ", refrigeratorDetail_ingredientName='" + refrigeratorDetail_ingredientName + '\'' +
                '}';
    }
}
