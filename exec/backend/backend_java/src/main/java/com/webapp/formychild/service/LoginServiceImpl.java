package com.webapp.formychild.service;

import com.webapp.formychild.dao.LoginDaoImpl;
import com.webapp.formychild.dto.ChildDto;
import com.webapp.formychild.dto.ParentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private LoginDaoImpl loginDao;

    @Override
    public ParentDto loginByParent(ParentDto parent) throws Exception {
        return loginDao.loginByParent(parent);
    }

    @Override
    public ChildDto loginByChild(ChildDto child) throws Exception {
        return loginDao.loginByChild(child);
    }

    @Override
    public String findEmail(ParentDto parent) throws Exception {
        return loginDao.findEmail(parent);
    }

    @Override
    public String findPassword(ParentDto parent) throws Exception {
        return loginDao.findPassword(parent);
    }
}
