package com.xiangshangban.transit_service.service;

import com.xiangshangban.transit_service.bean.CheckPendingJoinCompany;

/**
 * Created by mian on 2017/11/4.
 */
public interface CheckPendingJoinCompanyService {
    int insertSelective(CheckPendingJoinCompany record);

    CheckPendingJoinCompany selectByPrimaryKey(String userid,String companyid);
    
    int updateByPrimaryKeySelective(CheckPendingJoinCompany record);

    int updateByPrimaryKey(CheckPendingJoinCompany record);
    
    int deleteById(String userId,String companyid);
}
