package com.xiangshangban.transit_service.bean;

public class UusersEmployeeKey {
    private String userId;

    private String employeeId;

    private String companyId;

    public UusersEmployeeKey() {
    }

    public UusersEmployeeKey(String userId, String employeeId, String companyId) {
        this.userId = userId;
        this.employeeId = employeeId;
        this.companyId = companyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}