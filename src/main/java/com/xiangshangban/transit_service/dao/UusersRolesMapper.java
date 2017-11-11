package com.xiangshangban.transit_service.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.transit_service.bean.UusersRolesKey;
@Mapper
public interface UusersRolesMapper {
    int deleteByPrimaryKey(UusersRolesKey key);

    int insert(UusersRolesKey record);

    int insertSelective(UusersRolesKey record);
    
    //查看当前管理员及历史管理员
    UusersRolesKey SelectAdministrator(@Param("companyId")String companyId);
}