package com.webapp.formychild.dao;

import com.webapp.formychild.dto.ChildDto;
import com.webapp.formychild.dto.ParentDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class LoginDaoImpl {
    String ns = "com.webapp.formychild.dao.loginDao.";

    @Autowired
    private SqlSession sqlSession;

    public ParentDto loginByParent(ParentDto parent) throws SQLException {
        return sqlSession.selectOne(ns+"loginByParent", parent);
    }

    public ChildDto loginByChild(ChildDto child) {
        return sqlSession.selectOne(ns+"loginByChild", child);
    }

    public String findEmail(ParentDto parent) {
        return sqlSession.selectOne(ns+"findEmail", parent);
    }

    public String findPassword(ParentDto parent) {
        return sqlSession.selectOne(ns+"findPassword", parent);
    }
}
