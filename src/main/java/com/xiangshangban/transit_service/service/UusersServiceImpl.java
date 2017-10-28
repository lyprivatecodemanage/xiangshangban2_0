package com.xiangshangban.transit_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.dao.UusersMapper;
@Service("usersService")
public class UusersServiceImpl implements UusersService {
	@Autowired
	private UusersMapper uusersMapper;
	@Override
	public Uusers selectByPrimaryKey(String userid) {
		
		return uusersMapper.selectByPrimaryKey(userid);
	}

}
