package com.xiangshangban.register.common.dao;

import com.xiangshangban.register.common.bean.Permission;

import java.util.Set;

public interface PermissionMapper {
    //按ID删除权限
    int deleteByPrimaryKey(String permissionid);
    //新增权限
    int insert(Permission record);
    //新增权限
    int insertSelective(Permission record);
    //按ID查询权限
    Permission selectByPrimaryKey(String permissionid);
    //根据权限编号修改权限名称
    int updateByPrimaryKeySelective(Permission record);
    //根据权限编号修改权限名称
    int updateByPrimaryKey(Permission record);
    //根据用户ID获取权限路径的Set集合
    Set<String> findPermissionByUserId(String id);
}