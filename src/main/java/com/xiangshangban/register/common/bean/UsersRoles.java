package com.xiangshangban.register.common.bean;

public class UsersRoles {
    private String userid;

    private String roleid;

    public UsersRoles(String userid, String roleid) {
        this.userid = userid;
        this.roleid = roleid;
    }

    public UsersRoles() {
        super();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }
}