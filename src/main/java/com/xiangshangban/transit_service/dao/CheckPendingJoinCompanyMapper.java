package com.xiangshangban.transit_service.dao;

import com.xiangshangban.transit_service.bean.CheckPendingJoinCompany;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckPendingJoinCompanyMapper {
    int deleteByPrimaryKey(String userid);

    int insert(CheckPendingJoinCompany record);

    int insertSelective(CheckPendingJoinCompany record);

    CheckPendingJoinCompany selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(CheckPendingJoinCompany record);

    int updateByPrimaryKey(CheckPendingJoinCompany record);
    
    int deleteById(String userId);
}