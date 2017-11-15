package com.xiangshangban.transit_service.bean;

public class CheckPendingJoinCompany {

	public static String status_0 = "0";
	
	public static String status_1 = "1";
	
    private String userid;

    private String companyid;

    private String status;
    
    private String applyTime;

    public CheckPendingJoinCompany(String userid, String companyid, String status,String applyTime) {
        this.userid = userid;
        this.companyid = companyid;
        this.status = status;
        this.applyTime = applyTime;
    }

    public CheckPendingJoinCompany() {
        super();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid == null ? null : companyid.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
    
}