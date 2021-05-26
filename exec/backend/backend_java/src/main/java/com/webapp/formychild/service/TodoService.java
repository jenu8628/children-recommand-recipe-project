package com.webapp.formychild.service;

import com.webapp.formychild.dto.TodoDto;

import java.util.List;

public interface TodoService {

    // todo 추가
    String addTodo(TodoDto todo) throws Exception;

    // todo 삭제 - id
    void removeTodo(String todo_id) throws Exception;

    // todo 삭제 - day
    void removeDaysTodo(String todo_parentId, String todo_childId, String todo_date) throws Exception;

    // todo 리스트 반환
    List<TodoDto> getMyChildTodoList(String todo_parentId, String todo_childId) throws Exception;

    // todo 수정
    void modifyTodo(String todo_id, String todo_content) throws Exception;

    // todo 체크
    void checkTodo(String todo_id) throws Exception;

    void completeTodayTodo(String todo_childId);

    void incompleteTodayTodo(String todo_childId);
}
