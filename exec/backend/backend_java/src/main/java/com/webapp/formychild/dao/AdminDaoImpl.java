package com.webapp.formychild.dao;

import com.webapp.formychild.dto.ChildDto;
import com.webapp.formychild.dto.ParentDto;
import com.webapp.formychild.dto.TipDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDaoImpl {
    String ns = "com.webapp.formychild.dao.adminDao.";

    @Autowired
    private SqlSession sqlSession;

    public List<ParentDto> getAllParent() {
        return sqlSession.selectList(ns+"getAllParent");
    }

    public ParentDto getParentInfo(String parent_id) {
        return sqlSession.selectOne(ns+"getParentInfo", parent_id);
    }

    public List<ChildDto> getAllChild() {
        return sqlSession.selectList(ns+"getAllChild");
    }

    public ChildDto getChildInfo(ChildDto child) {
        return sqlSession.selectOne(ns+"getChildInfo", child);
    }

    public void addTip(TipDto tip) {
        sqlSession.insert(ns+"addTip", tip);
    }

    public void modifyTip(TipDto tip) {
        sqlSession.update(ns+"modifyTip", tip);
    }

    public void removeTip(String tip_id) {
        sqlSession.delete(ns+"removeTip", tip_id);
    }

    public List<TipDto> getAllTip() {
        return sqlSession.selectList(ns+"getAllTip");
    }

    public TipDto getTipInfo(String tip_id) {
        return sqlSession.selectOne(ns+"getTipInfo", tip_id);
    }
}
