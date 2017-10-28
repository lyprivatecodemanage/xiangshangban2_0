package com.xiangshangban.register.common.bean;

import java.util.Date;

public class Users {

    //禁止登录
    private static final String u_0 = new String("0");
    //有效登录
    private static final String u_1 = new String("1");

    private String userid;

    private String account;

    private String userpwd;

    private String temporarypwd;

    private String token;

    private String username;

    private String sex;

    private Date birthday;

    private String phone;

    private String email;

    private Date last_login_time;

    private String status;

    public Users(String userid, String account, String userpwd, String temporarypwd, String token, String username, String sex, Date birthday, String phone, String email, Date last_login_time, String status) {
        this.userid = userid;
        this.account = account;
        this.userpwd = userpwd;
        this.temporarypwd = temporarypwd;
        this.token = token;
        this.username = username;
        this.sex = sex;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.last_login_time = last_login_time;
        this.status = status;
    }

    public Users() {
        super();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd == null ? null : userpwd.trim();
    }

    public String getTemporarypwd() {
        return temporarypwd;
    }

    public void setTemporarypwd(String temporarypwd) {
        this.temporarypwd = temporarypwd == null ? null : temporarypwd.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}