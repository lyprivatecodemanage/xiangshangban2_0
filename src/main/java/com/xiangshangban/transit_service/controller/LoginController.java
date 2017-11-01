package com.xiangshangban.transit_service.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.exceptions.ClientException;
import com.xiangshangban.transit_service.bean.Login;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.service.LoginService;
import com.xiangshangban.transit_service.service.UusersService;
import com.xiangshangban.transit_service.util.FileMD5Util;
import com.xiangshangban.transit_service.util.FormatUtil;
import com.xiangshangban.transit_service.util.YtxSmsUtil;

@RestController
@RequestMapping("/loginController")
public class LoginController {

	private String smsCode;

	@Autowired
	private LoginService loginService;

	@Autowired
	private UusersService uusersService;

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}
	/**
	 * 登录
	 * @param phone
	 * @param smsCode
	 * @param type
	 * @param token
	 * @param session
	 * @param request
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/loginUser")
	public Map<String, Object> loginUser(String phone, String smsCode, String type, HttpSession session,HttpServletRequest request) {
		// Principal principal = UserUtils.getPrincipal();
		Map<String, Object> result = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		String token = request.getHeader("token");
		try {
		
				String salt = FileMD5Util.GetSalt();
				String sessionId= session.getId();
				String effectiveTime = "1";
				Date date = new Date();
				String now = sdf.format(date);
				Login newLogin = new Login();
				//判断app请求和web请求
				//app
				if(Integer.valueOf(type)==1) {
					// 判断token是否为null,也就是判断app是否是已登录
					if (token != null) {
						// 已登录则根据token查用户的信息
						Login login = loginService.selectByToken(token);
						// 判断token对应的用户信息是否存在,以及token是否过期
						if (login != null) {
							Date createTime = sdf.parse(login.getCreateTime());
							calendar.setTime(date);
							calendar.add(calendar.DATE, Integer.valueOf(login.getEffectiveTime()));
							String TabPhone = login.getPhone();
							Uusers user = uusersService.selectByPhone(TabPhone);
							if (user != null) {
								phone = user.getPhone();
								smsCode = user.getTemporarypwd();
							}
							// token过期
							if (calendar.getTime().getTime() < createTime.getTime()) {
								//产生新的token
								token = FileMD5Util.getMD5String(phone + now + salt);
							}
							login.setSalt(salt);
							login.setToken(token);
							login.setCreateTime(now);
							login.setPhone(phone);
							login.setEffectiveTime(effectiveTime);
							login.setSessionId(sessionId);
							loginService.updateByPrimaryKeySelective(login);
						}
					}else{
						//首次登录,或退出账号时
						token = FileMD5Util.getMD5String(phone + now + salt);
						newLogin.setCreateTime(now);
						newLogin.setSalt(salt);
						newLogin.setPhone(phone);
						newLogin.setId(FormatUtil.createUuid());
						newLogin.setEffectiveTime(effectiveTime);
						newLogin.setSessionId(sessionId);
						newLogin.setToken(token);
						loginService.insertSelective(newLogin);
					}
				}
				//web
				if(Integer.valueOf(type)==0) {
					sessionId = session.getId();
					Login login = loginService.selectBySessionId(sessionId);
					//判断连接是否存在
					//存在
					if(login!=null) {
						String TabPhone = login.getPhone();
						Uusers user = uusersService.selectByPhone(TabPhone);
						//获取用户手机号和smsCode
						if(user!=null) {
							phone = user.getPhone();
							smsCode = user.getTemporarypwd();
						}
						//记录登录信息
						token = FileMD5Util.getMD5String(phone + now + salt);
						login.setSalt(salt);
						login.setToken(token);
						login.setCreateTime(now);
						login.setPhone(phone);
						login.setEffectiveTime(effectiveTime);
						login.setSessionId(sessionId);
						loginService.updateByPrimaryKeySelective(login);
					}else{
						loginService.selectByPhone(phone);
						//首次登录,或退出账号时
						token = FileMD5Util.getMD5String(phone + now + salt);
						newLogin.setCreateTime(now);
						newLogin.setSalt(salt);
						newLogin.setPhone(phone);
						newLogin.setId(FormatUtil.createUuid());
						newLogin.setEffectiveTime(effectiveTime);
						newLogin.setSessionId(sessionId);
						newLogin.setToken(token);
						loginService.insertSelective(newLogin);
					}
				}
				
				System.out.println(session.getId());
				UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(phone, smsCode);
				System.out.println(usernamePasswordToken.toString());
				Subject subject = SecurityUtils.getSubject();
				usernamePasswordToken.setRememberMe(true);
				Serializable sessionId1 = subject.getSession().getId();
				subject.login(usernamePasswordToken); // 完成登录
				// Uusers user=(Uusers) subject.getPrincipal();
				String str = subject.getPrincipal().toString();
				System.out.println(str);
				// session.setAttribute("user", user);
				if(Integer.valueOf(type)==1){
				result.put("token", token);
				}
				result.put("message", "登录成功!");
				result.put("returnCode", "3000");
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
	 * shiro退出
	 * @param session
	 * @return
	 */
	@RequiresRoles(value={"管理员","超级管理员"},logical=Logical.OR)
	@RequestMapping(value = "/logOut")
	// @RequestMapping(value="/logOut",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String logOut(HttpSession session) {
		Subject subject = SecurityUtils.getSubject();

		subject.logout();
		// session.removeAttribute("user");
		return "login";
	}
	/**
	 * 获取短信验证码
	 * @param phone
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sendSms")
	public String sendSms(String phone,HttpSession session) {
		YtxSmsUtil sms = new YtxSmsUtil("LTAIcRopzlp5cbUd", "VnLMEEXQRukZQSP6bXM6hcNWPlphiP");
		try {
			Uusers user = uusersService.selectByPhone(phone);
			smsCode = sms.sendIdSms(phone);
			if(user!=null){
				uusersService.updateSmsCode(phone, smsCode);
			}
			System.out.println(session.getId());
			return smsCode;
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}
	@RequestMapping(value = "/unAuthorizedUrl")
	public Map<String, Object> unAuthorizedUrl() {
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("message", "没有权限");
		result.put("returnCode", "4000");
		return result;
	}
}
