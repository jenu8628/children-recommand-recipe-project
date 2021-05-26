package com.webapp.formychild.controller;

import com.webapp.formychild.dto.ChildDto;
import com.webapp.formychild.dto.ParentDto;
import com.webapp.formychild.help.NumberResult;
import com.webapp.formychild.service.JwtService;
import com.webapp.formychild.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/login")
@Api(value = "Login Controller", description = "Login Controller")
public class LoginController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JavaMailSender javaMailSender;

    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @ApiOperation(value = "부모 로그인, 로그인 성공시 토큰 정보를 Map 형태로 반환한다.",
            response = Map.class,
            notes = "loginByParent(String parent_email, String parent_pwd)")
    @RequestMapping(value = "/loginByParent", method = RequestMethod.POST)
    private ResponseEntity<Map<String, Object>> loginByParent(@RequestParam(value = "parent_email") String parent_email,
                                                              @RequestParam(value = "parent_pwd") String parent_pwd) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        ParentDto parent = new ParentDto();

        try {
            parent.setParent_email(parent_email);
            parent.setParent_pwd(parent_pwd);

            ParentDto loginParent = loginService.loginByParent(parent);

            if (loginParent != null) {
                // jwt.io에서 확인
                // 로그인 성공 시 토큰 생성
                String token = jwtService.createParent(loginParent);
                logger.info("부모 로그인 토큰 정보 : {}", token);

                // parent 정보 Map에 저장
                resultMap.put("auth_token", token);
                resultMap.put("parent_id", loginParent.getParent_id());
                resultMap.put("parent_email", loginParent.getParent_email());

                status = HttpStatus.OK;
            } else {
                resultMap.put("meesage", "부모 로그인 실패, 부모정보 X");
                status = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            logger.error("부모 로그인 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "자식 로그인, 로그인 성공시 토큰 정보를 Map 형태로 반환한다.",
            response = Map.class,
            notes = "loginByChild(String child_id)")
    @RequestMapping(value = "/loginByChild", method = RequestMethod.POST)
    private ResponseEntity<Map<String, Object>> loginByChild(@RequestParam(value = "child_id") String child_id) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        ChildDto child = new ChildDto();

        try {
            child.setChild_id(child_id);

            ChildDto loginChild = loginService.loginByChild(child);

            if (loginChild != null) {
                // jwt.io에서 확인
                // 로그인 성공 시 토큰 생성
                String token = jwtService.createChild(loginChild);
                logger.info("부모 로그인 토큰 정보 : {}", token);

                // child 정보 Map에 저장
                resultMap.put("auth_token", token);
                resultMap.put("child_id", loginChild.getChild_id());

                status = HttpStatus.OK;
            } else {
                resultMap.put("meesage", "자식 로그인 실패, 자식정보 X");
                status = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            logger.error("자식 로그인 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "부모정모(이름, 전화번호)를 입력받아 부모의 이메일을 반환한다",
                    response = String.class,
                    notes = "findEmail(String parent_name, String parent_tel)")
    @RequestMapping(value = "/findEmail", method = RequestMethod.POST)
    public ResponseEntity<String> findEmail(@RequestParam(value = "parent_name") String parent_name,
                                            @RequestParam(value = "parent_tel") String parent_tel){
        String parent_email = null;
        HttpStatus status = null;
        ParentDto parent = new ParentDto();

        try {
            parent.setParent_name(parent_name);
            parent.setParent_tel(parent_tel);

            parent_email = loginService.findEmail(parent);

            if(parent_email == null){
                status = HttpStatus.BAD_REQUEST;
            }else{
                status = HttpStatus.OK;
            }
        }catch (Exception e){
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(parent_email, status);
    }

    @ApiOperation(value = "부모 정보(이메일, 이름, 생년월일)를 입력받아 부모의 비밀번호를 반환, 이메일 전송한다.",
                    response = NumberResult.class,
                    notes = "findPassword(String parent_email, String parent_name, String parent_birth)")
    @RequestMapping(value = "/findPassword", method = RequestMethod.POST)
    public ResponseEntity<String> findPassword(@RequestParam(value = "parent_email") String parent_email,
                                                     @RequestParam(value = "parent_name") String parent_name,
                                                     @RequestParam(value = "parent_birth") String parent_birth){
        HttpStatus status = null;
        NumberResult ns = new NumberResult();
        ParentDto parent = new ParentDto();
        String pwd = null;

        try {
            parent.setParent_email(parent_email);
            parent.setParent_name(parent_name);
            parent.setParent_birth(parent_birth);

            pwd = loginService.findPassword(parent);

            if (pwd != null){
//                MimeMessage message = javaMailSender.createMimeMessage();
//                MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//                // 메일 제목 설정
//                helper.setSubject("[오구오구] 비밀번호 안내 메일입니다.");
//
//                // 수신자 설정
//                helper.setTo(parent.getParent_email());
//
//                // 메일 내용
//                String text = "사용자 비밀번호 안내 메일입니다. <br/> ";
//                text+= "사용자의 비밀번호는 [ "+ pwd + " ] 입니다. <br/><br/>";
//                text+= "즐거운 사이트 이용 바랍니다. :)";
//                helper.setText(text,true);
//
//                // 메일 발송
//                javaMailSender.send(message);

                ns.setValue("비밀번호가 이메일로 발송되었습니다.", 1, "succ");

                status = HttpStatus.OK;
            } else{
                status = HttpStatus.ACCEPTED;
            }
        }catch (Exception e){
            ns.setValue("비밀번호 찾기에 실패했습니다.", 0, "fail");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(pwd, status);
    }
}
