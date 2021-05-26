package com.webapp.formychild.service;

import com.webapp.formychild.dao.AllergyDaoImpl;
import com.webapp.formychild.dto.AllergyDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllergyServiceImpl implements AllergyService{

    @Autowired
    private AllergyDaoImpl allergyDao;

    @Override
    public String getMyChildAllergyId(String allergy_childId) throws Exception {
        return allergyDao.getMyChildAllergyId(allergy_childId);
    }

    @Override
    public void addChildAllergy(String allergy_childId) throws Exception {
        allergyDao.addChildAllergy(allergy_childId);
    }

    @Override
    public List<String> getMyChildAllergy(String allergyDetail_allergyId) throws Exception {
        return allergyDao.getMyChildAllergy(allergyDetail_allergyId);
    }

    @Override
    public void addMyChildAllergy(AllergyDetailDto allergyDetail) throws Exception {
        allergyDao.addMyChildAllergy(allergyDetail);
    }

    @Override
    public void removeMyChildAllergy(AllergyDetailDto allergyDetail) throws Exception {
        allergyDao.removeMyChildAllergy(allergyDetail);
    }
}
