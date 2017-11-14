package com.xiangshangban.transit_service.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.util.HttpClientUtil;
import com.xiangshangban.transit_service.util.PropertiesUtils;

@RestController
@RequestMapping("/webApproval")
public class WebApprovalManagementController {
	Logger logger = Logger.getLogger(WebApprovalManagementController.class);
	
	/**
	 * @author 李业(已完成)
	 * 审批模块--流程设计:获取各类型的审批模板类型(如:加班,请假,出差......)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/processDesign", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	public Map<String, Object> processDesign() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String url = PropertiesUtils.pathUrl("processDesignUrl");
			String str = HttpClientUtil.sendRequet(url, "{}");
			JSONObject jobj = JSON.parseObject(str);
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
	 * @author 李业(已完成)
	 * 自定义模板:通过前端传递的模板id来确定是自定义哪一种模板,并返回前端模板信息(如:加班,请假......)
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/customTemplate", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> customTemplate(@RequestBody String jsonString) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String sendurl =PropertiesUtils.pathUrl("customTemplateUrl");
			String returnJsonString = HttpClientUtil.sendRequet(sendurl, jsonString);
			result.put("approvalTemplate", JSON.parseObject(returnJsonString).get("approvalTemplate"));
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
	 * @author 李业(已完成)
	 * 自定义模板提交:提交模板信息(如:加班,请假......)
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/commitCustomTemplate", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> commitCustomTemplate(@RequestBody String jsonString) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String sendurl =PropertiesUtils.pathUrl("commitCustomTemplateUrl");
			String returnJsonString = HttpClientUtil.sendRequet(sendurl,jsonString);
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
	 * @author 李业(未完成)
	 * 审批流程中添加审批人按钮接口
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/assigneeType", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> assigneeType(@RequestBody String jsonString) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			JSONObject jobj = JSON.parseObject(jsonString);
			String approvalRelation = jobj.getString("approvalRelation");
			String companyId = jobj.getString("companyId");
			//按汇报关系
			if(Integer.valueOf(approvalRelation)==0){
				String assigneeType = "0";
				result.put("assigneeType", assigneeType);
				result.put("returnCode", "3000");
				result.put("message", "成功");
				return result;
			}
			//不按汇报关系
			if(Integer.valueOf(approvalRelation)==1){
				//选择审批人(0:按岗位;1:按人员)
				String approvalPersonType = jobj.getString("approvalPersonType");
				//审批链类型(0:串签;1:或签;)
				String approvalChainType = jobj.getString("approvalChainType");
				
				
				
			}
			
			
			
			/*String sendurl ="http://localhost:8092/activiti/webApproval/approvalProcess";
			String returnJsonString = HttpClientUtil.sendRequet(sendurl, jsonString);*/
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
	 * @author 李业(未完成)
	 * 审批流程设置审批链,包括默认设置和分条件设置
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/approvalProcess", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> approvalProcess(@RequestBody String jsonString) {
		Map<String, Object> result = new HashMap<String, Object>();
	
		try {
			String sendurl =PropertiesUtils.pathUrl("approvalProcessUrl");
			String returnJsonString = HttpClientUtil.sendRequet(sendurl, jsonString);
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
	 * @author 李业(已完成)
	 * 审批模块--流程管理(web)
	 * @param loginUserId
	 * @param applicationType
	 * @param applicationStatus
	 * @param createTime
	 * @return
	 */
	@RequestMapping(value = "/processManagement", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
	public Map<String, Object> processManagement(String companyId,String loginUserId, String applicationType, String applicationStatus,
			String createTime) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginUserId", loginUserId);//申请人id
		params.put("applicationType", applicationType);//申请类型
		params.put("applicationStatus", applicationStatus);//审批状态
		params.put("createTime", createTime);//发起时间
		params.put("companyId", companyId);//公司id
		boolean applicationTypeFlag = Pattern.matches("[0-4]?", applicationType);
		boolean applicationStatusFlag = Pattern.matches("[0-4]?", applicationStatus);
		//boolean createTimeFlag = Pattern.matches("", createTime);
		if(!applicationTypeFlag || !applicationStatusFlag){
			result.put("message", "参数格式不正确");
			result.put("returnCode", "3007");
			return result;
		}
		try {
			if(createTime!=null && !"".equals(createTime)){
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTime);
			}
			//判断输入的发起人是否存在
			
			String sendurl =PropertiesUtils.pathUrl("processManagementUrl");
			String returnJsonString = HttpClientUtil.sendRequet(sendurl, JSON.toJSONString(params));
			//jsonstring转map
			result = JSON.parseObject(returnJsonString);
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
			logger.info(e);
			result.put("returnCode", "3006");
			result.put("message", "失败");
			return result;
		}
	}
}
