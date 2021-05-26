package com.webapp.formychild.dao;

import com.webapp.formychild.dto.ParentDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParentDaoImpl {
    String ns = "com.webapp.formychild.dao.parentDao.";

    @Autowired
    private SqlSession sqlSession;

    public void joinParent(ParentDto parent){
        sqlSession.insert(ns + "joinParent", parent);
    }

    public int checkForDuplicateEmail(String input_email) {
        return sqlSession.selectOne(ns + "checkForDuplicateEmail", input_email);
    }

    public int verifyPassword(String parent_id, String input_pwd) {
        return sqlSession.selectOne(ns + "verifyPassword", new ParentDto(parent_id, input_pwd));
    }

    public ParentDto getParentInfo(String parent_id) {
        return sqlSession.selectOne(ns + "getParentInfo", parent_id);
    }

    public void modifyParentInfo(ParentDto parent) {
        sqlSession.update(ns + "modifyParentInfo", parent);
    }

    public void leaveParent(String parent_id) {
        sqlSession.delete(ns + "leaveParent", parent_id);
    }
}
