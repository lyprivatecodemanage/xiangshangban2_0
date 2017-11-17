package com.xiangshangban.transit_service.filter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xiangshangban.transit_service.bean.Login;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.service.LoginService;
import com.xiangshangban.transit_service.service.UusersService;
import com.xiangshangban.transit_service.util.FileMD5Util;
import com.xiangshangban.transit_service.util.FormatUtil;
import com.xiangshangban.transit_service.util.RedisUtil;

/**
 * 
 * <p>Title: CustomFormAuthenticationFilter</p>
 * <p>Description:自定义FormAuthenticationFilter，认证之前实现 验证码校验 </p>
 * <p>Company: www.itcast.com</p> 
 * 
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
	
	//原FormAuthenticationFilter的认证方法
	@Override
	protected boolean onAccessDenied(ServletRequest req,
			ServletResponse res) throws Exception {
		return super.onAccessDenied(req, res);
	}
		
}
