package com.xiangshangban.register.common.controller;

import com.xiangshangban.register.common.bean.Users;
import com.xiangshangban.register.common.service.UsersService;
import com.xiangshangban.register.common.util.MathUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by mian on 2017/10/23.
 */

@RestController
@RequestMapping("user")
public class UsersLoginController {

    @Autowired
    UsersService UsersService;

    @RequestMapping(value = "userlogin",method = RequestMethod.GET)
    public Map<String,Object> userlogin(String phone, String temporarypwd){

        return null;
    }

}
