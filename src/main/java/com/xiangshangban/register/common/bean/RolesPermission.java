package com.xiangshangban.register.common.bean;

public class RolesPermission {
    private String roleid;

    private String permissionid;

    public RolesPermission(String roleid, String permissionid) {
        this.roleid = roleid;
        this.permissionid = permissionid;
    }

    public RolesPermission() {
        super();
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getPermissionid() {
        return permissionid;
    }

    public void setPermissionid(String permissionid) {
        this.permissionid = permissionid == null ? null : permissionid.trim();
    }
}