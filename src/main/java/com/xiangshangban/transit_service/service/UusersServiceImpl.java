package com.xiangshangban.transit_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.dao.UusersMapper;
@Service("usersService")
public class UusersServiceImpl implements UusersService {
	@Autowired
	private UusersMapper uusersMapper;
	
	@Override
	public Uusers selectByPhone(String phone) {
		
		return uusersMapper.selectByPhone(phone);
	}
	@Override
	public Uusers selectByAccount(String account) {
		// TODO Auto-generated method stub
		return uusersMapper.selectByAccount(account);
	}
	
	@Override
	public int updateSmsCode(String phone,String smsCode) {
		// TODO Auto-generated method stub
		return uusersMapper.updateSmsCode(phone,smsCode);
	}
	
	
	
	@Override
	public Uusers selectByPrimaryKey(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> selectRoles(String phone) {
		// TODO Auto-generated method stub
		return uusersMapper.selectRoles(phone);
	}

}
