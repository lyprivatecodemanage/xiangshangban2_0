package com.xiangshangban.transit_service.service;

import com.xiangshangban.transit_service.bean.UserCompanyKey;
import com.xiangshangban.transit_service.dao.UserCompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mian on 2017/11/6.
 */
@Service("userCompanyService")
public class UserCompanyServiceImpl implements UserCompanyService {

    @Autowired
    UserCompanyMapper userCompanyMapper;

    @Override
    public int deleteByPrimaryKey(UserCompanyKey key) {
        return userCompanyMapper.deleteByPrimaryKey(key);
    }

    @Override
    public int insert(UserCompanyKey record) {
        return userCompanyMapper.insert(record);
    }

    @Override
    public int insertSelective(UserCompanyKey record) {
        return userCompanyMapper.insertSelective(record);
    }
}
