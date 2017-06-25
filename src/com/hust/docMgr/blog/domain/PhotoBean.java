package com.hust.docMgr.blog.domain;

import java.io.Serializable;

public class PhotoBean implements Serializable{
	private int id=-1;
	private String photoAddr="";
	private String photoInfo="";
	private String photoTime="";
	private String photoCreator="";
	public String getPhotoCreator() {
		return photoCreator;
	}
	public void setPhotoCreator(String photoCreator) {
		this.photoCreator = photoCreator;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhotoAddr() {
		return photoAddr;
	}
	public void setPhotoAddr(String photoAddr) {
		this.photoAddr = photoAddr;
	}	
	public String getPhotoInfo() {
		return photoInfo;
	}
	public void setPhotoInfo(String photoInfo) {
		this.photoInfo = photoInfo;
	}
	public String getPhotoTime() {
		return photoTime;
	}
	public void setPhotoTime(String photoTime) {
		this.photoTime = photoTime;
	}
}
