package com.webapp.formychild.controller;

import com.webapp.formychild.dto.ChildDto;
import com.webapp.formychild.dto.ParentDto;
import com.webapp.formychild.dto.TipDto;
import com.webapp.formychild.help.NumberResult;
import com.webapp.formychild.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("api/admin")
@Api(value = "Admin Controller", description = "Admin Controller")
public class AdminController {

    @Autowired
    private AdminService adminService;

    public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @ApiOperation(value = "전체 보호자의 목록을 반환한다.",
            response = List.class,
            notes = "getAllParent()")
    @RequestMapping(value = "/getAllParent", method = RequestMethod.GET)
    public ResponseEntity<List<ParentDto>> getAllParent() {
        List<ParentDto> parents = null;
        HttpStatus status = null;

        try {
            parents = adminService.getAllParent();

            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(parents, status);
    }

    @ApiOperation(value = "보호자의 상세정보를 받아온다.",
            response = ParentDto.class,
            notes = "getParentInfo(String parent_id)")
    @RequestMapping(value = "/getParentInfo", method = RequestMethod.GET)
    public ResponseEntity<ParentDto> getParenInfo(@RequestParam(value = "parent_id") String parent_id) {
        HttpStatus status = null;
        ParentDto parent = null;

        try {
            parent = adminService.getParentInfo(parent_id);

            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(parent, status);
    }

    @ApiOperation(value = "전체 아이의 목록을 반환한다.",
            response = List.class,
            notes = "getAllChild()")
    @RequestMapping(value = "/getAllChild", method = RequestMethod.GET)
    public ResponseEntity<List<ChildDto>> getAllChild() {
        List<ChildDto> children = null;
        HttpStatus status = null;

        try {
            children = adminService.getAllChild();

            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(children, status);
    }

    @ApiOperation(value = "아이의 상세정보를 반환한다.",
            response = ChildDto.class,
            notes = "getChildInfo(String child_id, String child_parentId)")
    @RequestMapping(value = "/getChildInfo", method = RequestMethod.GET)
    public ResponseEntity<ChildDto> getChildInfo(String child_id, String child_parentId) {
        HttpStatus status = null;
        ChildDto child = new ChildDto();
        ChildDto getChild = new ChildDto();

        try {
            child.setChild_id(child_id);
            child.setChild_parentId(child_parentId);

            getChild = adminService.getChildInfo(child);

            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(getChild, status);
    }

    @ApiOperation(value = "오늘의 소식을 입력한다.",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            notes = "addTip(String tip_title, String tip_content, MultipartFile tip_media)")
    @RequestMapping(value = "/addTip", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> addTip(@RequestParam(value = "tip_title") String tip_title,
                                               @RequestParam(value = "tip_content") String tip_content,
                                               @RequestParam(value = "tip_media") MultipartFile tip_media) {
        NumberResult ns = new NumberResult();
        HttpStatus status = null;
        TipDto tip = new TipDto();

        try {
            tip.setTip_title(tip_title);
            tip.setTip_content(tip_content);
            tip.setTip_media(tip_media.getBytes());

            adminService.addTip(tip);

            ns.setValue("오늘의 소식 등록에 성공했습니다.", 1, "succ");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            ns.setValue("오늘의 소식 등록에 실패했습니다.", 0, "fail");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ns, status);
    }

    @ApiOperation(value = "오늘의 소식을 변경한다.",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            notes = "modifyTip(String tip_id, String tip_title, String tip_content, MultipartFile tip_media)")
    @RequestMapping(value = "/modifyTip", method = RequestMethod.PUT)
    public ResponseEntity<NumberResult> modifyTip(@RequestParam(value = "tip_id") String tip_id,
                                                  @RequestParam(value = "tip_title") String tip_title,
                                                  @RequestParam(value = "tip_content") String tip_content,
                                                  @RequestParam(value = "tip_media") MultipartFile tip_media) {
        NumberResult ns = new NumberResult();
        HttpStatus status = null;
        TipDto tip = new TipDto();

        try {
            tip.setTip_id(tip_id);
            tip.setTip_title(tip_title);
            tip.setTip_content(tip_content);
            tip.setTip_media(tip_media.getBytes());

            adminService.modifyTip(tip);

            ns.setValue("오늘의 소식 변경에 성공했습니다.", 1, "succ");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            ns.setValue("오늘의 소식 변경에 실패했습니다.", 0, "fail");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ns, status);
    }

    @ApiOperation(value = "오늘의 소식을 삭제한다.",
            response = NumberResult.class,
            notes = "removeTip(String tip_id)")
    @RequestMapping(value = "/removeTip", method = RequestMethod.DELETE)
    public ResponseEntity<NumberResult> removeTip(@RequestParam(value = "tip_id") String tip_id) {
        NumberResult ns = new NumberResult();
        HttpStatus status = null;

        try {
            adminService.removeTip(tip_id);

            ns.setValue("오늘의 소식 삭제에 성공했습니다.", 1, "succ");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            ns.setValue("오늘의 소식 삭제에 실패했습니다.", 0, "fail");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ns, status);
    }

    @ApiOperation(value = "오늘의 소식 목록을 반환한다.",
            response = List.class,
            notes = "getAllTip()")
    @RequestMapping(value = "/getAllTip", method = RequestMethod.GET)
    public ResponseEntity<List<TipDto>> getAllTip(){
        List<TipDto> tips = null;
        HttpStatus status = null;

        try {
            tips = adminService.getAllTip();

            status = HttpStatus.OK;
        }catch (Exception e){
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(tips, status);
    }

    @ApiOperation(value = "오늘의 소식 정보를 반환한다.",
            response = TipDto.class,
            notes = "getTipInfo(String tip_id)")
    @RequestMapping(value = "/getTipInfo", method = RequestMethod.GET)
    public ResponseEntity<TipDto> getTipInfo(@RequestParam(value = "tip_id") String tip_id){
        HttpStatus status = null;
        TipDto tip = new TipDto();

        try {
            tip = adminService.getTipInfo(tip_id);

            status = HttpStatus.OK;
        }catch (Exception e){
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(tip, status);
    }
}
