package com.xiangshangban.transit_service.service;


import com.xiangshangban.transit_service.bean.CheckPendingJoinCompany;
import com.xiangshangban.transit_service.dao.CheckPendingJoinCompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mian on 2017/11/4.
 */
@Service("checkPendingJoinCompanyService")
public class CheckPendingJoinCompanyServiceImpl implements CheckPendingJoinCompanyService {

    @Autowired
    CheckPendingJoinCompanyMapper checkPendingJoinCompanyMapper;

    @Override
    public int insertSelective(CheckPendingJoinCompany record) {
        return checkPendingJoinCompanyMapper.insertSelective(record);
    }

    @Override
    public CheckPendingJoinCompany selectByPrimaryKey(String userid) {
        return checkPendingJoinCompanyMapper.selectByPrimaryKey(userid);
    }
}
