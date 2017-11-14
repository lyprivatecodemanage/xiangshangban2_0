package com.xiangshangban.transit_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.transit_service.bean.ChangePhone;
import com.xiangshangban.transit_service.dao.ChangePhoneMapper;
@Service("changePhoneService")
public class ChangePhoneServiceImpl implements ChangePhoneService {
	@Autowired
	private ChangePhoneMapper changePhoneMapper;
	
	
	@Override
	public int insertActiviti(ChangePhone changePhone) {
		
		return changePhoneMapper.insertActiviti(changePhone);
	}

	@Override
	public ChangePhone selectOneByApprovalPersonId(String approvalPersonId) {
		
		return changePhoneMapper.selectOneByApprovalPersonId(approvalPersonId);
	}

}
