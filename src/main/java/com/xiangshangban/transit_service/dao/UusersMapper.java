package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.Uusers;
@Mapper
public interface UusersMapper {
    int deleteByPrimaryKey(String userid);

    int insert(Uusers record);

    int insertSelective(Uusers record);

    Uusers selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(Uusers record);

    int updateByPrimaryKey(Uusers record);
}