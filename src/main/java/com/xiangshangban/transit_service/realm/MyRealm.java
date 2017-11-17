package com.xiangshangban.transit_service.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiangshangban.transit_service.bean.Upermission;
import com.xiangshangban.transit_service.bean.Uroles;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.service.UpermissionService;
import com.xiangshangban.transit_service.service.UusersService;

public class MyRealm extends AuthorizingRealm {
	@Autowired
	private UusersService usersService;

	@Autowired
	private UpermissionService upermissionService;
	//
	// public void clearAuthz() {
	// this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	// }

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
		System.err.println("<---------------权限配置  MyShiroRealm.doGetAuthorizationInfo()---------------->");

		Uusers uusers = (Uusers) token.getPrimaryPrincipal();

		if (uusers != null) {
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
			// 根据用户信息查询出用户的角色
			List<Uroles> list = usersService.selectRoles(token.getPrimaryPrincipal().toString());

			for (Uroles uroles : list) {
				// 给予shrio 用户角色
				authorizationInfo.addRole(uroles.getRolename());
				List<Upermission> perList = upermissionService.selectByRoleId(uroles.getRoleid());
				for (Upermission upermission : perList) {
					// 给予shrio 权限地址
					authorizationInfo.addStringPermission(upermission.getPermissionurl());
				}
			}
			return authorizationInfo;
		}

		return null;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// token用户输入
		// 第一步从token中取出身份信息
		String userCode = token.getPrincipal().toString();
		System.out.println("=================>" + token.toString());
		// 第二部:根据用户输入的userCode从数据库查询
		// ....
		// 根据从数据库查询到密码
		Uusers user = usersService.selectByPhone(userCode);
		if (user == null) {
			return null;
		}
		// String password = user.getUserpwd();
		String password = user.getTemporarypwd();
		// String password = "111111";
		// 如果查询到返回认证信息AuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userCode, password,
				this.getName());

		return simpleAuthenticationInfo;
	}

}
