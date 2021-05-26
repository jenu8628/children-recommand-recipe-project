package com.webapp.formychild.service;

import com.webapp.formychild.dao.AdminDaoImpl;
import com.webapp.formychild.dto.ChildDto;
import com.webapp.formychild.dto.ParentDto;
import com.webapp.formychild.dto.TipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminDaoImpl adminDao;

    @Override
    public List<ParentDto> getAllParent() throws Exception {
        return adminDao.getAllParent();
    }

    @Override
    public ParentDto getParentInfo(String parent_id) throws Exception {
        return adminDao.getParentInfo(parent_id);
    }

    @Override
    public List<ChildDto> getAllChild() throws Exception {
        return adminDao.getAllChild();
    }

    @Override
    public ChildDto getChildInfo(ChildDto child) throws Exception {
        return adminDao.getChildInfo(child);
    }

    @Override
    public void addTip(TipDto tip) throws Exception {
        adminDao.addTip(tip);
    }

    @Override
    public void modifyTip(TipDto tip) throws Exception {
        adminDao.modifyTip(tip);
    }

    @Override
    public void removeTip(String tip_id) throws Exception {
        adminDao.removeTip(tip_id);
    }

    @Override
    public List<TipDto> getAllTip() throws Exception {
        return adminDao.getAllTip();
    }

    @Override
    public TipDto getTipInfo(String tip_id) throws Exception {
        return adminDao.getTipInfo(tip_id);
    }
}
