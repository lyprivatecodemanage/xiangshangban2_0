package com.xiangshangban.register.common.dao;

import com.xiangshangban.register.common.bean.RolesPermission;

import java.util.List;
import java.util.Map;

public interface RolesPermissionMapper {
    int insert(RolesPermission record);

    int insertSelective(RolesPermission record);

    List<RolesPermission> findRolePermissionByPid(String id);
    // 根据角色ID查找
    List<RolesPermission> findRolePermissionByRid(String id);
    // 根据权限ID && 角色ID查找
    List<RolesPermission> find(RolesPermission entity);
    // 根据权限ID删除
    int deleteByPid(String id);
    // 根据角色ID删除
    int deleteByRid(String id);
    // 根据角色ID && 权限ID删除
    int delete(RolesPermission entity);
    // 根据角色IDs删除
    int deleteByRids(Map<String,Object> resultMap);
}