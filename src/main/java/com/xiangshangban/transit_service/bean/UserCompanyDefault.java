package com.xiangshangban.transit_service.bean;

public class UserCompanyDefault {
	
	public static String status_0 = "0";
	
	public static String status_1 = "1";
	private String userId;
	private String companyId;
	private String currentOption;
	
	public UserCompanyDefault(){}
	
	public UserCompanyDefault(String userId, String companyId, String currentOption) {
		this.userId = userId;
		this.companyId = companyId;
		this.currentOption = currentOption;
	}
	
	public UserCompanyDefault(String userId, String companyId) {
		super();
		this.userId = userId;
		this.companyId = companyId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCurrentOption() {
		return currentOption;
	}
	public void setCurrentOption(String currentOption) {
		this.currentOption = currentOption;
	}
	
	
}
