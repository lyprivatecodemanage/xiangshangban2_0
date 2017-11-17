package com.xiangshangban.transit_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.transit_service.bean.Upermission;
import com.xiangshangban.transit_service.dao.UpermissionMapper;

@Service("upermissionService")
public class UpermissionServiceImpl implements UpermissionService {

	@Autowired
	UpermissionMapper upermissionMapper;
	
	@Override
	public int deleteByPrimaryKey(String permissionid) {
		// TODO Auto-generated method stub
		return upermissionMapper.deleteByPrimaryKey(permissionid);
	}

	@Override
	public int insert(Upermission record) {
		// TODO Auto-generated method stub
		return upermissionMapper.insert(record);
	}

	@Override
	public int insertSelective(Upermission record) {
		// TODO Auto-generated method stub
		return upermissionMapper.insertSelective(record);
	}

	@Override
	public Upermission selectByPrimaryKey(String permissionid) {
		// TODO Auto-generated method stub
		return upermissionMapper.selectByPrimaryKey(permissionid);
	}

	@Override
	public int updateByPrimaryKeySelective(Upermission record) {
		// TODO Auto-generated method stub
		return upermissionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Upermission record) {
		// TODO Auto-generated method stub
		return upermissionMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Upermission> selectByRoleId(String roleId) {
		// TODO Auto-generated method stub
		return upermissionMapper.selectByRoleId(roleId);
	}

}
