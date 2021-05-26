package com.webapp.formychild.controller;

import com.webapp.formychild.dto.ParentDto;
import com.webapp.formychild.help.NumberResult;
import com.webapp.formychild.service.JwtService;
import com.webapp.formychild.service.ParentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/parent")
@Api(value = "Parent Controller", description = "Parent Controller")
public class ParentController {

    @Autowired
    private ParentService parentService;

    public static final Logger logger = LoggerFactory.getLogger(ParentController.class);

    @ApiOperation(value = "부모 회원가입", response = NumberResult.class,
            notes = "Try it out => joinParent(String parent_email, String parent_pwd, String parent_name, String parent_tel, String parent_birth)")
    @RequestMapping(value = "/joinParent", method = RequestMethod.POST)
    private ResponseEntity<NumberResult> joinParent(@RequestParam(value = "parent_email") String parent_email,
                                                    @RequestParam(value = "parent_pwd") String parent_pwd,
                                                    @RequestParam(value = "parent_name") String parent_name,
                                                    @RequestParam(value = "parent_tel") String parent_tel,
                                                    @RequestParam(value = "parent_birth") String parent_birth) throws SQLException {
        NumberResult ns = new NumberResult();
        ParentDto parent = new ParentDto();

        try {
            parent.setParent_email(parent_email);
            parent.setParent_pwd(parent_pwd);
            parent.setParent_name(parent_name);
            parent.setParent_tel(parent_tel);
            parent.setParent_birth(parent_birth);

            parentService.joinParent(parent);
            ns.setValue("joinParent", 1, "succ");
        } catch (Exception e) {
            e.printStackTrace();
            ns.setValue("joinParent", 0, "fail");
            return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<NumberResult>(ns, HttpStatus.OK);
    }

    @ApiOperation(value = "부모 회원가입시 이메일 중복 체크", response = NumberResult.class,
            notes = "Try it out => checkForDuplicateEmail(string input_email)")
    @RequestMapping(value = "/checkForDuplicateEmail", method = RequestMethod.POST)
    private ResponseEntity<NumberResult> checkForDuplicateEmail(@RequestParam(value = "input_email") String input_email) {
        NumberResult ns = new NumberResult();
        try {
            if (parentService.checkForDuplicateEmail(input_email) == 0) {
                // 이메일 중복 없음
                ns.setValue("checkForDuplicateEmail", 1, "succ");
            } else {
                // 이메일 중복
                ns.setValue("checkForDuplicateEmail", 0, "fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<NumberResult>(ns, HttpStatus.OK);
    }

    @ApiOperation(value = "부모 회원 탈퇴", response = NumberResult.class)
    @RequestMapping(value = "/leaveParent", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> leaveParent(@RequestParam(value = "parent_id") String parent_id) throws SQLException {
        NumberResult ns = new NumberResult();
        try {
            parentService.leaveParent(parent_id);
            ns.setValue("leaveParent", 1, "succ");
        } catch (Exception e) {
            e.printStackTrace();
            ns.setValue("leaveParent", 0, "fail");
            return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<NumberResult>(ns, HttpStatus.OK);
    }

    @ApiOperation(value = "부모가 입력한 비밀번호와 DB의 비밀번호가 일치하는지 확인", response = NumberResult.class)
    @RequestMapping(value = "/verifyPassword", method = RequestMethod.POST)
    private ResponseEntity<NumberResult> verifyPassword(@RequestParam(value = "parent_id") String parent_id,
                                                       @RequestParam(value = "input_pwd") String input_pwd) {
        NumberResult ns = new NumberResult();

        try {
            if (parentService.verifyPassword(parent_id, input_pwd) == 1) {
                ns.setValue("verifyPassword", 1, "succ");
            } else {
                ns.setValue("verifyPassword", 0, "fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<NumberResult>(ns, HttpStatus.OK);
    }

    @ApiOperation(value = "부모 회원 정보 반환", response = Map.class,
            notes = "Try it out => getParentInfo(parent_id)")
    @RequestMapping(value = "/getParentInfo", method = RequestMethod.GET)
    private ResponseEntity<ParentDto> getParentInfo(@RequestParam(value = "parent_id") String parent_id) throws Exception {
        ParentDto parent = null;
        try {
            parent = parentService.getParentInfo(parent_id);
        } catch (Exception e) {
            logger.error("부모 정보 반환 실패 : {}", e);
            return new ResponseEntity<ParentDto>(parent, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ParentDto>(parent, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "부모 회원의 정보 수정", response = Map.class,
            notes = "Try it out => modifyParentInfo(parent_id, parent_pwd, parent_name, parent_tel, parent_birth)")
    @RequestMapping(value = "/modifyParentInfo", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> modifyParentInfo(@RequestParam(value = "parent_id") String parent_id,
                                                   @RequestParam(value = "parent_pwd") String parent_pwd,
                                                   @RequestParam(value = "parent_name") String parent_name,
                                                   @RequestParam(value = "parent_tel") String parent_tel,
                                                   @RequestParam(value = "parent_birth") String parent_birth) throws Exception {
        NumberResult ns = new NumberResult();
        ParentDto parent = new ParentDto();

        try {
            parent.setParent_id(parent_id);
            parent.setParent_pwd(parent_pwd);
            parent.setParent_name(parent_name);
            parent.setParent_tel(parent_tel);
            parent.setParent_birth(parent_birth);

            parentService.modifyParentInfo(parent);
            ns.setValue("modifyParentInfo", 1, "succ");

        } catch (Exception e) {
            e.printStackTrace();
            ns.setValue("modifyParentInfo", 0, "fail");
            return new ResponseEntity<NumberResult>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<NumberResult>(ns, HttpStatus.OK);
    }

}
