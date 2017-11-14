package com.xiangshangban.transit_service.service;

import com.xiangshangban.transit_service.bean.ChangePhone;

public interface ChangePhoneService {

	int insertActiviti(ChangePhone changePhone);
	
	ChangePhone selectOneByApprovalPersonId(String approvalPersonId);
}
