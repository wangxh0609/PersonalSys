package com.hust.docMgr.blog.domain;

import java.io.Serializable;

public class WordBean implements Serializable{
	private int id;
	private String wordTitle;
	private String wordContent;
	private String wordTime;
	private String wordAuthor;
	private int parentId=-1;
	private String toName;
	private String bozName;
	private int firstParentId=-1;
	
	public int getFirstParentId() {
		return firstParentId;
	}
	public void setFirstParentId(int firstParentId) {
		this.firstParentId = firstParentId;
	}
	public String getBozName() {
		return bozName;
	}
	public void setBozName(String bozName) {
		this.bozName = bozName;
	}
	private int flag=-1;//代表没看过该留言
    public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
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
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWordAuthor() {
		return wordAuthor;
	}
	public void setWordAuthor(String wordAuthor) {
		this.wordAuthor = wordAuthor;
	}
	public String getWordContent() {
		return wordContent;
	}
	public void setWordContent(String wordContent) {
		this.wordContent = wordContent;
	}
	public String getWordTime() {
		return wordTime;
	}
	public void setWordTime(String wordTime) {
		this.wordTime = wordTime;
	}
	public String getWordTitle() {
		return wordTitle;
	}
	public String getWordTitle(int len){
		if(len<=0||len>wordTitle.length())
			len=wordTitle.length();
		return wordTitle.substring(0,len)+"...";
	}
	public void setWordTitle(String wordTitle) {
		this.wordTitle = wordTitle;
	}
}
