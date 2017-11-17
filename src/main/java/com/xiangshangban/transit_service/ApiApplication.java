package com.xiangshangban.transit_service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.xiangshangban.transit_service.filter.CustomFormAuthenticationFilter;
import com.xiangshangban.transit_service.filter.ServletFilter;
import com.xiangshangban.transit_service.realm.MyRealm;
import com.xiangshangban.transit_service.shiro.CredentialsMatcher;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
@SpringBootApplication
public class ApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
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

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(manager);
		// 配置登录的url
		bean.setLoginUrl("/loginController/loginUser");
		bean.setUnauthorizedUrl("/loginController/unAuthorizedUrl");
		CustomFormAuthenticationFilter formAuthenticationFilter = new CustomFormAuthenticationFilter();
		formAuthenticationFilter.setUsernameParam("phone");
		formAuthenticationFilter.setPasswordParam("smsCode");
		Map<String, Filter> map = new HashMap<String, Filter>();
		map.put("authc", formAuthenticationFilter);
		bean.setFilters(map);
		// 配置访问权限
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/loginController/sendSms", "anon");
		filterChainDefinitionMap.put("/registerController/*", "anon");
		filterChainDefinitionMap.put("/loginController/logOut", "logout");
		// filterChainDefinitionMap.put("/loginController/loginUser", "anon");
		// filterChainDefinitionMap.put("/*", "authc");//表示需要认证才可以访问
		filterChainDefinitionMap.put("/**", "authc");// 表示需要认证才可以访问
		// filterChainDefinitionMap.put("/*.*", "authc");
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean;
	}

	// 配置核心安全事务管理器
	@Bean(name = "securityManager")
	public SecurityManager securityManager(@Qualifier("myRealm") MyRealm myRealm) {
		System.err.println("--------------shiro已经加载----------------");
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(myRealm);
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(3600000);
		sessionManager.setDeleteInvalidSessions(true);
		manager.setSessionManager(sessionManager);
		return manager;
	}

	// 配置自定义的权限登录器
	@Bean(name = "myRealm")
	public MyRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
		MyRealm myRealm = new MyRealm();
		myRealm.setCredentialsMatcher(matcher);
		return myRealm;
	}

	// 配置自定义的密码比较器
	@Bean(name = "credentialsMatcher")
	public CredentialsMatcher credentialsMatcher() {
		return new CredentialsMatcher();
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager manager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(manager);
		return advisor;
	}

	// 设置认证授权异常捕获
	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
		Properties property = new Properties();
		property.setProperty("org.apache.shiro.authz.UnauthenticatedException",
				"redirect:/loginController/unAuthorizedUrl");
		property.setProperty("org.apache.shiro.authz.UnauthorizedException",
				"redirect:/loginController/unAuthorizedUrl");
		simpleMappingExceptionResolver.setExceptionMappings(property);
		return simpleMappingExceptionResolver;
	}

	// 显示声明CommonsMultipartResolver为mutipartResolver
	@Bean(name = "multipartResolver")
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setResolveLazily(true);// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
		resolver.setMaxInMemorySize(1);
		/* resolver.setMaxUploadSize(50*1024*1024);//上传文件大小 50M 50*1024*1024 */
		return resolver;
	}
}
