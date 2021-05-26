package com.webapp.formychild.service;

import com.webapp.formychild.dto.AllergyDetailDto;

import java.util.List;

public interface AllergyService {

    // 아이 아이디로 알러지 아이디 반환
    String getMyChildAllergyId(String allergy_childId) throws Exception;

    // 아이 알러지 생성
    void addChildAllergy(String allergy_childId) throws Exception;

    // 아이 알러지 목록 반환
    List<String> getMyChildAllergy(String allergyDetail_allergyId) throws Exception;

    // 아이 알러지 등록
    void addMyChildAllergy(AllergyDetailDto allergyDetail) throws Exception;

    // 아이 알러지 삭제
    void removeMyChildAllergy(AllergyDetailDto allergyDetail) throws Exception;
}
