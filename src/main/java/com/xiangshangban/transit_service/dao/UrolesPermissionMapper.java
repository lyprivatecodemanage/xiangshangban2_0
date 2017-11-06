package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.UrolesPermissionKey;
@Mapper
public interface UrolesPermissionMapper {
    int deleteByPrimaryKey(UrolesPermissionKey key);

    int insert(UrolesPermissionKey record);

    int insertSelective(UrolesPermissionKey record);
}