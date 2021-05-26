package com.webapp.formychild.dto;

public class TodoDto {
    private String todo_id;         // 할일 아이디
    private String todo_parentId;   // 부모 아이디
    private String todo_childId;    // 자식 아이디
    private String todo_content;    // 할일 내용
    private int todo_check;         // 체크 여부
    private String todo_date;       // 할일 날짜

    public TodoDto() {
    }

    public TodoDto(String todo_id, String todo_parentId, String todo_childId, String todo_content, int todo_check, String todo_date) {
        this.todo_id = todo_id;
        this.todo_parentId = todo_parentId;
        this.todo_childId = todo_childId;
        this.todo_content = todo_content;
        this.todo_check = todo_check;
        this.todo_date = todo_date;
    }

    public String getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(String todo_id) {
        this.todo_id = todo_id;
    }

    public String getTodo_parentId() {
        return todo_parentId;
    }

    public void setTodo_parentId(String todo_parentId) {
        this.todo_parentId = todo_parentId;
    }

    public String getTodo_childId() {
        return todo_childId;
    }

    public void setTodo_childId(String todo_childId) {
        this.todo_childId = todo_childId;
    }

    public String getTodo_content() {
        return todo_content;
    }

    public void setTodo_content(String todo_content) {
        this.todo_content = todo_content;
    }

    public int getTodo_check() {
        return todo_check;
    }

    public void setTodo_check(int todo_check) {
        this.todo_check = todo_check;
    }

    public String getTodo_date() {
        return todo_date;
    }

    public void setTodo_date(String todo_date) {
        this.todo_date = todo_date;
    }

    @Override
    public String toString() {
        return "TodoDto{" +
                "todo_id='" + todo_id + '\'' +
                ", todo_parentId='" + todo_parentId + '\'' +
                ", todo_childId='" + todo_childId + '\'' +
                ", todo_content='" + todo_content + '\'' +
                ", todo_check=" + todo_check +
                ", todo_date='" + todo_date + '\'' +
                '}';
    }
}
