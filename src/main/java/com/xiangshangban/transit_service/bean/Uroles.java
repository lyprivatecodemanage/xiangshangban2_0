package com.xiangshangban.transit_service.bean;

import java.util.List;

public class Uroles {
	//管理员角色
	public static String admin_role = "a3b69966-befa-11e7-a869-00e04c682380";
	//普通用户角色
	public static String user_role = "dad65fdb-e9e1-48da-a1f2-54e738473700";
	
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

	public Uroles() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}