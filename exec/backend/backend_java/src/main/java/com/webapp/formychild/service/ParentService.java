package com.webapp.formychild.service;

import com.webapp.formychild.dto.ParentDto;

public interface ParentService {

    // 회원가입
    void joinParent(ParentDto parent) throws Exception;

    // 이메일 중복 체크
    int checkForDuplicateEmail(String input_email) throws Exception;

    // 비밀번호 재확인
    int verifyPassword(String parent_email, String input_pwd) throws Exception;

    // 회원 정보 받아오기
    ParentDto getParentInfo(String parent_id) throws Exception;

    // 회원 정보 수정
    void modifyParentInfo(ParentDto parent) throws Exception;

    // 회원 탈퇴
    void leaveParent(String parent_id) throws Exception;
}
