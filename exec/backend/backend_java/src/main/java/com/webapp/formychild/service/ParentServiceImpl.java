package com.webapp.formychild.service;

import com.webapp.formychild.dao.ParentDaoImpl;
import com.webapp.formychild.dto.ParentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentServiceImpl implements ParentService{

    @Autowired
    private ParentDaoImpl ParentDao;

    @Override
    public void joinParent(ParentDto parent) throws Exception {
        ParentDao.joinParent(parent);
    }

    @Override
    public int checkForDuplicateEmail(String input_email) throws Exception {
        return ParentDao.checkForDuplicateEmail(input_email);
    }

    @Override
    public int verifyPassword(String parent_id, String input_pwd) throws Exception {
        return ParentDao.verifyPassword(parent_id, input_pwd);
    }

    @Override
    public ParentDto getParentInfo(String parent_id) throws Exception {
        return ParentDao.getParentInfo(parent_id);
    }

    @Override
    public void modifyParentInfo(ParentDto parent) throws Exception {
        ParentDao.modifyParentInfo(parent);
    }

    @Override
    public void leaveParent(String parent_id) throws Exception {
        ParentDao.leaveParent(parent_id);
    }
}
