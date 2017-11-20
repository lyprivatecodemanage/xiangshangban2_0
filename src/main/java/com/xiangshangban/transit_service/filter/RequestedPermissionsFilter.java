package com.xiangshangban.transit_service.filter;

//@WebFilter(filterName = "RequestedPermissionsFilter", urlPatterns = "/*")
public class RequestedPermissionsFilter {
//
//	@Autowired
//	UusersRolesMapper uusersRolesMapper;
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
	// System.err.println("------授权模块进入-------");
//		// TODO Auto-generated method stub
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//
//		boolean flag = false;
//
//		String uri = req.getRequestURI();
//		String companyId = req.getHeader("companyId");
//		String userId = req.getHeader("userId");
//
//		List<Upermission> list = uusersRolesMapper.SelectUserIdByPermission(userId,companyId);
//
//		for (Upermission upermission : list) {
//			if (uri.equals(upermission)) {
//				flag = true;
//				break;
//			}
//		}
//		if (flag) {
	// System.err.println("<---------------权限进入------------------>");
//			chain.doFilter(req, res);
//		} else {
	// System.err.println("<--------无权限--------->");
//		}
//	}
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//
//	}
//
}
