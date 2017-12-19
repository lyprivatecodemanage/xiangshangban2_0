package com.xiangshangban.transit_service.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.xiangshangban.transit_service.util.RedisUtil;

/**
 * @author 李业 /认证拦截器
 * @author cachee
 *
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
	
	private static final Logger log = Logger.getLogger(CustomFormAuthenticationFilter.class);
	//原FormAuthenticationFilter的认证方法
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
			boolean flag = super.onAccessDenied(request, response);
			return flag;
	
	}
	 @Override
	    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
	            throws Exception {
	        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	        String url = this.getSuccessUrl();
	        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + url);    //页面跳转
	        return false;
	    }
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		HttpServletRequest req = (HttpServletRequest)request;
		String type = req.getHeader("type");
		String token = req.getHeader("token");
		String clientId = req.getHeader("clientId");
		String phone = "";
		RedisUtil redis = RedisUtil.getInstance();
		if("1".equals(type)){
			if(StringUtils.isEmpty(username)&&StringUtils.isEmpty(password)){
				/*if(StringUtils.isNotEmpty(token)){
					//从redis缓存中查phone 和 smsCode
					phone = redis.getJedis().hget(token, "token");
					if(StringUtils.isNotEmpty(phone)){
						//查到phone，然后根据查到的phone去数据库查询smsCode
						username = phone;//redis中查到的Phone
						password = token;
					}else{
						username = "";
						password = token;
					}
				}*/
				username = clientId;
				password = token;
						
			}
		}
		
       return createToken(username, password, request, response);
   }
		
}
