package com.xiangshangban.transit_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.transit_service.bean.Upermission;
import com.xiangshangban.transit_service.bean.UusersRolesKey;
import com.xiangshangban.transit_service.dao.UusersRolesMapper;

@Service("uusersRolesService")
public class UusersRolesServiceImpl implements UusersRolesService {

	@Autowired
	UusersRolesMapper uusersRolesMapper;
	
	@Override
	public int deleteByPrimaryKey(UusersRolesKey key) {
		// TODO Auto-generated method stub
		return uusersRolesMapper.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(UusersRolesKey record) {
		// TODO Auto-generated method stub
		return uusersRolesMapper.insert(record);
	}

	@Override
	public int insertSelective(UusersRolesKey record) {
		// TODO Auto-generated method stub
		return uusersRolesMapper.insertSelective(record);
	}

	@Override
	public UusersRolesKey SelectAdministrator(String companyId) {
		// TODO Auto-generated method stub
		return uusersRolesMapper.SelectAdministrator( companyId);
	}

	@Override
	public int updateAdministrator(String userId, String newUserId, String companyId,String historyUserIds) {
		// TODO Auto-generated method stub
		return uusersRolesMapper.updateAdministrator(userId, newUserId, companyId,historyUserIds);
	}

	@Override
	public List<Upermission> SelectUserIdByPermission(String userId,String companyId) {
		// TODO Auto-generated method stub
		return uusersRolesMapper.SelectUserIdByPermission(userId,companyId);
	}

}
