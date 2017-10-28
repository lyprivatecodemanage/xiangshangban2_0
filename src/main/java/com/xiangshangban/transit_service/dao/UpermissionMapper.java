package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.Upermission;
@Mapper
public interface UpermissionMapper {
    int deleteByPrimaryKey(String permissionid);

    int insert(Upermission record);

    int insertSelective(Upermission record);

    Upermission selectByPrimaryKey(String permissionid);

    int updateByPrimaryKeySelective(Upermission record);

    int updateByPrimaryKey(Upermission record);
}