package com.xiangshangban.transit_service.dao;

import com.xiangshangban.transit_service.bean.ClientDetail;

public interface ClientDetailMapper {
    int insert(ClientDetail record);

    int insertSelective(ClientDetail record);
}