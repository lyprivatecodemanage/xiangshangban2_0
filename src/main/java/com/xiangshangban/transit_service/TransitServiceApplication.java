package com.xiangshangban.transit_service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.xiangshangban.register.common.util.MathUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import com.xiangshangban.transit_service.filter.ServletFilter;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class TransitServiceApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(TransitServiceApplication.class, args);

		test1("classpath:config/Shiro-realm.ini","wang","123");
		Subject subject = SecurityUtils.getSubject();
		Assert.assertTrue(subject.isPermitted("$user1$2"));//OK
		Assert.assertTrue(subject.isPermitted("$user2$2"));//OK
		Assert.assertTrue(subject.isPermitted("$user1$2"));//OK
		Assert.assertTrue(subject.isPermitted("$user1$8"));//OK
		Assert.assertTrue(subject.isPermitted("$user2$10"));//OK
		Assert.assertTrue(subject.isPermitted("$user1$4"));//OK
		Assert.assertTrue(subject.isPermitted("menu:view"));//OK
    }

	public static void test1(String iniFile,String phone,String newpwd) {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(iniFile);
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(phone, newpwd);
		token.setRememberMe(true);
		try {
			subject.login(token);
			System.out.println(token.getUsername()+"      "+token.getPassword());
			System.out.println("登录成功");

		} catch (AuthenticationException e) {
			//登录失败
			System.out.println("登录失败");
			e.printStackTrace();
		}
		Assert.assertEquals(true, subject.isAuthenticated());
	}
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
  	   FilterRegistrationBean registrationBean = new FilterRegistrationBean();
  	   ServletFilter weChatFilter = new ServletFilter();
  	   registrationBean.setFilter(weChatFilter);
  	   List<String> urlPatterns = new ArrayList<String>();
  	   urlPatterns.add("/*");
  	   registrationBean.setUrlPatterns(urlPatterns);
  	   return registrationBean;
  	}

}
