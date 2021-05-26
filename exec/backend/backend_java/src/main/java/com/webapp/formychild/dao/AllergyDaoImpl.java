package com.webapp.formychild.dao;

import com.webapp.formychild.dto.AllergyDetailDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AllergyDaoImpl {
    String ns = "com.webapp.formychild.dao.allergyDao.";

    @Autowired
    private SqlSession sqlSession;

    public String getMyChildAllergyId(String allergy_childId) {
        return sqlSession.selectOne(ns+"getMyChildAllergyId", allergy_childId);
    }

    public void addChildAllergy(String allergy_childId) {
        sqlSession.insert(ns+"addChildAllergy", allergy_childId);
    }

    public List<String> getMyChildAllergy(String allergyDetail_allergyId) {
        return sqlSession.selectList(ns+"getMyChildAllergy", allergyDetail_allergyId);
    }

    public void addMyChildAllergy(AllergyDetailDto allergyDetail) {
        sqlSession.insert(ns+"addMyChildAllergy", allergyDetail);
    }

    public void removeMyChildAllergy(AllergyDetailDto allergyDetail) {
        sqlSession.delete(ns+"removeMyChildAllergy", allergyDetail);
    }
}
