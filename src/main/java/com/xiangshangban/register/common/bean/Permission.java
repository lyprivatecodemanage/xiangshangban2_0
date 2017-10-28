package com.xiangshangban.register.common.bean;

public class Permission {
    private String permissionid;

    private String permissionurl;

    private String permissiondescribe;

    public Permission(String permissionid, String permissionurl, String permissiondescribe) {
        this.permissionid = permissionid;
        this.permissionurl = permissionurl;
        this.permissiondescribe = permissiondescribe;
    }

    public Permission() {
        super();
    }

    public String getPermissionid() {
        return permissionid;
    }

    public void setPermissionid(String permissionid) {
        this.permissionid = permissionid == null ? null : permissionid.trim();
    }

    public String getPermissionurl() {
        return permissionurl;
    }

    public void setPermissionurl(String permissionurl) {
        this.permissionurl = permissionurl == null ? null : permissionurl.trim();
    }

    public String getPermissiondescribe() {
        return permissiondescribe;
    }

    public void setPermissiondescribe(String permissiondescribe) {
        this.permissiondescribe = permissiondescribe == null ? null : permissiondescribe.trim();
    }
}