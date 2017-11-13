package com.xiangshangban.transit_service.bean;

public class ChangePhone {
	private String id; //主键id
	private String oldPhone;//旧手机号
	private String newPhone;//新手机号
	private String userId;//用户id
	private String userName;//用户name
	private String postName;//岗位名称
	private String companyId;//公司id
	private String companyName;//公司name
	private String approvalPersonId;//审批人id
	private String approvalPersonName;//审批人name
	private String verificationCode;//认证码
	private String createTime;//创建时间
	private String verificationStatus;//认证状态('0':未完成;'1':已完成)  默认:0
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOldPhone() {
		return oldPhone;
	}
	public void setOldPhone(String oldPhone) {
		this.oldPhone = oldPhone;
	}
	public String getNewPhone() {
		return newPhone;
	}
	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}
	public String getApprovalPersonId() {
		return approvalPersonId;
	}
	public void setApprovalPersonId(String approvalPersonId) {
		this.approvalPersonId = approvalPersonId;
	}
	public String getApprovalPersonName() {
		return approvalPersonName;
	}
	public void setApprovalPersonName(String approvalPersonName) {
		this.approvalPersonName = approvalPersonName;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
