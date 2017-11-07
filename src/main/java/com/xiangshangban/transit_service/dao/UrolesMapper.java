package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.Uroles;
@Mapper
public interface UrolesMapper {
    int deleteByPrimaryKey(String roleid);

    int insert(Uroles record);

    int insertSelective(Uroles record);

    Uroles selectByPrimaryKey(String roleid);

    int updateByPrimaryKeySelective(Uroles record);

    int updateByPrimaryKey(Uroles record);
}