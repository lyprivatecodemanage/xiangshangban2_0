package com.xiangshangban.transit_service.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.util.HttpClientUtil;

@RestController
@RequestMapping("/appApprovalManagementController")
public class AppApprovalManagementController {
	private Logger logger = Logger.getLogger(AppApprovalManagementController.class);

	/**
	 * @author 李业 获取申请类型(已完成)
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/getType", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> getType() {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			String url = "http://localhost:8092/activiti/appApprovalController/applicationForLeave";
			String str = HttpClientUtil.sendRequet(url, "{}");
			JSONObject jobj = JSON.parseObject(str);
			result.put("applicationTypeList", jobj.get("list"));
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
	 * @author 李业 app页面请假申请页面申请项目下的对应假期接口(已完成)
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/getVacationModel", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> getVacationModel(@RequestBody String jsonString) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 转发请假申请的请求
			String url1 = "http://localhost:8091/attendance/vacationModel/select";
			String resultJsonString = HttpClientUtil.sendRequet(url1, jsonString);
			// 获得考勤模块假期模板对应假期的详细信息
			JSONObject resultJobj = JSON.parseObject(resultJsonString);
			result.put("vacationModel", resultJobj.get("vacationModel"));
			result.put("returnCode", "3000");
			result.put("message", "成功");
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e);
			result.put("returnCode", "3006");
			result.put("message", "失败");
			return result;
		}
	}

	/**
	 * 
	 * @author 李业 请假申请
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/applicationForLeave", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> applicationForLeave(String applicationType, String leaveType, String loginUserId,
			String loginUserName, String startTime, String endTime, String effectiveTimeLength, String reason,
			String unit, String companyId, MultipartFile oldfile) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			// 设置工作流接口所需的参数
			Map<String, String> params = new HashMap<String, String>();
			params.put("proposerId", loginUserId);// 申请人id
			params.put("proposerName", loginUserName);// 申请人name
			params.put("startTime", startTime);// 申请起始时间
			params.put("endTime", endTime);// 申请结束时间
			params.put("effectiveTimeLength", effectiveTimeLength);// 申请有效时长
			params.put("reason", reason);// 申请理由
			params.put("unit", unit);// 申请计算单位
			params.put("applicationType", applicationType);// 申请类型
			params.put("leaveType", leaveType);// 请假类型
			params.put("companyId", companyId);// 公司id

			// 判断是否有附件
			/*
			 * if (!oldfile.isEmpty()) { InputStream in =
			 * oldfile.getInputStream(); String name =
			 * oldfile.getOriginalFilename(); System.out.println(name + "\t" +
			 * oldfile.getSize() + "\t" + in.available()); byte[] b = new
			 * byte[in.available()]; in.read(b); File targetFile = new
			 * File("C:/Users/cachee/Desktop/aaa/" + name); OutputStream out =
			 * new FileOutputStream(targetFile); out.write(b); out.flush();
			 * out.close(); in.close(); }
			 */

			// 缺少申请时间段是否有其他申请的判断
			// ⑴先判断是什么类型的申请
			// 0：加班，1：请假，2：出差，3：外出，4：补勤
			if (Integer.valueOf(applicationType) == 0) {

			}
			if (Integer.valueOf(applicationType) == 1) {

			}
			if (Integer.valueOf(applicationType) == 2) {

			}
			if (Integer.valueOf(applicationType) == 3) {

			}
			if (Integer.valueOf(applicationType) == 4) {

			}

			// 审批链是否完整的判断,并获取审批级数,设置审批人
			String urlApprovalChain = "http://localhost:8092/activiti/appApprovalController/getJudge";
			String jsonStr1 = HttpClientUtil.sendRequet(urlApprovalChain, JSON.toJSON(params));
			if (Integer.valueOf(JSON.parseObject(jsonStr1).getString("returnCode")) == 3006) {
				result.put("returnCode", "3006");
				result.put("message", "审批链不存在");
				return result;
			}
			Map<String, Object> newParams = JSON.parseObject(JSON.parseObject(jsonStr1).get("params").toString());

			// 调用工作流接口
			String url2 = "http://localhost:8092/activiti/activitiApplicatonController/startLeaveFlow";
			newParams.put("files", null);
			String jsonStr2 = HttpClientUtil.sendRequet(url2, newParams);

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
	public Map<String, Object> overtimeApplication(String applicationType, String calculateType, String loginUserId,
			String loginUserName, String startTime, String endTime, String effectiveTimeLength, String reason,
			String unit, String companyId, MultipartFile oldfile) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("proposerId", loginUserId);
		params.put("proposerName", loginUserName);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("effectiveTimeLength", effectiveTimeLength);
		params.put("calculateType", calculateType);
		params.put("reason", reason);
		params.put("unit", unit);
		params.put("companyId", companyId);
		params.put("applicationType", applicationType);

		try {
			
			// 审批链是否完整的判断,并获取审批级数,设置审批人
						String urlApprovalChain = "http://localhost:8092/activiti/appApprovalController/getJudge";
						String jsonStr1 = HttpClientUtil.sendRequet(urlApprovalChain, JSON.toJSON(params));
						if (Integer.valueOf(JSON.parseObject(jsonStr1).getString("returnCode")) == 3006) {
							result.put("returnCode", "3006");
							result.put("message", "审批链不存在");
							return result;
						}
						Map<String, Object> newParams = JSON.parseObject(JSON.parseObject(jsonStr1).get("params").toString());

			String url = "http://localhost:8092/activiti/activitiApplicatonController/startOverWorkFlow";
			// 返回的json字符串,解析之后返给前端
			String str = HttpClientUtil.sendRequet(url, newParams);
			System.out.println(str);

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
	public Map<String, Object> travelApplications(String applicationType, String calculateType, String loginUserId,
			String loginUserName, String startTime, String endTime, String effectiveTimeLength, String reason,
			String unit, String companyId, MultipartFile oldfile) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			String url = "http://192.168.0.242:8097/activiti/activitiApplicatonController";
			// 返回的json字符串,解析之后返给前端
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

	/**
	 * 我的发起
	 * 
	 * @param loginUserId
	 * @param applicationStatus
	 * @param applicationType
	 * @param matchTime
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/myLaunchProcess", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Map<String, Object> myLaunchProcess(@RequestBody String jsonString) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			String sendurl = "http://localhost:8092/activiti/activitiHistoryController/myLaunchProcess";
			String stringResult = HttpClientUtil.sendRequet(sendurl, jsonString);
			result = (Map<String, Object>) JSON.parseObject(stringResult);
			// 此处要数据处理
			return result;
		} catch (Exception e) {
			result.put("message", "错误");
			return result;
		}
	}

	/**
	 * 我的审批
	 * 
	 * @param loginUserId
	 * @param applicationStatus
	 * @param applicationType
	 * @param matchTime
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/myApprovalProcess", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Map<String, Object> myApprovalProcess(@RequestBody String jsonString) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String sendurl = "http://localhost:8092/activiti/activitiHistoryController/myApprovalProcess";
			String stringResult = HttpClientUtil.sendRequet(sendurl, jsonString);
			result = (Map<String, Object>) JSON.parseObject(stringResult);
			// 此处要数据处理
			return result;
		} catch (Exception e) {
			result.put("message", "错误");
			return result;
		}
	}

	/**
	 * 审批(通过,驳回)
	 * 
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/signTaskAndApproval", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Map<String, Object> signTaskAndApproval(@RequestBody String jsonString) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			String url = "http://localhost:8092/activiti/activitiTaskController/signTaskAndApproval";
			String resultStr = HttpClientUtil.sendRequet(url, jsonString);
			result = JSON.parseObject(resultStr);
			return result;
		} catch (Exception e) {
			result.put("message", "错误");
			return result;
		}
	}

}
