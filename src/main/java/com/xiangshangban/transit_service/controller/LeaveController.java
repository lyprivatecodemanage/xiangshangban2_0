package com.xiangshangban.transit_service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.util.HttpClientUtil;

@RestController
@RequestMapping("/submit/application")
public class LeaveController {

	/**
	 * 获取申请类型
	 * 
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/getType", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> getType(@RequestBody String jsonString) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			String url = "http://localhost:8092/activiti/approvalController/applicationForLeave";
			String str = HttpClientUtil.sendRequet(url, null);
			JSONObject jobj = JSON.parseObject(str);
			System.out.println(str);
			result.put("object", jobj.get("list"));
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
	 * 
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/applicationForLeave", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> applicationForLeave(@RequestBody String jsonString) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

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
	 * 加班申请
	 * 
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/overtimeApplication", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> overtimeApplication(@RequestBody String jsonString) {
		Map<String, Object> result = new HashMap<String, Object>();

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
	 * 出差申请
	 * 
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/travelApplications", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> travelApplications(@RequestBody String jsonString) {
		Map<String, Object> result = new HashMap<String, Object>();

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
	 * 外出申请
	 * 
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/goOutforApplication", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> goOutforApplication(@RequestBody String jsonString) {
		Map<String, Object> result = new HashMap<String, Object>();

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

}
