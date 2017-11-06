package com.xiangshangban.transit_service.dao;

import com.xiangshangban.transit_service.bean.Client;

public interface ClientMapper {
    int insert(Client record);

    int insertSelective(Client record);
}