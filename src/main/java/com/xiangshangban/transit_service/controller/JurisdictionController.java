package com.xiangshangban.transit_service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xiangshangban.transit_service.bean.Upermission;
import com.xiangshangban.transit_service.bean.Uroles;
import com.xiangshangban.transit_service.service.UusersRolesService;

@RestController
@RequestMapping("aurisdictionController")
public class JurisdictionController {
	private Logger logger = Logger.getLogger(AdministratorController.class);
	
	@Autowired
	UusersRolesService uusersRolesService;
	
	
	/***
	 * 焦振/根据用户编号查询出用户可访问的权限url地址
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "userIdByPermission", method = RequestMethod.POST)
	public Map<String,Object> userIdByPermission(String userId,HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		List<Upermission> list = new ArrayList<>();
		
		try {
			String companyId = request.getHeader("companyId");
			list = uusersRolesService.SelectUserIdByPermission(userId, companyId);
			map.put("permissionList",JSON.toJSON(list));
			map.put("returnCode","3000");
			map.put("message", "数据请求成功");
			return map;
		}catch(NullPointerException e){
			e.printStackTrace();
			logger.info(e);
			map.put("returnCode","4007");
			map.put("message", "结果为null");
			return map;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info(e);
			map.put("returnCode","3001");
			map.put("message", "服务器错误");
			return map;
		}
	}

	/***
	 * 焦振/根据用户ID和请求头中的公司ID 来获取角色信息
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "LoginRole", method = RequestMethod.POST)
	public Map<String, Object> LoginRole(String userId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String companyId = request.getHeader("companyId");
		try {

			Uroles role = uusersRolesService.SelectRoleByUserId(userId, companyId);

			map.put("role", role.getRolename());
			map.put("returnCode", "3000");
			map.put("message", "数据请求成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
			map.put("returnCode", "3001");
			map.put("message", "服务器错误");
			return map;
		}
	}

}
