package com.hust.crm.coursetype.domain;

import java.util.HashSet;
import java.util.Set;

import com.hust.crm.classes.domain.CrmClasses;

public class CrmCourseType {
	/*
	 * 
	 * CREATE TABLE `crm_course_type` (
  `courseTypeId` varchar(255) NOT NULL PRIMARY KEY,
  `courseCost` double DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `courseName` varchar(500) DEFAULT NULL,
  `remark` varchar(5000) DEFAULT NULL
);

	 */
	private String courseTypeId;
	private double courseCost;
	private Integer    total;
	private String courseName;
	public String getCourseTypeId() {
		return courseTypeId;
	}
	public void setCourseTypeId(String courseTypeId) {
		this.courseTypeId = courseTypeId;
	}
	public double getCourseCost() {
		return courseCost;
	}
	public void setCourseCost(double courseCost) {
		this.courseCost = courseCost;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Set<CrmClasses> getClassesSet() {
		return classesSet;
	}
	public void setClassesSet(Set<CrmClasses> classesSet) {
		this.classesSet = classesSet;
	}
	private String remark;
	//一对多：一个课程类别对应多个班级
	private Set<CrmClasses> classesSet=new HashSet<CrmClasses>();
	
}
