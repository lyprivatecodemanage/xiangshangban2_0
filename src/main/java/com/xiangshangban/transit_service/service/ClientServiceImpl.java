package com.xiangshangban.transit_service.service;

import com.xiangshangban.transit_service.bean.Client;
import com.xiangshangban.transit_service.dao.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mian on 2017/11/3.
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientMapper clientMapper;

    @Override
    public int insert(Client record) {
        return clientMapper.insert(record);
    }

    @Override
    public int insertSelective(Client record) {
        return clientMapper.insertSelective(record);
    }

    @Override
    public Client SelectById(String clientId) {
        return clientMapper.SelectById(clientId);
    }
}
