package com.xiangshangban.transit_service.bean;

public class UusersRolesKey {
    private String roleid;

    private String userid;

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public UusersRolesKey() {
    }

    public UusersRolesKey(String roleid, String userid) {
        this.roleid = roleid;
        this.userid = userid;
    }
}