package com.webapp.formychild.dto;

public class ParentDto {
    private String parent_id;       // 보호자 아이디
    private String parent_email;    // 보호자 이메일
    private String parent_pwd;      // 보호자 비밀번호
    private String parent_name;     // 보호자 이름
    private String parent_tel;      // 보호자 연락처
    private String parent_birth;    // 보호자 생년월일

    public ParentDto() {
    }

    public ParentDto(String parent_id, String parent_email, String parent_pwd, String parent_name, String parent_tel, String parent_birth) {
        this.parent_id = parent_id;
        this.parent_email = parent_email;
        this.parent_pwd = parent_pwd;
        this.parent_name = parent_name;
        this.parent_tel = parent_tel;
        this.parent_birth = parent_birth;
    }

    public ParentDto(String parent_id, String parent_pwd) {
        this.parent_id = parent_id;
        this.parent_pwd = parent_pwd;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_email() {
        return parent_email;
    }

    public void setParent_email(String parent_email) {
        this.parent_email = parent_email;
    }

    public String getParent_pwd() {
        return parent_pwd;
    }

    public void setParent_pwd(String parent_pwd) {
        this.parent_pwd = parent_pwd;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getParent_tel() {
        return parent_tel;
    }

    public void setParent_tel(String parent_tel) {
        this.parent_tel = parent_tel;
    }

    public String getParent_birth() {
        return parent_birth;
    }

    public void setParent_birth(String parent_birth) {
        this.parent_birth = parent_birth;
    }

    @Override
    public String toString() {
        return "ParentDto{" +
                "parent_id='" + parent_id + '\'' +
                ", parent_email='" + parent_email + '\'' +
                ", parent_pwd='" + parent_pwd + '\'' +
                ", parent_name='" + parent_name + '\'' +
                ", parent_tel='" + parent_tel + '\'' +
                ", parent_birth='" + parent_birth + '\'' +
                '}';
    }
}
