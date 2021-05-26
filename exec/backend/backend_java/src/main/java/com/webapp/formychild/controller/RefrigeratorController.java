package com.webapp.formychild.controller;

import com.webapp.formychild.dto.RefrigeratorDetailDto;
import com.webapp.formychild.help.NumberResult;
import com.webapp.formychild.service.RefrigeratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/refrigerator")
@Api(value = "Refrigerator Controller", description = "Refrigerator Controller")
public class RefrigeratorController {

    @Autowired
    private RefrigeratorService refrigeratorService;

    public static final Logger logger = LoggerFactory.getLogger(RefrigeratorController.class);

    @ApiOperation(value = "부모 아이디로 냉장고의 아이디를 반환한다",
            response = String.class,
            notes = "getMyRefrigeratorId(String refrigerator_parentsId)")
    @RequestMapping(value = "/getMyRefrigeratorId", method = RequestMethod.GET)
    public ResponseEntity<String> getMyRefrigeratorId(@RequestParam(value = "refrigerator_parentsId") String refrigerator_parentsId) {
        HttpStatus status = null;
        String refrigerator_id = null;

        try {
            refrigerator_id = refrigeratorService.getMyRefrigeratorId(refrigerator_parentsId);

            if (refrigerator_id == null) {
                refrigeratorService.addRefrigerator(refrigerator_parentsId);
                refrigerator_id = refrigeratorService.getMyRefrigeratorId(refrigerator_parentsId);

                status = HttpStatus.OK;
            } else {
                status = HttpStatus.OK;
            }
        } catch (Exception e) {
            logger.error("error : {} ", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(refrigerator_id, status);
    }

    @ApiOperation(value = "냉장고에 들어있는 재료목록을 반환한다.",
            response = List.class,
            notes = "getMyRefrigerator(String refrigeratorDetail_refrigeratorId)")
    @RequestMapping(value = "/getMyRefrigerator/{refrigeratorDetail_refrigeratorId}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getMyRefrigerator(@PathVariable("refrigeratorDetail_refrigeratorId") String refrigeratorDetail_refrigeratorId) {
        List<String> ingredients = null;
        HttpStatus status = null;

        try {
            ingredients = refrigeratorService.getMyRefrigerator(refrigeratorDetail_refrigeratorId);

            if (ingredients != null) {
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.ACCEPTED;
            }
        } catch (Exception e) {
            logger.error("error : {} ", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ingredients, status);
    }

    @ApiOperation(value = "냉장고에 재료를 저장한다.",
            response = NumberResult.class,
            notes = "addMyRefrigerator(String refrigeratorDetail_refrigeratorId, String refrigeratorDetail_ingredientName)")
    @RequestMapping(value = "/addMyRefrigerator", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> addMyRefrigerator(@RequestParam(value = "refrigeratorDetail_refrigeratorId") String refrigeratorDetail_refrigeratorId,
                                                          @RequestParam(value = "refrigeratorDetail_ingredientName") String refrigeratorDetail_ingredientName) {
        HttpStatus status = null;
        NumberResult ns = new NumberResult();
        RefrigeratorDetailDto refrigeratorDetail = new RefrigeratorDetailDto();

        try {
            refrigeratorDetail.setRefrigeratorDetail_refrigeratorId(refrigeratorDetail_refrigeratorId);
            refrigeratorDetail.setRefrigeratorDetail_ingredientName(refrigeratorDetail_ingredientName);

            refrigeratorService.addMyRefrigerator(refrigeratorDetail);

            ns.setValue("재료 등록에 성공했습니다.", 1, "succ");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {} ", e);

            ns.setValue("재료 등록에 실패했습니다.", 0, "fail");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ns, status);
    }

    @ApiOperation(value = "냉장고의 재료를 삭제한다.",
            response = NumberResult.class,
            notes = "removeMyRefrigerator(String refrigeratorDetail_refrigeratorId, String refrigeratorDetail_ingredientName)")
    @RequestMapping(value = "/removeMyRefrigerator", method = RequestMethod.DELETE)
    public ResponseEntity<NumberResult> removeMyRefrigerator(@RequestParam(value = "refrigeratorDetail_refrigeratorId") String refrigeratorDetail_refrigeratorId,
                                                             @RequestParam(value = "refrigeratorDetail_ingredientName") String refrigeratorDetail_ingredientName) {
        HttpStatus status = null;
        NumberResult ns = new NumberResult();
        RefrigeratorDetailDto refrigeratorDetail = new RefrigeratorDetailDto();

        try {
            refrigeratorDetail.setRefrigeratorDetail_refrigeratorId(refrigeratorDetail_refrigeratorId);
            refrigeratorDetail.setRefrigeratorDetail_ingredientName(refrigeratorDetail_ingredientName);

            refrigeratorService.removeMyRefrigerator(refrigeratorDetail);

            ns.setValue("재료 삭제에 성공했습니다.",1,"succ");
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            ns.setValue("재료 삭제에 실패했습니다.",0,"fail");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ns, status);
    }
}
