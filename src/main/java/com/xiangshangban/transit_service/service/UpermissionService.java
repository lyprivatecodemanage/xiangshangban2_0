package com.xiangshangban.transit_service.service;

import java.util.List;

import com.xiangshangban.transit_service.bean.Upermission;

public interface UpermissionService {
	
	int deleteByPrimaryKey(String permissionid);

    int insert(Upermission record);

    int insertSelective(Upermission record);

    Upermission selectByPrimaryKey(String permissionid);

    int updateByPrimaryKeySelective(Upermission record);

    int updateByPrimaryKey(Upermission record);
    
    List<Upermission> selectByRoleId(String roleId);
}
