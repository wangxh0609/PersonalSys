package com.hust.docMgr.infodb.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;


public class DocMarInfo{
	@Override
	public String toString() {
		return "DocMarInfo [infoId=" + infoId + ", type=" + type + ", source=" + source + ", accountNum=" + accountNum
				+ ", content=" + content + ", memo=" + memo + ", creator=" + creator + ", createTime=" + createTime
				+ ", state=" + state + "]";
	}
	private String infoId;
	private String type;
	private String source;
	private String accountNum;
	private String content;
	private String memo;
	private String creator;
	private Date createTime;
	private String state;
	


	
	public DocMarInfo() {
	}
	public DocMarInfo(String infoId, String type, String source, String accountNum, String content, String memo, String creator, Date createTime, String state) {
		this.infoId = infoId;
		this.type = type;
		this.source = source;
		this.accountNum = accountNum;
		this.content = content;
		this.memo = memo;
		this.creator = creator;
		this.createTime = createTime;
		this.state = state;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
