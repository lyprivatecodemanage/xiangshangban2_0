package com.xiangshangban.transit_service.service;

import java.util.List;

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
	 * 添加用户登录信息
	 * @param record
	 * @return
	 */
	int insertLoginSelective(Login record);
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
	List<Login> selectByPhone(String phone);
	
	Login selectOneByPhone(String phone);
	
    Login selectOneByPhoneFromApp(String phone);
    
    Login selectOneByPhoneFromWeb(String phone);
	
	Login selectByQrcode(String qrcode);
	
	int updateByPrimaryKeySelective(Login record);
	
	int updateStatusByPhone(String phone);
	
	int deleteByPrimatyKey(String id,String clientId);
	
	Login selectByTokenAndClientId(String token, String clientId);
	
	int updateStatusBySessionId(String sessionId);
	
	int updateStatusById(String id,String clientId);
	
	int deleteById(String id);
}
