package com.xiangshangban.register.common.dao;

import com.xiangshangban.register.common.bean.UsersRoles;

import java.util.List;
import java.util.Map;

public interface UsersRolesMapper {
    int insert(UsersRoles record);

    int insertSelective(UsersRoles record);

    int deleteByUserId(String id);

    int deleteRoleByUserIds(Map<String, Object> resultMap);
    // 根据用户roleId查询用户ID
    List<String> findUserIdByRoleId(String id);
}