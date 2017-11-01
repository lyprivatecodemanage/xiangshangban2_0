package com.xiangshangban.transit_service.bean;

import java.util.List;

public class Uroles {
    private String roleid;

    private String rolename;

    private List<Upermission>	permissionList;
    
    
    
    public List<Upermission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Upermission> permissionList) {
		this.permissionList = permissionList;
	}

	public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}