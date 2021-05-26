package com.webapp.formychild.controller;

import com.webapp.formychild.dto.AllergyDetailDto;
import com.webapp.formychild.help.NumberResult;
import com.webapp.formychild.service.AllergyService;
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
@RequestMapping("/api/allergy")
@Api(value = "Allergy Controller", description = "Allergy Controller")
public class AllergyController {

    @Autowired
    private AllergyService allergyService;

    public static final Logger logger = LoggerFactory.getLogger(AllergyController.class);

    @ApiOperation(value = "아이 아이디로 알러지 아이디를 반환한다.",
            response = String.class,
            notes = "getMyChildAllergyId(String allergy_childId)")
    @RequestMapping(value = "/getMyChildAllergyId", method = RequestMethod.GET)
    public ResponseEntity<String> getMyChildAllergyId(@RequestParam(value = "allergy_childId") String allergy_childId) {
        HttpStatus status = null;
        String allergy_id = null;

        try {
            allergy_id = allergyService.getMyChildAllergyId(allergy_childId);

            if (allergy_id == null) {
                allergyService.addChildAllergy(allergy_childId);
                allergy_id = allergyService.getMyChildAllergyId(allergy_childId);

                status = HttpStatus.OK;
            } else {
                status = HttpStatus.OK;
            }
        } catch (Exception e) {
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(allergy_id, status);
    }

    @ApiOperation(value = "아이의 알러지 목록을 반환한다.",
            response = List.class,
            notes = "getMyChildAllergy(String allergyDetail_childId)")
    @RequestMapping(value = "/getMyChildAllergy", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getMyChildAllergy(@RequestParam("allergyDetail_childId") String allergyDetail_childId) {
        List<String> allergys = null;
        HttpStatus status = null;

        try {
            allergys = allergyService.getMyChildAllergy(allergyDetail_childId);

            if (allergys != null) {
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.ACCEPTED;
            }
        } catch (Exception e) {
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(allergys, status);
    }

    @ApiOperation(value = "아이의 알러지를 저장한다.",
            response = NumberResult.class,
            notes = "addMyChildAllergy(String allergyDetail_allergyId, String allergyDetail_allergyName)")
    @RequestMapping(value = "/addMyChildAllergy", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> addMyChildAllergy(@RequestParam(value = "allergyDetail_childId") String allergyDetail_childId,
                                                          @RequestParam(value = "allergyDetail_allergyName") String allergyDetail_allergyName) {
        HttpStatus status = null;
        NumberResult ns = new NumberResult();
        AllergyDetailDto allergyDetail = new AllergyDetailDto();

        try {
            allergyDetail.setAllergyDetail_childId(allergyDetail_childId);
            allergyDetail.setAllergyDetail_allergyName(allergyDetail_allergyName);

            allergyService.addMyChildAllergy(allergyDetail);

            ns.setValue("알러지 등록에 성공했습니다.",1,"succ");
            status = HttpStatus.OK;
        }catch (Exception e){
            logger.error("error : {}",e);

            ns.setValue("알러지 등록에 실패했습니다.",0,"fail");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ns, status);
    }

    @ApiOperation(value = "아이의 알러지를 삭제한다.",
            response = NumberResult.class,
            notes = "removeMyChildAllergy(String allergyDetail_allergyId, String allergyDetail_allergyName)")
    @RequestMapping(value = "/removeMyChildAllergy", method = RequestMethod.DELETE)
    public ResponseEntity<NumberResult> removeMyChildAllergy(@RequestParam(value = "allergyDetail_childId") String allergyDetail_childId,
                                                             @RequestParam(value = "allergyDetail_allergyName") String allergyDetail_allergyName){
        HttpStatus status = null;
        NumberResult ns = new NumberResult();
        AllergyDetailDto allergyDetail = new AllergyDetailDto();

        try {
            allergyDetail.setAllergyDetail_childId(allergyDetail_childId);
            allergyDetail.setAllergyDetail_allergyName(allergyDetail_allergyName);

            allergyService.removeMyChildAllergy(allergyDetail);

            ns.setValue("알러지 삭제에 성공했습니다.", 1, "succ");
            status = HttpStatus.OK;
        }catch (Exception e){
            logger.error("error : {}", e);

            ns.setValue("알러지 삭제에 실패했습니다.",0,"fail");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ns, status);
    }

}
