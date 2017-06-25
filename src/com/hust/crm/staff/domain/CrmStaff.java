package com.hust.crm.staff.domain;

import java.util.Date;

import com.hust.crm.post.domain.CrmPost;


public class CrmStaff {
	/*
	 * 
	 * CREATE TABLE `crm_staff` (
  `staffId` varchar(255) NOT NULL PRIMARY KEY,
  `loginName` varchar(100) DEFAULT NULL,
  `loginPwd` varchar(100) DEFAULT NULL,
  `staffName` varchar(100) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `onDutyDate` datetime DEFAULT NULL,
  `postId` varchar(255) DEFAULT NULL,
  CONSTRAINT FOREIGN KEY (`postId`) REFERENCES `crm_post` (`postId`)
);
	 */
	private boolean isNeedEncry=true;
	public boolean isNeedEncry() {
		return isNeedEncry;
	}

	public void setNeedEncry(boolean isNeedEncry) {
		this.isNeedEncry = isNeedEncry;
	}

	private String staffId;
	private String loginName;
	private String loginPwd;
	private String staffName;
	private String gender;
	private Date onDutyDate;    
	private String headImg;
	private String email;
	private CrmPost post;
	private String phoneNum;
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	private String state;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}	
	

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getOnDutyDate() {
		return onDutyDate;
	}

	public void setOnDutyDate(Date onDutyDate) {
		this.onDutyDate = onDutyDate;
	}

	public CrmPost getPost() {
		return post;
	}

	public void setPost(CrmPost post) {
		this.post = post;
	}
}
