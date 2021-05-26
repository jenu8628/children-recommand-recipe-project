package com.webapp.formychild.dto;

public class ScrapDto {
    private String scrap_id;        // 즐겨찾기 아이디
    private String scrap_parentId;   // 부모 아이디
    private String scrap_recipeId;  // 레시피 아이디

    public ScrapDto() {
    }

    public ScrapDto(String scrap_id, String scrap_parentId, String scrap_recipeId) {
        this.scrap_id = scrap_id;
        this.scrap_parentId = scrap_parentId;
        this.scrap_recipeId = scrap_recipeId;
    }

    public String getScrap_id() {
        return scrap_id;
    }

    public void setScrap_id(String scrap_id) {
        this.scrap_id = scrap_id;
    }

    public String getScrap_parentId() {
        return scrap_parentId;
    }

    public void setScrap_parentId(String scrap_parentId) {
        this.scrap_parentId = scrap_parentId;
    }

    public String getScrap_recipeId() {
        return scrap_recipeId;
    }

    public void setScrap_recipeId(String scrap_recipeId) {
        this.scrap_recipeId = scrap_recipeId;
    }

    @Override
    public String toString() {
        return "ScrapDto{" +
                "scrap_id='" + scrap_id + '\'' +
                ", scrap_parentId='" + scrap_parentId + '\'' +
                ", scrap_recipeId='" + scrap_recipeId + '\'' +
                '}';
    }
}
