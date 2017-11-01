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
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiangshangban.transit_service.bean.Upermission;
import com.xiangshangban.transit_service.bean.Uroles;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.service.UusersService;

import ch.qos.logback.core.subst.Token;

public class MyRealm extends AuthorizingRealm {
	@Autowired
	private UusersService usersService;
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
		 System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
		    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		    //Uusers user = usersService.selectByPhone(token.getPrimaryPrincipal().toString());
		    //获取角色
		    List<String> list = usersService.selectRoles(token.getPrimaryPrincipal().toString());
		   for(int i =0;i<list.size();i++){
			   authorizationInfo.addRole(list.get(i).toString());
		   }
		   /* for(Uroles role:userInfo.getUrolesList()){
		        authorizationInfo.addRole(role.getRoleid());
		        for(Upermission p:role.getPermissionList()){
		            authorizationInfo.addStringPermission(p.getPermissionurl());
		        }
		    }*/
		    return authorizationInfo;
	}
	
	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// token用户输入
		// 第一步从token中取出身份信息
		String userCode = token.getPrincipal().toString();
		System.out.println(token.toString());
		//第二部:根据用户输入的userCode从数据库查询
		//....
		//根据从数据库查询到密码
		Uusers user = usersService.selectByPhone(userCode);
		//String password = user.getUserpwd();
		String password = user.getTemporarypwd();
		//String password = "111111";
		//如果查询到返回认证信息AuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userCode,password,this.getName());
		return simpleAuthenticationInfo;

	}

}
