package com.xiangshangban.transit_service.dao;

import com.xiangshangban.transit_service.bean.Client;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClientMapper {
    int insert(Client record);

    int insertSelective(Client record);

    Client SelectById(String clientId);
}