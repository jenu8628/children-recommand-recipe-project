package com.webapp.formychild.dao;

import com.webapp.formychild.dto.TodoDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoDaoImpl {
    String ns = "com.webapp.formychild.dao.todoDao.";

    @Autowired
    private SqlSession sqlSession;

    public String addTodo(TodoDto todo) throws Exception {
        sqlSession.insert(ns + "addTodo", todo);
        String todo_id = sqlSession.selectOne(ns + "getTodoIdAfterAddTodo", todo);
        return todo_id;
    }

    public void removeTodo(String todo_id) throws Exception {
        sqlSession.delete(ns + "removeTodo", todo_id);
    }

    public void removeDaysTodo(String todo_parentId, String todo_childId, String todo_date) throws Exception {
        TodoDto todo = new TodoDto();
        todo.setTodo_parentId(todo_parentId);
        todo.setTodo_childId(todo_childId);
        todo.setTodo_date(todo_date);

        sqlSession.delete(ns + "removeDaysTodo", todo);
    }

    public List<TodoDto> getMyChildTodoList(String todo_parentId, String todo_childId) throws Exception {
        TodoDto todo = new TodoDto();
        todo.setTodo_parentId(todo_parentId);
        todo.setTodo_childId(todo_childId);

        return sqlSession.selectList(ns + "getMyChildTodoList", todo);
    }

    public void modifyTodo(String todo_id, String todo_content) throws Exception {
        TodoDto todo = new TodoDto();
        todo.setTodo_id(todo_id);
        todo.setTodo_content(todo_content);

        sqlSession.update(ns + "modifyTodo", todo);
    }

    public void checkTodo(String todo_id) throws Exception {
        sqlSession.update(ns + "checkTodo", todo_id);
    }

    public void completeTodayTodo(String todo_childId) {
        sqlSession.update(ns + "completeTodayTodo", todo_childId);
    }

    public void incompleteTodayTodo(String todo_childId) {
        sqlSession.update(ns + "incompleteTodayTodo", todo_childId);
    }
}
