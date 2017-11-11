package com.xiangshangban.transit_service.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.transit_service.bean.Company;
import com.xiangshangban.transit_service.bean.Login;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.service.CompanyService;
import com.xiangshangban.transit_service.service.LoginService;
import com.xiangshangban.transit_service.service.UusersService;
import com.xiangshangban.transit_service.util.FileMD5Util;
import com.xiangshangban.transit_service.util.FormatUtil;
import com.xiangshangban.transit_service.util.RedisUtil;
import com.xiangshangban.transit_service.util.YtxSmsUtil;


@RestController
@RequestMapping("/loginController")
public class LoginController {
	Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private LoginService loginService;
	@Autowired
	private UusersService uusersService;
	@Autowired
	CompanyService companyService;
	/**
	 * @author 李业/获得clientId
	 * @param type
	 * @param imei
	 * @param model
	 * @return
	 *//*
	@RequiresRoles(value={"admin","superAdmin"},logical=Logical.OR)
	@RequestMapping("/getClientId")
	public Map<String, Object> getClientId(String type,String imei,String model){
		Map<String, Object> result = new HashMap<String,Object>();
		try{
		String clientId = FileMD5Util.getMD5String(type+imei+model);
		result.put("clientId", clientId);
		result.put("message", "成功");
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
	}*/
	
	/**
	 * @author 李业/获取二维码
	 * @param session
	 * @return
	 */
	@RequestMapping("/getQrcode")
	public Map<String,Object> getQrcode(String type,String companyId,HttpSession session){
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			String qrcode = "";
			//登录
			if(Integer.valueOf(type)==0){
				String sessionId = session.getId();
				//产生二维码(UUID)
				qrcode = FormatUtil.createUuid();
				RedisUtil redis = RedisUtil.getInstance();
				//将二位码存入redis,设置有效时间300秒
				redis.new Hash().hset("qrcode_"+qrcode, "qrcode", qrcode);
				redis.expire("qrcode_"+qrcode, 300);
				Login login = new Login();
				login.setSessionId(sessionId);
				login.setQrcode(qrcode);
				login.setQrcodeStatus("0");
				login.setId(FormatUtil.createUuid());
				loginService.insertSelective(login);
				qrcode="shjn:login="+qrcode;
			}
			//注册
			if(Integer.valueOf(type)==1){
				//根据公司ID查询出公司编号 生成二维码
				Company company = companyService.selectByPrimaryKey(companyId);
				qrcode="shjn:invite="+company.getCompany_no();
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
	public Map<String,Object> scanCode(String qrcode,HttpServletRequest request){
		Map<String, Object> result = new HashMap<String,Object>();
		try{
			RedisUtil redis = RedisUtil.getInstance();
			String token = request.getHeader("ACCESS_TOKEN");
			//二维码是否过期(过期时间300秒)
			String redisQrcode = redis.new Hash().hget("qrcode_"+qrcode, "qrcode");
			if(redisQrcode==null){
				result.put("message", "二维码已过期");
				result.put("returnCode", "4001");
				return result;
			}
			if(redisQrcode.equals(qrcode)){
				Login webLogin = loginService.selectByQrcode(qrcode);
				Login appLogin = loginService.selectByToken(token);
				//Uusers user = uusersService.selectByPhone(appLogin.getPhone());
				List<String> listRole = uusersService.selectRoles(appLogin.getPhone());
				//判断是否是企业管理员,'0':不是,'1':是
				int i = 0;
				for(String role:listRole){
					if("admin".equals(role)){
						i=i+1;
					}
				}
				if(i==1){
					//建立qrcode,token,sessionId的关联
					webLogin.setToken(token);
					webLogin.setSalt(appLogin.getSalt());
					webLogin.setEffectiveTime(appLogin.getEffectiveTime());
					webLogin.setPhone(appLogin.getPhone());
					//设置未扫描状态
					webLogin.setQrcodeStatus("1");
					loginService.updateByPrimaryKeySelective(webLogin);
				}else{
					loginService.deleteByPrimatyKey(webLogin.getId());
					result.put("message", "没有企业管理员的权限");
					result.put("returnCode", "4002");
					return result;
				}
			}else{
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
	public Map<String,Object> confirmLogin(HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			String token = request.getHeader("ACCESS_TOKEN");
			Login login = loginService.selectByToken(token);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//设置登录时间
			login.setCreateTime(sdf.format(new Date()));
			//更改二维码扫描状态
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
	public Map<String,Object> training(HttpServletRequest request,HttpSession session){
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			String sessionId = session.getId();
			//获取app扫码状态
			Login login = loginService.selectBySessionId(sessionId);
			if(Integer.valueOf(login.getQrcodeStatus())!=2){
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
	@RequestMapping(value = "/loginUser")
	public Map<String, Object> loginUser(String phone, String smsCode, HttpSession session,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		String type = request.getHeader("type");
		String token = request.getHeader("ACCESS_TOKEN");
		String UserAgent = request.getHeader("User-Agent");
		RedisUtil redis = RedisUtil.getInstance();
		String redisSmsCode ="";
		if(phone != null && !"".equals(phone)){
		 redisSmsCode= redis.new Hash().hget("smsCode_"+phone, "smsCode");
		 if(redisSmsCode==null){
			 result.put("message", "验证码过期");
			 result.put("returnCode", "4001");
			 return result;
		 }else if(!redisSmsCode.equals(smsCode)){
			 result.put("message", "验证码不正确");
			 result.put("returnCode", "4002");
			 return result;
		 }
		}
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
						//loginService.selectByPhone(phone);
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
				
				UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(phone, smsCode);
				Subject subject = SecurityUtils.getSubject();
				usernamePasswordToken.setRememberMe(true);
				subject.login(usernamePasswordToken); // 完成登录
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
	 * @author 李业/shiro退出
	 * @param session
	 * @return
	 */
	@RequiresRoles(value={"admin","superAdmin"},logical=Logical.OR)
	@RequestMapping(value = "/logOut")
	public Map<String,Object> logOut(HttpSession session) {
		Map<String,Object> result = new HashMap<String,Object>();
		try{
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
	public Map<String,Object> sendSms(String phone,HttpSession session) {
		Map<String,Object> result = new HashMap<String,Object>();
		YtxSmsUtil sms = new YtxSmsUtil("LTAIcRopzlp5cbUd", "VnLMEEXQRukZQSP6bXM6hcNWPlphiP");
		try {
			Uusers user = uusersService.selectByPhone(phone);
			String smsCode = sms.sendIdSms(phone);
			//user不为null,说明是登录获取验证码
			if(user!=null){
				//更新数据库验证码记录,当做登录凭证
				uusersService.updateSmsCode(phone, smsCode);
			}
			RedisUtil redis = RedisUtil.getInstance();
			//设值
			redis.new Hash().hset("smsCode_"+phone, "smsCode", smsCode);
			//设置redis保存时间
			redis.expire("smsCode_"+phone, 120);
			//设置返回结果
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
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("message", "没有权限");
		result.put("returnCode", "4000");
		String url = request.getRequestURI();
		logger.info("url :"+url+"message : 没有权限");
		return result;
	}
}

