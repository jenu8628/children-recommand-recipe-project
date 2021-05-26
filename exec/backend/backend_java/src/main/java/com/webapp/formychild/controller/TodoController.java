package com.webapp.formychild.controller;

import com.webapp.formychild.dto.ParentDto;
import com.webapp.formychild.dto.TodoDto;
import com.webapp.formychild.help.NumberResult;
import com.webapp.formychild.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/todo")
@Api(value = "Todo Controller", description = "Todo Controller")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @ApiOperation(value = "Todo 추가", response = NumberResult.class,
            notes = "Try it out => addTodo(todo_parentId, todo_childId, todo_content)")
    @RequestMapping(value = "/addTodo", method = RequestMethod.POST)
    private ResponseEntity<NumberResult> addTodo(@RequestParam(value = "todo_parentId") String todo_parentId,
                                                 @RequestParam(value = "todo_childId") String todo_childId,
                                                 @RequestParam(value = "todo_content") String todo_content) throws SQLException {
        NumberResult ns = new NumberResult();

        TodoDto todo = new TodoDto();
        todo.setTodo_parentId(todo_parentId);
        todo.setTodo_childId(todo_childId);
        todo.setTodo_content(todo_content);

        String todo_id = "";

        try {
            todo_id = todoService.addTodo(todo);
            ns.setValue("addTodo", 1, todo_id);
        } catch (Exception e) {
            e.printStackTrace();
            ns.setValue("addTodo", 0, "fail");
            return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<NumberResult>(ns, HttpStatus.OK);
    }

    @ApiOperation(value = "Todo 수정", response = NumberResult.class,
            notes = "Try it out => modifyTodo(todo_id, todo_content)")
    @RequestMapping(value = "/modifyTodo", method = RequestMethod.POST)
    private ResponseEntity<NumberResult> modifyTodo(@RequestParam(value = "todo_id") String todo_id,
                                                 @RequestParam(value = "todo_content") String todo_content) throws SQLException {
        NumberResult ns = new NumberResult();
        try {
            todoService.modifyTodo(todo_id, todo_content);
            ns.setValue("modifyTodo", 1, todo_id);
        } catch (Exception e) {
            e.printStackTrace();
            ns.setValue("modifyTodo", 0, "fail");
            return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<NumberResult>(ns, HttpStatus.OK);
    }

    @ApiOperation(value = "todo 삭제", response = NumberResult.class)
    @RequestMapping(value = "/removeTodo", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> removeTodo(@RequestParam(value = "todo_id") String todo_id) throws SQLException {
        NumberResult ns = new NumberResult();
        try {
            todoService.removeTodo(todo_id);
            ns.setValue("removeTodo", 1, "succ");
        } catch (Exception e) {
            e.printStackTrace();
            ns.setValue("removeTodo", 0, "fail");
            return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<NumberResult>(ns, HttpStatus.OK);
    }

    @ApiOperation(value = "todo 삭제 - 선택한 날짜에 해당하는 todo 전체 삭제", response = NumberResult.class)
    @RequestMapping(value = "/removeDaysTodo", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> removeDaysTodo(@RequestParam(value = "todo_parentId") String todo_parentId,
                                                       @RequestParam(value = "todo_childId") String todo_childId,
                                                       @RequestParam(value = "todo_date") String todo_date) throws SQLException {
        NumberResult ns = new NumberResult();
        try {
            todoService.removeDaysTodo(todo_parentId, todo_childId, todo_date);
            ns.setValue("removeDaysTodo", 1, "succ");
        } catch (Exception e) {
            e.printStackTrace();
            ns.setValue("removeDaysTodo", 0, "fail");
            return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<NumberResult>(ns, HttpStatus.OK);
    }

    @ApiOperation(value = "Todo List 반환", response = Map.class,
            notes = "Try it out => getMyChildTodoList(todo_parentId, todo_childId)")
    @RequestMapping(value = "/getMyChildTodoList", method = RequestMethod.GET)
    private ResponseEntity<List<TodoDto>> getMyChildTodoList(@RequestParam(value = "todo_parentId") String todo_parentId,
                                                             @RequestParam(value = "todo_childId") String todo_childId) throws Exception {
        NumberResult ns = new NumberResult();
        List<TodoDto> todoList = null;

        try {
            todoList = todoService.getMyChildTodoList(todo_parentId, todo_childId);
            ns.setValue("getMyChildTodoList", todoList.size(), "succ");
        } catch (Exception e) {
            ns.setValue("getMyChildTodoList", 0, "fail");
            return new ResponseEntity<List<TodoDto>>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<TodoDto>>(todoList, HttpStatus.OK);
    }

    @ApiOperation(value = "todo 체크", response = NumberResult.class)
    @RequestMapping(value = "/checkTodo", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> checkTodo(@RequestParam(value = "todo_id") String todo_id) throws SQLException {
        NumberResult ns = new NumberResult();
        try {
            todoService.checkTodo(todo_id);
            ns.setValue("checkTodo", 1, "succ");
        } catch (Exception e) {
            e.printStackTrace();
            ns.setValue("checkTodo", 0, "fail");
            return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<NumberResult>(ns, HttpStatus.OK);
    }

    @ApiOperation(value = "today todo 모두 완료 체크", response = NumberResult.class)
    @RequestMapping(value = "/completeTodayTodo", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> completeTodayTodo(@RequestParam(value = "todo_childId") String todo_childId) throws SQLException {
        NumberResult ns = new NumberResult();
        try {
            todoService.completeTodayTodo(todo_childId);
            ns.setValue("completeTodayTodo", 1, "succ");
        } catch (Exception e) {
            e.printStackTrace();
            ns.setValue("completeTodayTodo", 0, "fail");
            return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<NumberResult>(ns, HttpStatus.OK);
    }

    @ApiOperation(value = "today todo 모두 미완료 체크", response = NumberResult.class)
    @RequestMapping(value = "/incompleteTodayTodo", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> incompleteTodayTodo(@RequestParam(value = "todo_childId") String todo_childId) throws SQLException {
        NumberResult ns = new NumberResult();
        try {
            todoService.incompleteTodayTodo(todo_childId);
            ns.setValue("incompleteTodayTodo", 1, "succ");
        } catch (Exception e) {
            e.printStackTrace();
            ns.setValue("incompleteTodayTodo", 0, "fail");
            return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<NumberResult>(ns, HttpStatus.OK);
    }
}
