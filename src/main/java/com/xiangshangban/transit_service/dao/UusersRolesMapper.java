package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.UusersRolesKey;
@Mapper
public interface UusersRolesMapper {
    int deleteByPrimaryKey(UusersRolesKey key);

    int insert(UusersRolesKey record);

    int insertSelective(UusersRolesKey record);
}