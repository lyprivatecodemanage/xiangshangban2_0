package com.xiangshangban.transit_service.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * 
 * <p>
 * Title: CustomFormAuthenticationFilter
 * </p>
 * <p>
 * Description:自定义FormAuthenticationFilter，认证之前实现 验证码校验
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
	
	// 原FormAuthenticationFilter的认证方法
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		//HttpServletRequest res = (HttpServletRequest)request;
		//System.out.println("CustomFormAuthenticationFilter:\t"+res.getSession().getId());
		return true;
	}
		
}
