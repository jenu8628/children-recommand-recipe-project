package com.webapp.formychild.service;

import com.webapp.formychild.dao.TodoDaoImpl;
import com.webapp.formychild.dto.TodoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoDaoImpl TodoDao;

    @Override
    public String addTodo(TodoDto todo) throws Exception {
        return TodoDao.addTodo(todo);
    }

    @Override
    public void removeTodo(String todo_id) throws Exception {
        TodoDao.removeTodo(todo_id);
    }

    @Override
    public void removeDaysTodo(String todo_parentId, String todo_childId, String todo_date) throws Exception {
        TodoDao.removeDaysTodo(todo_parentId, todo_childId, todo_date);
    }

    @Override
    public List<TodoDto> getMyChildTodoList(String todo_parentId, String todo_childId) throws Exception {
        return TodoDao.getMyChildTodoList(todo_parentId, todo_childId);
    }

    @Override
    public void modifyTodo(String todo_id, String todo_content) throws Exception {
        TodoDao.modifyTodo(todo_id, todo_content);
    }

    @Override
    public void checkTodo(String todo_id) throws Exception {
        TodoDao.checkTodo(todo_id);
    }

    @Override
    public void completeTodayTodo(String todo_childId) {
        TodoDao.completeTodayTodo(todo_childId);
    }

    @Override
    public void incompleteTodayTodo(String todo_childId) {
        TodoDao.incompleteTodayTodo(todo_childId);
    }
}
