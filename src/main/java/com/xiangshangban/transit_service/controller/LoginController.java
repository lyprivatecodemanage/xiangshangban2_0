package com.xiangshangban.transit_service.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xiangshangban.transit_service.bean.Company;
import com.xiangshangban.transit_service.bean.Login;
import com.xiangshangban.transit_service.bean.UniqueLogin;
import com.xiangshangban.transit_service.bean.Uroles;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.service.CompanyService;
import com.xiangshangban.transit_service.service.LoginService;
import com.xiangshangban.transit_service.service.UniqueLoginService;
import com.xiangshangban.transit_service.service.UusersRolesService;
import com.xiangshangban.transit_service.service.UusersService;
import com.xiangshangban.transit_service.util.FileMD5Util;
import com.xiangshangban.transit_service.util.FormatUtil;
import com.xiangshangban.transit_service.util.RedisUtil;
import com.xiangshangban.transit_service.util.YtxSmsUtil;

@RestController
@RequestMapping("/loginController")
public class LoginController {
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
	
	/**
	 * @author 李业/获取二维码
	 * @param session
	 * @return
	 */
	@RequestMapping("/getQrcode")
	public Map<String, Object> getQrcode(String type, String companyId, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String qrcode = "";
			// 登录
			if (Integer.valueOf(type) == 0) {
				String sessionId = session.getId();
				// 产生二维码(UUID)
				qrcode = FormatUtil.createUuid();
				RedisUtil redis = RedisUtil.getInstance();
				// 将二位码存入redis,设置有效时间300秒
				redis.new Hash().hset("qrcode_" + qrcode, "qrcode", qrcode);
				redis.expire("qrcode_" + qrcode, 300);
				Login login = new Login();
				login.setSessionId(sessionId);
				login.setQrcode(qrcode);
				login.setQrcodeStatus("0");
				login.setId(FormatUtil.createUuid());
				loginService.insertSelective(login);
				qrcode = "shjn:login=" + qrcode;
			}
			// 注册
			if (Integer.valueOf(type) == 1) {

				String format = "http://www.xiangshangban.com/show?shjncode=invite_";
				// 根据公司ID查询出公司编号 生成二维码
				Company company = companyService.selectByPrimaryKey(companyId);
				Map<String, String> invite = new HashMap<>();
				invite.put("companyNo", company.getCompany_no());
				invite.put("companyName", company.getCompany_name());
				invite.put("companyPersonalName", company.getCompany_personal_name());
				qrcode = format + JSON.toJSONString(invite);
			}
			result.put("qrcode", qrcode);
			result.put("message", "成功");
			result.put("returnCode", "3000");
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
	 * @author 李业/app扫描二维码
	 * @param qrcode
	 * @param request
	 * @return
	 */
	@RequestMapping("/scanCode")
	public Map<String, Object> scanCode(String qrcode, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			RedisUtil redis = RedisUtil.getInstance();
			String token = request.getHeader("ACCESS_TOKEN");
			// 二维码是否过期(过期时间300秒)
			String redisQrcode = redis.new Hash().hget("qrcode_" + qrcode, "qrcode");
			if (redisQrcode == null) {
				result.put("message", "二维码已过期");
				result.put("returnCode", "4001");
				return result;
			}
			if (redisQrcode.equals(qrcode)) {
				Login webLogin = loginService.selectByQrcode(qrcode);
				Login appLogin = loginService.selectByToken(token);
				// Uusers user =
				// uusersService.selectByPhone(appLogin.getPhone());
				List<Uroles> listRole = uusersService.selectRoles(appLogin.getPhone());
				// 判断是否是企业管理员,'0':不是,'1':是
				int i = 0;
				for (Uroles role : listRole) {
					if ("admin".equals(role.getRolename())) {
						i = i + 1;
					}
				}
				if (i == 1) {
					// 建立qrcode,token,sessionId的关联
					webLogin.setToken(token);
					webLogin.setSalt(appLogin.getSalt());
					webLogin.setEffectiveTime(appLogin.getEffectiveTime());
					webLogin.setPhone(appLogin.getPhone());
					// 设置未扫描状态
					webLogin.setQrcodeStatus("1");
					loginService.updateByPrimaryKeySelective(webLogin);
				} else {
					loginService.deleteByPrimatyKey(webLogin.getId());
					result.put("message", "没有企业管理员的权限");
					result.put("returnCode", "4002");
					return result;
				}
			} else {
				result.put("message", "二维码不正确");
				result.put("returnCode", "4001");
				return result;
			}
			result.put("message", "扫码成功,请确认登录");
			result.put("returnCode", "3000");
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
	 * @author 李业/app确认二维码登录
	 * @param request
	 * @return
	 */
	@RequestMapping("/confirmLogin")
	public Map<String, Object> confirmLogin(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String token = request.getHeader("ACCESS_TOKEN");
			Login login = loginService.selectByToken(token);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 设置登录时间
			login.setCreateTime(sdf.format(new Date()));
			// 更改二维码扫描状态
			login.setQrcodeStatus("2");
			loginService.updateByPrimaryKeySelective(login);
			result.put("message", "登录成功");
			result.put("returnCode", "3000");
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
	 * @author 李业/web二维码轮询接口
	 * @return
	 */
	@RequestMapping("/training")
	public Map<String, Object> training(HttpServletRequest request, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String sessionId = session.getId();
			// 获取app扫码状态
			Login login = loginService.selectBySessionId(sessionId);
			if (Integer.valueOf(login.getQrcodeStatus()) != 2) {
				result.put("message", "二维码未确认登录,请稍后...");
				result.put("returnCode", "4003");
				return result;
			}
			login.setQrcodeStatus("3");
			loginService.updateByPrimaryKeySelective(login);
			result.put("message", "登录成功");
			result.put("returnCode", "3000");
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
		String token = request.getHeader("ACCESS_TOKEN");
		String clientId = request.getHeader("clientId");
		String id = "";
		if (phone != null && !"".equals(phone)) {
			// 判断手机号是否注册
			Uusers user = uusersService.selectByPhone(phone);
			Login loginRecord = loginService.selectOneByPhone(phone);
			if (user == null) {
				result.put("message", "手机号不存在,请注册");
				result.put("returnCode", "4004");
				return result;
			}
			if(!"1".equals(user.getIsActive())){
				result.put("message", "账号未激活");
				result.put("returnCode", "4022");
				return result;
			}
			if(!StringUtils.isEmpty(loginRecord)){
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
			if (!StringUtils.isEmpty(type) && Integer.valueOf(type) == 1) {
				// 判断token是否为null,也就是判断app是否是已登录
				if (!StringUtils.isEmpty(token)) {
					// 已登录则根据token查用户的信息
					Login login = loginService.selectByToken(token);
					// 验证设备
					if (!clientId.equals(login.getClientId())) {
						result.put("message", "账号在其他设备登录");
						result.put("returnCode", "4021");
						return result;
					}
					// 判断token对应的用户信息是否存在,以及token是否过期
					if (!StringUtils.isEmpty(login)) {
						Date createTime = sdf.parse(login.getCreateTime());
						calendar.setTime(date);
						calendar.add(Calendar.DATE, Integer.parseInt(login.getEffectiveTime()));
						phone = login.getPhone();
						if (StringUtils.isEmpty(phone)) {
							result.put("message", "用户身份信息缺失");
							result.put("returnCode", "3003");
							return result;
						}
						Uusers user = uusersService.selectByPhone(phone);
						if (!StringUtils.isEmpty(user)) {
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
						uniqueLoginService.deleteByPhone(phone);
						uniqueLoginService.insert(new UniqueLogin(FormatUtil.createUuid(),phone,"",token,clientId,"1",now));
					}
				} else {
					// 首次登录,或退出账号时
					token = FileMD5Util.getMD5String(phone + now + salt);
					newLogin = new Login(FormatUtil.createUuid(), phone, token, salt, now, effectiveTime, sessionId,
							null, null, "1", clientId);
					loginService.insertSelective(newLogin);
					UniqueLogin uniqueLogin = uniqueLoginService.selectByPhone(phone);
					if(!StringUtils.isEmpty(uniqueLogin)){
						uniqueLoginService.deleteByPhone(phone);
					}
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
				result.put("roles", roles.getRolename());
			}
			// web
			if (type != null && Integer.valueOf(type) == 0) {
				newLogin = new Login(FormatUtil.createUuid(), phone, null, null, now, effectiveTime, sessionId, null,
						null, "1", "web");
				loginService.insertSelective(newLogin);
				UniqueLogin uniqueLogin = uniqueLoginService.selectByPhone(phone);
				if(!StringUtils.isEmpty(uniqueLogin)){
					uniqueLoginService.deleteByPhone(phone);
				}
				uniqueLoginService.insert(new UniqueLogin(FormatUtil.createUuid(),phone,sessionId,"","","1",now));
				Uusers user = uusersService.selectCompanyBySessionId(sessionId);
				if(user==null || StringUtils.isEmpty(user.getCompanyId())){
					result.put("message", "用户身份信息缺失");
					result.put("returnCode", "3003");
					return result;
				}
				result.put("companyId", user.getCompanyId());
				result.put("userId", user.getUserid());
				Uroles roles = uusersRolesService.SelectRoleByUserId(user.getUserid(), user.getCompanyId());
				result.put("roles", roles.getRolename());
			}
			if (!StringUtils.isEmpty(id)) {
				int i = loginService.updateStatusById(id);
				if (i <= 0) {
					result.put("message", "token替换失败");
					result.put("returnCode", "4023");
					return result;
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

	/**
	 * @author 李业/检查登录信息是否改变
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/offsiteLogin")
	public Map<String, Object> offsiteLogin(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//request.getSession().invalidate();
			result.put("message", "账号在别处登录,请重新登录");
			result.put("returnCode", "4021");
			return result;
		} catch (Exception e) {
			logger.info(e);
			result.put("message", "服务器错误");
			result.put("returnCode", "3001");
			return result;
		}

	}

	/**
	 * @author 李业/shiro退出
	 * @param session
	 * @return
	 */
	@RequiresRoles(value = { "admin", "superAdmin" }, logical = Logical.OR)
	@RequestMapping(value = "/logOut")
	public Map<String, Object> logOut(HttpServletRequest request ,HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String phone = "";
			String type = request.getHeader("type");
			if("0".equals(type)){
				Object obj = session.getAttribute("phone");
				if(!StringUtils.isEmpty(obj)){
					phone = obj.toString();
					uniqueLoginService.deleteByPhone(phone);
				}
			}else{
				String token = request.getHeader("ACCESS_TOKEN");
				String clientId = request.getHeader("clientId");
				uniqueLoginService.deleteByTokenAndClientId(token, clientId);
			}
			Subject subject = SecurityUtils.getSubject();
			subject.logout();
			result.put("message", "退出成功");
			result.put("returnCode", "3000");
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
			result.put("message", "参数为null");
			logger.info(e);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "失败");
			logger.info(e);
			return result;
		}
	}

	/**
	 * @author 李业/获取短信验证码
	 * @param phone
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sendSms")
	public Map<String, Object> sendSms(String phone, HttpServletRequest request, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		YtxSmsUtil sms = new YtxSmsUtil("LTAIcRopzlp5cbUd", "VnLMEEXQRukZQSP6bXM6hcNWPlphiP");
		try {
			Uusers user = uusersService.selectByPhone(phone);
			// 获取验证码
			// String smsCode = sms.sendIdSms(phone);
			String smsCode = "6666";
			// user不为null,说明是登录获取验证码
			if (user != null) {
				// 更新数据库验证码记录,当做登录凭证
				uusersService.updateSmsCode(phone, smsCode);
			}
			RedisUtil redis = RedisUtil.getInstance();
			// 设值
			redis.new Hash().hset("smsCode_" + phone, "smsCode", smsCode);
			// 设置redis保存时间
			redis.expire("smsCode_" + phone, 120);
			// 设置返回结果
			result.put("smsCode", smsCode);
			result.put("message", "成功");
			result.put("returnCode", "3000");
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
			result.put("message", "参数为null");
			logger.info(e);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "3001");
			result.put("message", "失败");
			logger.info(e);
			return result;
		}
	}

	/**
	 * @author 李业/无权限请求统一返回接口
	 * @return
	 */
	@RequestMapping(value = "/unAuthorizedUrl")
	public Map<String, Object> unAuthorizedUrl(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("message", "没有权限");
		result.put("returnCode", "4000");
		String url = request.getRequestURI();
		logger.info("url :" + url + "message : 没有权限");
		return result;
	}

}
