package com.webapp.formychild.dto;

public class RefrigeratorDto {
    private String refrigerator_id;         // 냉장고 아이디
    private String refrigerator_parentId;   // 보호자 아이디

    public RefrigeratorDto() {
    }

    public RefrigeratorDto(String refrigerator_id, String refrigerator_parentId) {
        this.refrigerator_id = refrigerator_id;
        this.refrigerator_parentId = refrigerator_parentId;
    }

    public String getRefrigerator_id() {
        return refrigerator_id;
    }

    public void setRefrigerator_id(String refrigerator_id) {
        this.refrigerator_id = refrigerator_id;
    }

    public String getRefrigerator_parentId() {
        return refrigerator_parentId;
    }

    public void setRefrigerator_parentId(String refrigerator_parentId) {
        this.refrigerator_parentId = refrigerator_parentId;
    }

    @Override
    public String toString() {
        return "RefrigeratorDto{" +
                "refrigerator_id='" + refrigerator_id + '\'' +
                ", refrigerator_parentId='" + refrigerator_parentId + '\'' +
                '}';
    }
}
