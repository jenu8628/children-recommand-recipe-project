package com.webapp.formychild.service;

import com.webapp.formychild.dto.ChildDto;
import com.webapp.formychild.dto.ParentDto;

public interface LoginService {

    // 부모 로그인
    ParentDto loginByParent(ParentDto parent) throws Exception;

    // 자식 로그인
    ChildDto loginByChild(ChildDto child) throws Exception;

    // 부모 이메일 찾기
    String findEmail(ParentDto parent) throws Exception;

    // 부모 비밀번호 찾기
    String findPassword(ParentDto parent) throws Exception;
}
