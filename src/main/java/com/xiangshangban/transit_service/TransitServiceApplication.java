package com.xiangshangban.transit_service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.apache.shiro.mgt.SecurityManager;
import com.xiangshangban.transit_service.filter.ServletFilter;
import com.xiangshangban.transit_service.realm.MyRealm;
import com.xiangshangban.transit_service.shiro.CredentialsMatcher;

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
    
    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/loginController/loginUser");
        bean.setSuccessUrl("/loginController/login");
        bean.setUnauthorizedUrl("/loginController/unAuthorizedUrl");
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<String,String>();
       /* filterChainDefinitionMap.put("/jsp/login.jsp*", "anon"); //表示可以匿名访问
        filterChainDefinitionMap.put("/loginUser", "anon"); 
        filterChainDefinitionMap.put("/logout*","anon");
        filterChainDefinitionMap.put("/jsp/error.jsp*","anon");
        filterChainDefinitionMap.put("/jsp/index.jsp*","authc");
        filterChainDefinitionMap.put("/*", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/*.*", "authc");*/
        filterChainDefinitionMap.put("/loginUser", "anon");
       /* filterChainDefinitionMap.put("/*", "authc");*///表示需要认证才可以访问
       /* filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/*.*", "authc");*/
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }
    //配置核心安全事务管理器
    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") MyRealm myRealm) {
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(myRealm);
        return manager;
    }
    //配置自定义的权限登录器
    @Bean(name="authRealm")
    public MyRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
    	MyRealm myRealm=new MyRealm();
    	myRealm.setCredentialsMatcher(matcher);
        return myRealm;
    }
    //配置自定义的密码比较器
    @Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
}
