package com.xiangshangban.transit_service.controller;

import com.xiangshangban.transit_service.bean.Client;
import com.xiangshangban.transit_service.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mian on 2017/11/3.
 */
@RestController
@RequestMapping("/ClientIdController")
public class ClientController {

    @Autowired
    ClientService clientService;

    /***
     * 焦振/生成极光推送的clientId
     * @param type
     * @param imei
     * @param model
     * @return
     */
    @Transactional
    @RequestMapping(value = "/createClient")
    public Map<String,Object> createClient(String type,String imei,String model){

        Map<String,Object> map = new HashMap<String,Object>();

        try {
            String clientId = UUID.randomUUID().toString();
            Client client = new Client(clientId,type,imei,model);
            clientService.insertSelective(client);

            map.put("clientId",clientId);
            map.put("returnCode","3000");
            map.put("message","数据请求成功");
            return map;
        }catch(Exception e){
            e.printStackTrace();
            map.put("returnCode","3001");
            map.put("message","服务器错误");
            return map;
        }
    }


}
