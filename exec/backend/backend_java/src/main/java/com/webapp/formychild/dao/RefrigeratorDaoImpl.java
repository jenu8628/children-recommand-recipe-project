package com.webapp.formychild.dao;

import com.webapp.formychild.dto.RefrigeratorDetailDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RefrigeratorDaoImpl {
    String ns = "com.webapp.formychild.dao.refrigeratorDao.";

    @Autowired
    private SqlSession sqlSession;

    public String getMyRefrigeratorId(String refrigerator_parentsId) {
        return sqlSession.selectOne(ns+"getMyRefrigeratorId", refrigerator_parentsId);
    }

    public void addRefrigerator(String refrigerator_parentsId) {
        sqlSession.insert(ns+"addRefrigerator", refrigerator_parentsId);
    }

    public List<String> getMyRefrigerator(String refrigeratorDetail_refrigeratorId) {
        return sqlSession.selectList(ns+"getMyRefrigerator", refrigeratorDetail_refrigeratorId);
    }

    public void addMyRefrigerator(RefrigeratorDetailDto refrigeratorDetail) {
        sqlSession.insert(ns+"addMyRefrigerator", refrigeratorDetail);
    }

    public void removeMyRefrigerator(RefrigeratorDetailDto refrigeratorDetail) {
        sqlSession.delete(ns+"removeMyRefrigerator", refrigeratorDetail);
    }
}
