package com.xiangshangban.transit_service.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.transit_service.bean.Login;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.service.LoginService;
import com.xiangshangban.transit_service.service.UusersService;

@RestController
@RequestMapping("/ChangePhoneNumController")
public class ChangePhoneNumController {
	Logger logger = Logger.getLogger(ChangePhoneNumController.class);
	@Autowired
	private UusersService uusersService;
	@Autowired
	private LoginService loginService;

	/**
	 * 更改手机号中判断新手机号是否注册以及是否活跃
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping("/changePhoneTask")
	public Map<String, Object> changePhoneTask(String phone) {
		Map<String, Object> result = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Uusers user = uusersService.selectByPhone(phone);
			// 系统中是否有新手机号
			// 否
			if (user == null) {
				result.put("message", "未注册");
				result.put("returnCode", "4004");
				return result;
			} else {
				// 查看是否活跃
				Login login = loginService.selectOneByPhone(phone);
				Date lastLoginTime = sdf.parse(login.getCreateTime());
				Date nowTime = new Date();
				// 此处需要一个是否活跃的标准
				// if(){
				// 活跃
				// result.put("message","当前手机处于活跃状态,不可被重新注册");
				// result.put("returnCode","4010");
				// return result;
				// }
				// else{
				// 不活跃
				// result.put("message","当前手机处于不活跃状态,请输入验证码继续...");
				// result.put("returnCode","4011"); return result;
				// }
			}
			return result;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.info(e);
			result.put("returnCode", "3007");
			result.put("message", "参数格式不正确");
			return result;
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.info(e);
			result.put("returnCode", "3006");
			result.put("message", "参数为null");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
			result.put("returnCode", "3001");
			result.put("message", "失败");
			return result;
		}
	}

	/**
	 * 未完成
	 * @param phone
	 * @param userName
	 * @param companyName
	 * @return
	 */
	public Map<String, Object> isProtect(String phone,String userName,String companyName) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			
			
			return result;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.info(e);
			result.put("returnCode", "3007");
			result.put("message", "参数格式不正确");
			return result;
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.info(e);
			result.put("returnCode", "3006");
			result.put("message", "参数为null");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
			result.put("returnCode", "3001");
			result.put("message", "失败");
			return result;
		}
	}
}
