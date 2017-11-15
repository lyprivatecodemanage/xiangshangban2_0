package com.xiangshangban.transit_service.service;

import java.util.List;

import com.xiangshangban.transit_service.bean.ChangePhone;

public interface ChangePhoneService {

	int insertActiviti(ChangePhone changePhone);
	
	ChangePhone selectByPramaryKey(String approvalPersonId);
	
	List<ChangePhone> selectListByApprovalPersonId(String approvalPersonId,String companyId);
	
	int updateVerificationStatus(String id);
}
