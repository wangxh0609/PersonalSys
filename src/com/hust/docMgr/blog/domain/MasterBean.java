package com.hust.docMgr.blog.domain;

import java.io.Serializable;

public class MasterBean implements Serializable{
	private String masterName;
	private String masterPass;
	private String masterSex;
	private String masterOicq;
	
	public String getMasterName() {
		return masterName;
	}
	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}
	public String getMasterOicq() {
		return masterOicq;
	}
	public void setMasterOicq(String masterOicq) {
		this.masterOicq = masterOicq;
	}
	public String getMasterPass() {
		return masterPass;
	}
	public void setMasterPass(String masterPass) {
		this.masterPass = masterPass;
	}
	public String getMasterSex() {
		return masterSex;
	}
	public void setMasterSex(String masterSex) {
		this.masterSex = masterSex;
	}	
}
