package com.xiangshangban.transit_service.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.transit_service.bean.Login;
import com.xiangshangban.transit_service.bean.UniqueLogin;
import com.xiangshangban.transit_service.bean.Uroles;
import com.xiangshangban.transit_service.bean.UserCompanyDefault;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.service.CompanyService;
import com.xiangshangban.transit_service.service.LoginService;
import com.xiangshangban.transit_service.service.UniqueLoginService;
import com.xiangshangban.transit_service.service.UserCompanyService;
import com.xiangshangban.transit_service.service.UusersRolesService;
import com.xiangshangban.transit_service.service.UusersService;
import com.xiangshangban.transit_service.util.FileMD5Util;
import com.xiangshangban.transit_service.util.FormatUtil;
import com.xiangshangban.transit_service.util.RedisUtil;
import com.xiangshangban.transit_service.util.RedisUtil.Hash;

@RestController
@RequestMapping("/loginUserController")
public class LoginUserController {
	private static final Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private LoginService loginService;
	@Autowired
	private UusersService uusersService;
	@Autowired
	CompanyService companyService;
	@Autowired
	private UniqueLoginService uniqueLoginService;
	@Autowired
	private UusersRolesService uusersRolesService;
	@Autowired
	private UserCompanyService userCompanyService;
	/**
	 * @author 李业/短信验证码登录
	 * @param phone
	 * @param smsCode
	 * @param type
	 * @param token
	 * @param session
	 * @param request
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	public Map<String, Object> loginUser(String phone, String smsCode, HttpSession session,
			HttpServletRequest request) {
		System.out.println("logingUser:\t"+session.getId());
		Map<String, Object> result = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		// 获取请求参数
		String type = request.getHeader("type");
		String token = request.getHeader("token");
		String clientId = request.getHeader("clientId");
		String id = "";
		if (phone != null && !"".equals(phone)) {
			// 判断手机号是否注册
			Uusers user = uusersService.selectByPhone(phone);
			Login loginRecord = null;
			if("0".equals(type)){
				loginRecord = loginService.selectOneByPhoneFromWeb(phone);
			}
			if("1".equals(type)){
				loginRecord = loginService.selectOneByPhoneFromApp(phone);
			}
			if (user == null) {
				result.put("message", "手机号不存在,请注册");
				result.put("returnCode", "4004");
				return result;
			}
			int isActive = uusersService.isActive(phone);
			if(isActive==0){
				result.put("message", "账号未激活");
				result.put("returnCode", "4022");
				return result;
			}
			if(loginRecord!=null){
				id = loginRecord.getId();
			}
			// 初始化redis
			RedisUtil redis = RedisUtil.getInstance();
			// 从redis取出短信验证码
			String redisSmsCode = redis.new Hash().hget("smsCode_" + phone, "smsCode");
			if (StringUtils.isEmpty(redisSmsCode)) {
				result.put("message", "验证码过期");
				result.put("returnCode", "4001");
				return result;
			} else if (!redisSmsCode.equals(smsCode)) {
				result.put("message", "验证码不正确");
				result.put("returnCode", "4002");
				return result;
			}
		}
		try {
			String salt = FileMD5Util.GetSalt();
			String sessionId = request.getSession().getId();
			String effectiveTime = "1";
			Date date = new Date();
			String now = sdf.format(date);
			Login newLogin = new Login();
			// 判断app请求和web请求
			// app
			if (StringUtils.isNotEmpty(type) && Integer.valueOf(type) == 1) {
				// 判断token是否为null,也就是判断app是否是已登录
				if (StringUtils.isNotEmpty(token)) {
					// 已登录则根据token查用户的信息
					Login login = loginService.selectByToken(token);
					// 验证设备
					if (!clientId.equals(login.getClientId())) {
						result.put("message", "账号在其他设备登录");
						result.put("returnCode", "4021");
						return result;
					}
					// 判断token对应的用户信息是否存在,以及token是否过期
					if (login!=null) {
						Date createTime = sdf.parse(login.getCreateTime());
						calendar.setTime(date);
						calendar.add(Calendar.DATE, Integer.parseInt(login.getEffectiveTime()));
						phone = login.getPhone();
						if (StringUtils.isEmpty(phone)){
							result.put("message", "用户身份信息缺失");
							result.put("returnCode", "3003");
							return result;
						}
						Uusers user = uusersService.selectByPhone(phone);
						if (user!=null) {
							phone = user.getPhone();
							smsCode = user.getTemporarypwd();
						}
						// token过期
						if (calendar.getTime().getTime() < createTime.getTime()) {
							// 产生新的token
							token = FileMD5Util.getMD5String(phone + now + salt);
						}
						login = new Login(FormatUtil.createUuid(), phone, token, salt, now, effectiveTime, sessionId,
								null, null, "1", clientId);
						loginService.insertSelective(login);
						uniqueLoginService.deleteByPhoneFromApp(phone);
						uniqueLoginService.insert(new UniqueLogin(FormatUtil.createUuid(),phone,"",token,clientId,"1",now));
					}
				}else {
					// 首次登录,或退出账号时
					token = FileMD5Util.getMD5String(phone + now + salt);
					newLogin = new Login(FormatUtil.createUuid(), phone, token, salt, now, effectiveTime, sessionId,
							null, null, "1", clientId);
					loginService.insertSelective(newLogin);
					UniqueLogin uniqueLogin = uniqueLoginService.selectByPhoneFromApp(phone);
					if(uniqueLogin!=null){
						//删除app上次登录记录
						uniqueLoginService.deleteByPhoneFromApp(phone);
					}
					//添加本次登录记录
					uniqueLoginService.insert(new UniqueLogin(FormatUtil.createUuid(),phone,"",token,clientId,"1",now));
				}
				Uusers user = uusersService.selectByPhone(phone);
				if(user==null || StringUtils.isEmpty(user.getCompanyId())){
					result.put("message", "用户身份信息缺失");
					result.put("returnCode", "3003");
					return result;
				}
				//companyService.s
				result.put("userId", user.getUserid());
				result.put("companyId",user.getCompanyId());
				Uroles roles = uusersRolesService.SelectRoleByUserId(user.getUserid(), user.getCompanyId());
				if(roles==null || StringUtils.isEmpty(roles.getRolename())){
					result.put("message", "用户身份信息缺失");
					result.put("returnCode", "3003");
					return result;
				}
				result.put("roles", roles.getRolename());
			}
			// web
			if (type != null && Integer.valueOf(type) == 0) {
				//通过手机号码查出用户信息
				Uusers uuser = uusersService.selectByPhone(phone);
				//通过用户的ID查询出 用户 公司关联表信息
				UserCompanyDefault ucd = userCompanyService.selectBySoleUserId(uuser.getUserid());
				
				Uroles uroles = uusersRolesService.SelectRoleByUserId(uuser.getUserid(),ucd.getCompanyId());
				
				if(uroles.getRoleid().equals(Uroles.user_role)){
					result.put("message", "没有权限");
					result.put("returnCode", "4000");
					return result;
				}
				
				newLogin = new Login(FormatUtil.createUuid(), phone, null, null, now, effectiveTime, sessionId, null,
						null, "1", "web");
				loginService.insertSelective(newLogin);
				UniqueLogin uniqueLogin = uniqueLoginService.selectByPhoneFromWeb(phone);
				if(uniqueLogin!=null){
					uniqueLoginService.deleteByPhoneFromWeb(phone);
				}
				uniqueLoginService.insert(new UniqueLogin(FormatUtil.createUuid(),phone,sessionId,"","","0",now));
				Uusers user = uusersService.selectCompanyBySessionId(sessionId);
				if(user==null || StringUtils.isEmpty(user.getCompanyId())){
					result.put("message", "用户身份信息缺失");
					result.put("returnCode", "3003");
					return result;
				}
				result.put("companyId", user.getCompanyId());
				result.put("userId", user.getUserid());
				Uroles roles = uusersRolesService.SelectRoleByUserId(user.getUserid(), user.getCompanyId());
				if(roles==null || StringUtils.isEmpty(roles.getRolename())){
					result.put("message", "用户身份信息缺失");
					result.put("returnCode", "3003");
					return result;
				}
				result.put("roles", roles.getRolename());
				session.setAttribute("userId",user.getUserid());
				session.setAttribute("companyId", user.getCompanyId());
			}
			if (StringUtils.isNotEmpty(id) ){
				if(type != null && Integer.valueOf(type) == 0) {
					loginService.updateStatusById(id,"web");
					loginService.deleteByPrimatyKey(id,"web");
					/*if (i <= 0) {
						result.put("message", "session替换失败");
						result.put("returnCode", "4023");
						return result;
					}*/
				}else if(type != null && Integer.valueOf(type) == 1){
					loginService.updateStatusById(id,"");
					loginService.deleteByPrimatyKey(id,"");
					/*if (i <= 0) {
						result.put("message", "token替换失败");
						result.put("returnCode", "4023");
						return result;
					}*/
				}
				
			}
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(phone, smsCode);
			Subject subject = SecurityUtils.getSubject();
			subject.login(usernamePasswordToken); // 完成登录
			if (Integer.valueOf(type) == 1) {
				result.put("token", token);
			}
			session.setAttribute("phone", phone);
			result.put("message", "登录成功!");
			result.put("returnCode", "3000");
			return result;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			String url = request.getRequestURI();
			logger.info("url :" + url + "message : 没有登录认证");
			result.put("message", "无访问权限");
			result.put("returnCode", "4000");
			return result;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.put("returnCode", "3007");
			result.put("message", "参数格式不正确");
			logger.info(e);
			return result;
		} catch (NullPointerException e) {
			e.printStackTrace();
			result.put("returnCode", "3006");
			result.put("message", "必传参数为空");
			logger.info(e);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "服务器错误");
			logger.info(e);
			return result;
		}

	}
}
