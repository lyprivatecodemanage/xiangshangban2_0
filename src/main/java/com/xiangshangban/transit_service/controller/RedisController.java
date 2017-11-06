package com.xiangshangban.transit_service.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.transit_service.util.RedisUtil;

@RestController
@RequestMapping(value = "/redis/")
public class RedisController {
	/**
	 * 
	 * 设置
	 * @return
	 */
	@RequestMapping(value = "set",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	public String appUpload(@RequestParam(value="key")String key, @RequestParam(value="value")String value){ 
		RedisUtil redis = RedisUtil.getInstance();
		redis.new Hash().hset(key, "name", value);
		//redis.expireAt(key, TimeUtil.getLongAfterNow(1, Calendar.MINUTE)/1000);//设置超时时长1分钟
		redis.expire(key, 30);
        return "save success";  
    }
	/**
	 * 获取值
	 * @return
	 */
	@RequestMapping(value = "get.shtml",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public String appGetPath(@RequestParam(value="key")String key){
		RedisUtil redis = RedisUtil.getInstance();
		return redis.new Hash().hget(key, "name");
	}
} 

