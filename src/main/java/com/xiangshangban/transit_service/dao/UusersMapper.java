package com.xiangshangban.transit_service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.transit_service.bean.Uusers;
@Mapper
public interface UusersMapper {
    int deleteByPrimaryKey(String userid);

    int insert(Uusers record);

    int insertSelective(Uusers record);

    Uusers selectByPrimaryKey(String userId);
    
    Uusers selectByPhone(String phone);
    
    List<String> selectRoles(String phone);
    
    Uusers selectByAccount(String account);

    int updateByPrimaryKeySelective(Uusers record);

    int updateByPrimaryKey(Uusers record);
    
    int updateSmsCode(@Param("phone")String phone,@Param("temporarypwd")String smsCode);
}