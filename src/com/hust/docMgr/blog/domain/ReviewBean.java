package com.hust.docMgr.blog.domain;

import java.io.Serializable;

public class ReviewBean implements Serializable{
	private int id=0;
	private int articleId=0;
	private String reAuthor="";
	private String reContent="";
	private String reSdTime="";
	private int parentId=-1;
	private String toName;
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	private int flag=-1;//代表没看过该评论
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}	
	public String getReAuthor() {
		return reAuthor;
	}
	public void setReAuthor(String reAuthor) {
		this.reAuthor = reAuthor;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReContent() {
		return reContent;
	}
	public void setReContent(String reContent) {
		this.reContent = reContent;
	}
	public String getReSdTime() {
		return reSdTime;
	}
	public void setReSdTime(String reSdTime) {
		this.reSdTime = reSdTime;
	}
}
