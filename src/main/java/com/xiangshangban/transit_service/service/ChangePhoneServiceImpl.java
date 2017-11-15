package com.xiangshangban.transit_service.service;

import java.util.List;

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
	public ChangePhone selectByPramaryKey(String approvalPersonId) {
		
		return changePhoneMapper.selectByPramaryKey(approvalPersonId);
	}

	@Override
	public List<ChangePhone> selectListByApprovalPersonId(String approvalPersonId, String companyId) {
		// TODO Auto-generated method stub
		return changePhoneMapper.selectListByApprovalPersonId(approvalPersonId, companyId);
	}

	@Override
	public int updateVerificationStatus(String id) {
		// TODO Auto-generated method stub
		return changePhoneMapper.updateVerificationStatus(id);
	}

}
