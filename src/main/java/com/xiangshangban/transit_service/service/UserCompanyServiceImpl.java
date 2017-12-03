package com.xiangshangban.transit_service.service;

import com.xiangshangban.transit_service.bean.UserCompanyDefault;
import com.xiangshangban.transit_service.dao.UserCompanyDefaultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mian on 2017/11/6.
 */
@Service("userCompanyService")
public class UserCompanyServiceImpl implements UserCompanyService {

    @Autowired
    UserCompanyDefaultMapper userCompanyDefaultMapper;

    @Override
    public int deleteByPrimaryKey(UserCompanyDefault key) {
        return userCompanyDefaultMapper.deleteByPrimaryKey(key);
    }

    @Override
    public int insert(UserCompanyDefault record) {
        return userCompanyDefaultMapper.insert(record);
    }

    @Override
    public int insertSelective(UserCompanyDefault record) {
        return userCompanyDefaultMapper.insertSelective(record);
    }

	@Override
	public UserCompanyDefault selectBySoleUserId(String userId) {
		// TODO Auto-generated method stub
		return userCompanyDefaultMapper.selectBySoleUserId(userId);
	}
		public UserCompanyDefault selectByUserIdAndCompanyId(String userId, String companyId) {
		// TODO Auto-generated method stub
		return userCompanyDefaultMapper.selectByUserIdAndCompanyId(userId, companyId);
	}
}
