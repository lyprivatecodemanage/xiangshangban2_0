package com.xiangshangban.transit_service.service;


import com.xiangshangban.transit_service.bean.Client;

/**
 * Created by mian on 2017/11/3.
 */
public interface ClientService {
    int insert(Client record);

    int insertSelective(Client record);

    Client SelectById(String clientId);
    
    //根据imei查询是否存在clientId  
    Client SelectByImei(String imei);
}
