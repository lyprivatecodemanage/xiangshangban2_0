package com.xiangshangban.transit_service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.bean.UusersRolesKey;
import com.xiangshangban.transit_service.service.UusersRolesService;
import com.xiangshangban.transit_service.service.UusersService;
import com.xiangshangban.transit_service.util.HttpClientUtil;

@RestController
@RequestMapping("/administratorController")
public class AdministratorController {
	private Logger logger = Logger.getLogger(AdministratorController.class);

	@Autowired
	UusersRolesService uusersRolesService;
	
	@Autowired
	UusersService uusersService;
	
	/***
	 * 焦振/系统设置 --> 更改管理员界面初始化显示 
	 * 当前管理员和历史管理员(姓名、登录名、头像)
	 * @param request
	 * @return
	 */
	@RequestMapping("administratorInit")
	public Map<String,Object> administratorInit(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		List<Uusers> list = new ArrayList<>();
		try{
			//拿到请求头中的companyId查询公司管理员
			String companyId = request.getHeader("companyId");
			UusersRolesKey uusersRolesKey = uusersRolesService.SelectAdministrator(companyId);
			//结果为空时代表该公司暂未有管理员
			if(null == uusersRolesKey){
				map.put("returnCode","3000");
				map.put("message","数据请求成功");
				return map;
			}
			//查看历史管理员数据
			if(!StringUtils.isNotEmpty(uusersRolesKey.gethistoryUserIds())){
				String [] userids = uusersRolesKey.gethistoryUserIds().split(",");
				
				for (int i = 0; i < userids.length; i++) {
					list.add(uusersService.selectById(userids[userids.length-i-1]));
				}
				//历史管理员信息
				map.put("data",JSON.toJSON(list));
			}
			//查询当前管理员信息
			Uusers uusers = uusersService.selectById(uusersRolesKey.getUserId());
			
			map.put("admin",JSON.toJSON(uusers));
			map.put("returnCode","3000");
			map.put("message","数据请求成功");
			return map;
		}catch(NullPointerException e){
			e.printStackTrace();
			logger.info(e);
			map.put("returnCode", "4007");
			map.put("message","结果为null");
			return map;
		}
		catch(Exception e){
			e.printStackTrace();
			logger.info(e);
			map.put("returnCode", "3001");
			map.put("message","服务器错误");
			return map;
		}
	}
	
	
	/***
	 *焦振/查看公司所有部门
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value="/selectDepartment")
	public Map<String,Object> selectDepartment(HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String companyId = request.getHeader("companyId");
			
			//设置头部信息公司编号
			Map<String,String> map = new HashMap<>();
			map.put("companyId",companyId);
			//访问路径
			String url = "http://192.168.0.242:8093/organization/DepartmentController/findDepartmentTree";
			String str = HttpClientUtil.sendRequet(url,"{}",ContentType.APPLICATION_JSON, map);
			JSONObject jobj = JSON.parseObject(str);
			
			if("3000".equals(jobj.get("returnCode"))){
				result.put("companyName",jobj.get("companyName"));
				result.put("departmentDate", jobj.get("data"));
				result.put("returnCode", "3000");
				result.put("message", "成功");
				return result;	
			}else{
				result.put("returnCode", "3001");
				result.put("message", "失败");
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e);
			result.put("returnCode", "3001");
			result.put("message", "失败");
			return result;
		}
	} 
	
	/***
	 * 焦振/根据人员姓名     所属部门     主岗位
	 * 模糊分页查询
	 * @param employeeName
	 * @param departmentName
	 * @param postName
	 * @param pageNum
	 * @param pageRecordNum
	 * @param request
	 * @return
	 */
	@RequestMapping(value="administratorEmployee",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
	public Map<String,Object> administratorEmployee(String employeeName,String departmentName,String postName,String pageNum,String pageRecordNum,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		try{
			String companyId = request.getHeader("companyId");
			
			//设置头部信息公司编号
			Map<String,String> map = new HashMap<>();
			map.put("companyId",companyId);
			
			//设置post参数
			Map<String,String> parameter = new HashMap<>();
			parameter.put("employeeName", employeeName);
			parameter.put("departmentName", departmentName);
			parameter.put("postName", postName);
			parameter.put("pageNum", pageNum);
			parameter.put("pageRecordNum", pageRecordNum);
			
			//访问路径
			StringBuffer url = new StringBuffer("http://192.168.0.242:8093/organization/EmployeeController/findBydynamicempadmin");
			String str = HttpClientUtil.sendRequet(url.toString(),parameter,ContentType.APPLICATION_JSON, map);
			JSONObject jobj = JSON.parseObject(str);
			if("3000".equals(jobj.get("returnCode"))){
				result.put("employee", jobj.get("data"));
				result.put("returnCode", "3000");
				result.put("message", "数据请求成功");
				return result;	
			}else{
				result.put("returnCode", "3001");
				result.put("message", "服务器错误");
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e);
			result.put("returnCode", "3001");
			result.put("message", "服务器错误");
			return result;
		}
	}

	/***
	 * 焦振/更换管理员
	 * @param newUserId
	 * @param request
	 * @return
	 */
	@RequestMapping("updateadministrator")
	public Map<String,Object> updateadministrator(String newUserId,HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		String historyUserIds = "";
		try {
			//获取原来管理员数据的信息
			String companyId = request.getHeader("companyId");
			UusersRolesKey uusersRolesKey = uusersRolesService.SelectAdministrator(companyId);
			
			//将原来的管理员添加为历史管理员
			String huids = uusersRolesKey.gethistoryUserIds();
			if(uusersRolesKey.gethistoryUserIds().split(",").length>2){
				historyUserIds = huids.substring(huids.indexOf(",")+1)+","+uusersRolesKey.getUserId();
			}
			//更换新管理员
			uusersRolesService.updateAdministrator(uusersRolesKey.getUserId(),newUserId, companyId,historyUserIds);
			
			map.put("returnCode", "3000");
			map.put("message", "数据请求成功");
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info(e);
			map.put("returnCode", "3001");
			map.put("message", "服务器错误");
			return map;
		}
	}
	
}
