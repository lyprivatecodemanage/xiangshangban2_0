package com.xiangshangban.transit_service.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * 
 * @author 李业
 *
 */
@Configuration
public class MyInterceptorConfig extends WebMvcConfigurerAdapter{

    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new VerificationCodeInterceptor());
    }

}
