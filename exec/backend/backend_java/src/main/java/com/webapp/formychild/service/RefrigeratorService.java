package com.webapp.formychild.service;

import com.webapp.formychild.dto.RefrigeratorDetailDto;

import java.util.List;

public interface RefrigeratorService {

    // 냉장고 아이디 받아오기
    String getMyRefrigeratorId(String refrigerator_parentsId) throws Exception;

    // 생성된 냉장고가 없다면, 냉장고 생성
    void addRefrigerator(String refrigerator_parentsId) throws Exception;

    // 냉장고 안의 재료목록 반환
    List<String> getMyRefrigerator(String refrigeratorDetail_refrigeratorId) throws Exception;

    // 냉장고에 재료 등록
    void addMyRefrigerator(RefrigeratorDetailDto refrigeratorDetail) throws Exception;

    // 냉장고의 재료 삭제
    void removeMyRefrigerator(RefrigeratorDetailDto refrigeratorDetail) throws Exception;
}
