package com.xiangshangban.transit_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.transit_service.bean.Login;
import com.xiangshangban.transit_service.dao.LoginMapper;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginMapper loginMapper;

	/**
	 * 根据token查询
	 * 
	 * @param token
	 * @return
	 */
	@Override
	public Login selectByToken(String token) {

		return loginMapper.selectByToken(token);
	}

	/**
	 * 添加用户登录信息
	 * 
	 * @param record
	 * @return
	 */
	@Override
	public int insertSelective(Login record) {
		return loginMapper.insertSelective(record);
	}

	/**
	 * 根据sessionId查询
	 * 
	 * @param sessionId
	 * @return
	 */
	@Override
	public Login selectBySessionId(String sessionId) {
		return loginMapper.selectBySessionId(sessionId);
	}

	@Override
	public int updateByPrimaryKeySelective(Login record) {
		return loginMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据phone查询
	 * 
	 * @param phone
	 * @return
	 */
	@Override
	public List<Login> selectByPhone(String phone) {
		return loginMapper.selectByPhone(phone);
	}
	
	/**
	 * 根据手机号查询最近的一次登录信息
	 */
	@Override
	public Login selectOneByPhone(String phone) {
		return loginMapper.selectOneByPhone(phone);
	}
	/**
	 * 根据手机号查询最近的一次app登录信息
	 */
	@Override
	public Login selectOneByPhoneFromApp(String phone) {
		return loginMapper.selectOneByPhone(phone);
	}
	/**
	 * 根据手机号查询最近的一次web登录信息
	 */
	@Override
	public Login selectOneByPhoneFromWeb(String phone) {
		return loginMapper.selectOneByPhone(phone);
	}
	
	@Override
	public Login selectByQrcode(String qrcode) {
		return loginMapper.selectByQrcode(qrcode);
	}

	@Override
	public int deleteByPrimatyKey(String id,String clientId) {
	
		return loginMapper.deleteByPrimaryKey(id,clientId);
	}

	@Override
	public int updateStatusByPhone(String phone) {
		
		return loginMapper.updateStatusByPhone(phone);
	}

	@Override
	public Login selectByTokenAndClientId(String token, String clientId) {
		
		return loginMapper.selectByTokenAndClientId(token, clientId);
	}

	@Override
	public int updateStatusBySessionId(String sessionId) {
	
		return loginMapper.updateStatusBySessionId(sessionId);
	}

	@Override
	public int updateStatusById(String id,String clientId) {
		
		return loginMapper.updateStatusById(id,clientId);
	}

	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return loginMapper.deleteById(id);
	}

}
