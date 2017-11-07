package com.xiangshangban.transit_service.dao;

import com.xiangshangban.transit_service.bean.ClientDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClientDetailMapper {
    int insert(ClientDetail record);

    int insertSelective(ClientDetail record);
}