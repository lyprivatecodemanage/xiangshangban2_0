package com.xiangshangban.transit_service.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiangshangban.transit_service.util.RedisUtil;
/**
 * 
 * @author 李业
 *
 */
public class VerificationCodeInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getRequestURI().contains("/loginController/confirmSms")) {
			Map<String,Object> result = new HashMap<String,Object>();
			String phone = request.getParameter("phone");
			String smsCode = request.getParameter("smsCode");
			// 初始化redis
			RedisUtil redis = RedisUtil.getInstance();
			// 从redis取出短信验证码
			String redisSmsCode = redis.new Hash().hget("smsCode_" + phone, "smsCode");
			if (StringUtils.isEmpty(redisSmsCode)) {
				result.put("message", "验证码过期");
				result.put("returnCode", "4001");
				String jsonString = JSON.toJSONString(result);
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(jsonString);
				return false;
			} else if (!redisSmsCode.equals(smsCode)) {
				result.put("message", "验证码不正确");
				result.put("returnCode", "4002");
				String jsonString = JSON.toJSONString(result);
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(jsonString);
				return false;
			}
		}
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
