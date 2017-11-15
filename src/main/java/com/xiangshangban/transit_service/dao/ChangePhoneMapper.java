package com.xiangshangban.transit_service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.ChangePhone;

@Mapper
public interface ChangePhoneMapper {
	
	int insertActiviti(ChangePhone changePhone);
	
	ChangePhone selectByPramaryKey(String id);
	
	List<ChangePhone> selectListByApprovalPersonId(String approvalPersonId,String companyId);
	
	int updateVerificationStatus(String id);
}
