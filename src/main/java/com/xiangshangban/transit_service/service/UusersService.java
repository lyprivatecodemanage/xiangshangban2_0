package com.xiangshangban.transit_service.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xiangshangban.transit_service.bean.PersonalInformationVerification;
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
	 
	 List<Uusers> selectListsByPhone(String phone);
	 
	 int selectIdentityAuthentication(String phone,String userName,String companyName);
	 
	 List<PersonalInformationVerification> selectPersonalInformationVerification(String phone,String userName,String postName);
	 
	 int updateSmsCode(String Phone, String smsCode);
	 
	 List<String> selectRoles(String phone);
	 
	 int deleteByPrimaryKey(String userid);

	 int insert(Uusers record);

 	 int updateByPrimaryKey(Uusers record);

	 //注册时检查手机号是否已被注册
	int SelectCountByPhone(String phone);

	int updateByPrimaryKeySelective(Uusers record);

	int insertSelective(Uusers record);
		
	Uusers selectCompanyByToken(String token);

	Uusers selectCompanyBySessionId(String sessionId);
}
