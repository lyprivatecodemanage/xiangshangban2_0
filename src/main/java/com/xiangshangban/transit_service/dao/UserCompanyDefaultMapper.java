package com.xiangshangban.transit_service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.UserCompanyDefault;

@Mapper
public interface UserCompanyDefaultMapper {
	
	List<UserCompanyDefault> selectByUserId(String userId);
	
}
