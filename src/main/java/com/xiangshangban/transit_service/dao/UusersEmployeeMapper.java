package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.UusersEmployeeKey;
@Mapper
public interface UusersEmployeeMapper {
    int deleteByPrimaryKey(UusersEmployeeKey key);

    int insert(UusersEmployeeKey record);

    int insertSelective(UusersEmployeeKey record);
}