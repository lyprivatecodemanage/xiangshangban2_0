package com.xiangshangban.transit_service.bean;

import java.io.Serializable;

public class Version implements Serializable{
	private static final long serialVersionUID = 4340983807247775863L;
	private String versionCode;
	private String versionName;
	private String versionAddress;
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getVersionAddress() {
		return versionAddress;
	}
	public void setVersionAddress(String versionAddress) {
		this.versionAddress = versionAddress;
	}
	
}
