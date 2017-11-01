package com.xiangshangban.transit_service.bean;

import java.util.Date;
import java.util.List;

public class Uusers {
    private String userid;

    private String account;

    private String userpwd;

    private String temporarypwd;

    private String salt;

    private String phone;

    private Date lastLoginTime;

    private String status;
    
    private List<Uroles> UrolesList;
    
    

    public List<Uroles> getUrolesList() {
		return UrolesList;
	}

	public void setUrolesList(List<Uroles> urolesList) {
		UrolesList = urolesList;
	}

	public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getTemporarypwd() {
        return temporarypwd;
    }

    public void setTemporarypwd(String temporarypwd) {
        this.temporarypwd = temporarypwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}