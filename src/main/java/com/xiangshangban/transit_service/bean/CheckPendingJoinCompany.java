package com.xiangshangban.transit_service.bean;

public class CheckPendingJoinCompany {

    private String userid;

    private String companyid;

    private String status;

    public CheckPendingJoinCompany(String userid, String companyid, String status) {
        this.userid = userid;
        this.companyid = companyid;
        this.status = status;
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
}