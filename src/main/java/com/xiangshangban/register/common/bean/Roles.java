package com.xiangshangban.register.common.bean;

import java.util.LinkedList;
import java.util.List;

public class Roles {
    private String roleid;

    private String rolename;

    //***做 role --> permission 一对多处理
    private List<Permission> permissions = new LinkedList<Permission>();

    public Roles(String roleid, String rolename) {
        this.roleid = roleid;
        this.rolename = rolename;
    }

    public Roles() {
        super();
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }
}