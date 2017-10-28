package com.xiangshangban.register.common.dao;

import com.xiangshangban.register.common.bean.Roles;

import java.util.List;
import java.util.Map;

public interface RolesMapper {

    int deleteByPrimaryKey(String roleid);

    int insert(Roles record);

    int insertSelective(Roles record);

    Roles selectByPrimaryKey(String roleid);

    int updateByPrimaryKeySelective(Roles record);

    int updateByPrimaryKey(Roles record);
    //查询用户全部的role & permission
    List<Roles> findNowAllPermission(Map<String, Object> map);
}