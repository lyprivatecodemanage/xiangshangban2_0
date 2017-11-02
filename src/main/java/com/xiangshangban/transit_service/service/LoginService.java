package com.xiangshangban.transit_service.service;

import com.xiangshangban.transit_service.bean.Login;

public interface LoginService {
	/**
	 * 根据token查询
	 * @param token
	 * @return
	 */
	Login selectByToken(String token);
	/**
	 * 添加用户登录信息
	 * @param record
	 * @return
	 */
	int insertSelective(Login record);
	/**
	 * 根据sessionId查询
	 * @param sessionId
	 * @return
	 */
	Login selectBySessionId(String sessionId);
	/**
	 * 根据phone查询
	 * @param phone
	 * @return
	 */
	Login selectByPhone(String phone);
	
	
	int updateByPrimaryKeySelective(Login record);
}
