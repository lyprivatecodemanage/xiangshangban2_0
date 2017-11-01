package com.xiangshangban.transit_service.service;

import java.util.List;

import com.xiangshangban.transit_service.bean.Uusers;

public interface UusersService {
	
	 Uusers selectByAccount(String account);
	 
	 Uusers selectByPrimaryKey(String userId);
	 /**
	  * 根据phone查询
	  * @param phone
	  * @return
	  */
	 Uusers selectByPhone(String phone);
	 
	 int updateSmsCode(String Phone, String smsCode);
	 
	 List<String> selectRoles(String phone);
	 
}
