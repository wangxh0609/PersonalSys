package com.hust.crm.classes.domain;

import java.util.Date;


import com.hust.crm.coursetype.domain.CrmCourseType;

public class CrmClasses {
	/*
	 * 
	 * CREATE TABLE `crm_class` (
  `classId` varchar(50) NOT NULL PRIMARY KEY,
  `courseTypeId` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `beginTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `totalCount` int(11) DEFAULT NULL,
  `upgradeCount` int(11) DEFAULT NULL,
  `changeCount` int(11) DEFAULT NULL,
  `runoffCount` int(11) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `uploadTime` datetime DEFAULT NULL,
  `uploadPath` varchar(200) DEFAULT NULL,
  `uploadFilename` varchar(100) DEFAULT NULL,
  CONSTRAINT FOREIGN KEY (`courseTypeId`) REFERENCES `crm_course_type` (`courseTypeId`)
) ;
	 */
	
	private String classId;
	//private String courseTypeId;
	private String name;
	private Date beginTime;
	private Date endTime;
	
	private String status;
	private Integer totalCount;
	private Integer upgradeCount;
	
	private Integer changeCount;
	private Integer runoffCount;
	private String remark;
	
	private Date uploadTime;
	private String uploadPath;
	private String uploadFilename;
	//多对一：多个班级对应一个课程类别
	private CrmCourseType courseType;
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getUpgradeCount() {
		return upgradeCount;
	}
	public void setUpgradeCount(Integer upgradeCount) {
		this.upgradeCount = upgradeCount;
	}
	public Integer getChangeCount() {
		return changeCount;
	}
	public void setChangeCount(Integer changeCount) {
		this.changeCount = changeCount;
	}
	public Integer getRunoffCount() {
		return runoffCount;
	}
	public void setRunoffCount(Integer runoffCount) {
		this.runoffCount = runoffCount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getUploadFilename() {
		return uploadFilename;
	}
	public void setUploadFilename(String uploadFilename) {
		this.uploadFilename = uploadFilename;
	}
	public CrmCourseType getCourseType() {
		return courseType;
	}
	public void setCourseType(CrmCourseType courseType) {
		this.courseType = courseType;
	}
	

}
