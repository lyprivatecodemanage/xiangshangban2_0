package com.xiangshangban.transit_service.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiangshangban.transit_service.bean.Uusers;

@RestController
@RequestMapping("/loginController")
public class LoginController {
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping(value="/loginUser",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String loginUser(String username,String password,HttpSession session) {
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);   //完成登录
            Uusers user=(Uusers) subject.getPrincipal();
            session.setAttribute("user", user);
            return "index";
        } catch(Exception e) {
            return "login";//返回登录页面
        }
        
    }
    @RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
//        session.removeAttribute("user");
        return "login";
    }
}
