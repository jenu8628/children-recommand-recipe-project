package com.webapp.formychild.service;

import com.webapp.formychild.dto.ChildDto;
import com.webapp.formychild.dto.ParentDto;
import com.webapp.formychild.dto.TipDto;

import java.util.List;

public interface AdminService {

    // 모든 부모의 목록을 반환한다.
    List<ParentDto> getAllParent() throws Exception;

    // 부모의 상세정보를 받아온다.
    ParentDto getParentInfo(String parent_id) throws Exception;

    // 모든 아이의 목록을 반환한다.
    List<ChildDto> getAllChild() throws Exception;

    // 아이의 상세정보를 받아온다.
    ChildDto getChildInfo(ChildDto child) throws Exception;

    // 오늘의 소식을 등록한다.
    void addTip(TipDto tip) throws Exception;

    // 오늘의 소식을 변경한다.
    void modifyTip(TipDto tip) throws Exception;

    // 오늘의 소식을 삭제한다.
    void removeTip(String tip_id) throws Exception;

    // 오늘의 소식 목록을 반환한다.
    List<TipDto> getAllTip() throws Exception;

    // 오늘의 소식 정보를 반환한다.
    TipDto getTipInfo(String tip_id) throws Exception;
}
