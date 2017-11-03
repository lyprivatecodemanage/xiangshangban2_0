package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.Login;
@Mapper
public interface LoginMapper {

	int deleteByPrimaryKey(String id);

    int insert(Login record);

    int insertSelective(Login record);

    Login selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Login record);

    int updateByPrimaryKey(Login record);
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
    
    Login selectByPhone(String phone);
}