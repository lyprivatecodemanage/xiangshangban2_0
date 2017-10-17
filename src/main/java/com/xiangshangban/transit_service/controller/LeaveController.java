package com.xiangshangban.transit_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.transit_service.util.HttpClientUtil;

@RestController
@RequestMapping("/submit/application")
public class LeaveController {
	
	
	
	/**
	 * 获取请假类型
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value="/type",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> getType(@RequestBody String jsonString){
		Map<String,Object> result = new HashMap<String,Object>();
		
		
		try {
			String url = "http://192.168.0.242:8097/activiti/application/overwork/startFlow";
			HttpClientUtil.sendRequet(url, null);
			result.put("returnCode", "3000");
			result.put("message", "成功");
			return result;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.put("returnCode", "3007");
			result.put("message", "参数格式不正确");
			return result;
		} catch (NullPointerException e) {
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "参数为null");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "3006");
			result.put("message", "失败");
			return result;
		}
	}
	
	/**
	 * 请假申请
	 * @param jsonString
	 * @return
	 *//*
	@RequestMapping(value="/type",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> applicationForLeave(@RequestBody String jsonString){
		
	}
	
	*//**
	 * 加班申请
	 * @param jsonString
	 * @return
	 *//*
	@RequestMapping(value="/type",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public Map<String,Object> overtimeApplication(@RequestBody String jsonString){
		
	}*/
	
	
}
