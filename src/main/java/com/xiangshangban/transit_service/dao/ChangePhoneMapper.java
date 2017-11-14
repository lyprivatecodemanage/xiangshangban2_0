package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.ChangePhone;

@Mapper
public interface ChangePhoneMapper {
	
	int insertActiviti(ChangePhone changePhone);
	
	ChangePhone selectOneByApprovalPersonId(String approvalPersonId);
}
