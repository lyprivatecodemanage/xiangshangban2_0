package com.xiangshangban.transit_service.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.transit_service.bean.Login;
import com.xiangshangban.transit_service.bean.PersonalInformationVerification;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.service.LoginService;
import com.xiangshangban.transit_service.service.UusersService;
import com.xiangshangban.transit_service.util.RedisUtil;

@RestController
@RequestMapping("/ChangePhoneNumController")
public class ChangePhoneNumController {
	Logger logger = Logger.getLogger(ChangePhoneNumController.class);
	@Autowired
	private UusersService uusersService;
	@Autowired
	private LoginService loginService;

	/**
	 * @author 李业/更改手机号中判断新手机号是否注册以及是否活跃
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
				Long lastTime = lastLoginTime.getTime();
				Long firstTime = nowTime.getTime();
				Long dayNum = (firstTime - lastTime) / (1000 * 60 * 60 * 24);
				// 判断用户是否活跃
				if (dayNum < 90) {
					// 活跃
					result.put("message", "当前手机处于活跃状态,不可被重新注册");
					result.put("returnCode", "4010");
					return result;
				} else {
					// 不活跃
					result.put("message", "当前手机处于不活跃状态,请输入验证码继续...");
					result.put("returnCode", "4011");
					return result;
				}
			}
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
	 * @author 李业/不活跃号码判断是否在保护期
	 * @param phone
	 * @param smsCode
	 * @return
	 */
	@RequestMapping("/isProtect")
	public Map<String, Object> isProtect(String phone, String smsCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			RedisUtil redis = RedisUtil.getInstance();
			String redisSmsCode = redis.new Hash().hget("smsCode_" + phone, "smsCode");
			if (redisSmsCode == null) {
				result.put("message", "验证码已过期失效,请重新获取");
				result.put("returnCode", "4001");
				return result;
			}
			if (!smsCode.equals(redisSmsCode)) {
				result.put("message", "验证码不正确");
				result.put("returnCode", "4002");
				return result;
			}
			Login login = loginService.selectOneByPhone(phone);
			Date lastLoginTime = sdf.parse(login.getCreateTime());
			Date nowTime = new Date();
			Long lastTime = lastLoginTime.getTime();
			Long firstTime = nowTime.getTime();
			Long dayNum = (firstTime - lastTime) / (1000 * 60 * 60 * 24);
			// 判断手机号是否在保护期内
			if (dayNum < 180) {
				// 在保护期
				result.put("message", "该手机号当前处于保护期状态,请继续填写身份信息认证以完成申请...");
				result.put("returnCode", "4012");
				return result;
			} else {
				// 不在保护期
				result.put("message", "该手机号不处于保护期");
				result.put("returnCode", "4013");
				return result;
			}
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
	 * @author 李业/号码在保护期,进行的身份认证
	 * @param phone
	 * @param userName
	 * @param companyName
	 * @return
	 */
	public Map<String, Object> identityAuthentication(String phone, String userName, String companyName) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			int i = uusersService.selectIdentityAuthentication(phone, userName, companyName);
			// 判断输入的身份信息是否正确
			if (i > 0) {
				// 正确
				result.put("message", "处于保护期,提出修改认证信息通过");
				result.put("returnCode", "4014");
				return result;
			}
			result.put("message", "处于保护期,提出修改认证信息未通过");
			result.put("returnCode", "4015");
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

	public Map<String, Object> personalInformationVerification(String oldPhone,String newPhone,String userName,String postName) {
		Map<String, Object> result = new HashMap<String, Object>();
		//生成一个随机验证码
		int verificationCode =  (int)Math.floor(Math.random()*100000);
		try {
			List<PersonalInformationVerification>  personalInformationVerification  =uusersService.selectPersonalInformationVerification(oldPhone, userName, postName);
			if(personalInformationVerification.size()>0){
				result.put("personalInformationVerification", personalInformationVerification);
				result.put("verificationCode", verificationCode);
				result.put("message","本人认证信息通过");
				result.put("returnCode","4016");
				return result;
			}
			result.put("message", "本人认证信息未通过");
			result.put("returnCode", "4017");
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
