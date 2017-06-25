package com.hust.docMgr.blog.domain;

public class VisitorLogBean {
	private int id=-1;
	private String visitorName;
	private String visitorTime;
	private String visitorInfo;
	private String visitorIp;
	private String reVisitorName;//±ª∑√Œ ’ﬂ
	
	public String getReVisitorName() {
		return reVisitorName;
	}
	public void setReVisitorName(String reVisitorName) {
		this.reVisitorName = reVisitorName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVisitorName() {
		return visitorName;
	}
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	public String getVisitorTime() {
		return visitorTime;
	}
	public void setVisitorTime(String visitorTime) {
		this.visitorTime = visitorTime;
	}
	public String getVisitorInfo() {
		return visitorInfo;
	}
	public void setVisitorInfo(String visitorInfo) {
		this.visitorInfo = visitorInfo;
	}
	public String getVisitorIp() {
		return visitorIp;
	}
	public void setVisitorIp(String visitorIp) {
		this.visitorIp = visitorIp;
	}

}
