package com.webapp.formychild.service;

import com.webapp.formychild.dao.RefrigeratorDaoImpl;
import com.webapp.formychild.dto.RefrigeratorDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefrigeratorServiceImpl implements RefrigeratorService{

    @Autowired
    private RefrigeratorDaoImpl refrigeratorDao;

    @Override
    public String getMyRefrigeratorId(String refrigerator_parentsId) throws Exception{
        return refrigeratorDao.getMyRefrigeratorId(refrigerator_parentsId);
    }

    @Override
    public void addRefrigerator(String refrigerator_parentsId) throws Exception{
        refrigeratorDao.addRefrigerator(refrigerator_parentsId);
    }

    @Override
    public List<String> getMyRefrigerator(String refrigeratorDetail_refrigeratorId) throws Exception {
        return refrigeratorDao.getMyRefrigerator(refrigeratorDetail_refrigeratorId);
    }

    @Override
    public void addMyRefrigerator(RefrigeratorDetailDto refrigeratorDetail) throws Exception {
        refrigeratorDao.addMyRefrigerator(refrigeratorDetail);
    }

    @Override
    public void removeMyRefrigerator(RefrigeratorDetailDto refrigeratorDetail) throws Exception {
        refrigeratorDao.removeMyRefrigerator(refrigeratorDetail);
    }
}
