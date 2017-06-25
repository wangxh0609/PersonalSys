package com.hust.docMgr.blog.domain;

import java.io.Serializable;

public class FriendBean implements Serializable {
	private int id=-1;
	private String name="";//fname
	private String sex="ÄÐ";
	private String oicq="";
	private String blog="";	
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBlog() {
		return blog;
	}
	public void setBlog(String blog) {
		this.blog = blog;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOicq() {
		return oicq;
	}
	public void setOicq(String oicq) {
		this.oicq = oicq;
	}
}
