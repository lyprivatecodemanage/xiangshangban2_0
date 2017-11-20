package com.xiangshangban.transit_service.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.xiangshangban.transit_service.bean.Upermission;
import com.xiangshangban.transit_service.dao.UusersRolesMapper;

@WebFilter(filterName = "RequestedPermissionsFilter", urlPatterns = "/*")
public class RequestedPermissionsFilter implements Filter {

	@Autowired
	UusersRolesMapper uusersRolesMapper;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.err.println("------授权模块进入-------");
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		boolean flag = false;

		String uri = req.getRequestURI();
		String companyId = req.getHeader("companyId");
		String userId = req.getHeader("userId");

		List<Upermission> list = uusersRolesMapper.SelectUserIdByPermission(userId,companyId);

		for (Upermission upermission : list) {
			if (uri.equals(upermission)) {
				flag = true;
				break;
			}
		}
		if (flag) {
			System.err.println("<---------------权限进入------------------>");
			chain.doFilter(req, res);
		} else {
			System.err.println("<--------无权限--------->");
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
