package com.hust.docMgr.blog.domain;

import java.io.Serializable;

public class ArticleTypeBean implements Serializable{
	private int id=-1;
	private String typeName="";
	private String typeInfo="";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeInfo() {
		return typeInfo;
	}
	public void setTypeInfo(String typeInfo) {
		this.typeInfo = typeInfo;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
