package com.xiangshangban.transit_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.transit_service.bean.UniqueLogin;
import com.xiangshangban.transit_service.dao.UniqueLoginMapper;
@Service("uniqueLoginService")
public class UniqueLoginServiceImpl implements UniqueLoginService{
	@Autowired
	private UniqueLoginMapper uniqueLoginMapper;
	
	
	@Override
	public UniqueLogin selectByPhone(String phone) {
		return uniqueLoginMapper.selectByPhone(phone);
	}

	@Override
	public UniqueLogin selectBySessionId(String sessionId) {
		return uniqueLoginMapper.selectBySessionId(sessionId);
	}

	@Override
	public UniqueLogin selectByTokenAndClientId(String token, String clientId) {
		return uniqueLoginMapper.selectByTokenAndClientId(token, clientId);
	}

	@Override
	public int insert(UniqueLogin uniqueLogin) {
		return uniqueLoginMapper.insert(uniqueLogin);
	}

	@Override
	public int deleteByPhone(String phone) {
		return uniqueLoginMapper.deleteByPhone(phone);
	}

	@Override
	public int deleteBySessinId(String sessionId) {
		// TODO Auto-generated method stub
		return uniqueLoginMapper.deleteBySessinId(sessionId);
	}

	@Override
	public UniqueLogin selectByToken(String token) {
		// TODO Auto-generated method stub
		return uniqueLoginMapper.selectByToken(token);
	}

	@Override
	public int deleteByTokenAndClientId(String token, String clientId) {
		// TODO Auto-generated method stub
		return uniqueLoginMapper.deleteByTokenAndClientId(token, clientId);
	}

}
