package com.xiangshangban.transit_service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.transit_service.bean.Login;
@Mapper
public interface LoginMapper {

	int deleteByPrimaryKey(@Param("id")String id,@Param("clientId")String clientId);

	int deleteById(@Param("id")String id);
	
    int insert(Login record);

    int insertSelective(Login record);
    
    int insertLoginSelective(Login record);
    
    Login selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Login record);

    int updateByPrimaryKey(Login record);
    
    int updateStatusByPhone(String phone);
    /**
     * 根据token查询登录信息
     * @param token
     * @return
     */
    Login selectByToken(String token);
    /**
     * 根据qrcode查询登录信息
     * @param token
     * @return
     */
    Login selectByQrcode(String qrcode);
    /**
	 * 根据sessionId查询
	 * @param sessionId
	 * @return
	 */
    Login selectBySessionId(String sessionId);
    
    List<Login> selectByPhone(String phone);
    
    Login selectOneByPhone(String phone);
    
    Login selectOneByPhoneFromApp(String phone);
    
    Login selectOneByPhoneFromWeb(String phone);
    
    Login selectByTokenAndClientId(@Param("token") String token,@Param("clientId") String clientId);
    
    int updateStatusBySessionId(String sessionId);
    
    int updateStatusById(@Param("id")String id,@Param("clientId")String clientId);
    
    
}