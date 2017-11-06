package com.xiangshangban.transit_service.bean;

public class Login {
    private String id;//主键
	private String phone;//电话号码
	private String token;//token
	private String salt;//盐
	private String createTime;//创建时间
	private String effectiveTime;//有效期几天,整数
	private String sessionId;//sessionid
	private String qrcode;//二维码
	private String qrcodeStatus;//二维码扫描状态
	
	
	
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getQrcodeStatus() {
		return qrcodeStatus;
	}

	public void setQrcodeStatus(String qrcodeStatus) {
		this.qrcodeStatus = qrcodeStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	
}