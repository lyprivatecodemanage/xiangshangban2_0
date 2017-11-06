package com.xiangshangban.transit_service.service;

import com.xiangshangban.transit_service.bean.UserCompanyKey;

/**
 * Created by mian on 2017/11/6.
 */
public interface UserCompanyService {

    int deleteByPrimaryKey(UserCompanyKey key);

    int insert(UserCompanyKey record);

    int insertSelective(UserCompanyKey record);
}
