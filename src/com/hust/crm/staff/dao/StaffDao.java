package com.hust.crm.staff.dao;

import java.util.List;

import com.hust.crm.staff.domain.CrmStaff;
import com.hust.newcore.dao.BaseDao;

public interface StaffDao extends BaseDao<CrmStaff>{
	/*
	 * 通过用户名和密码查询
	 * 
	 */
	public CrmStaff find(String loginName,String loginPwd);
	public List<CrmStaff> findAll();
	
	public CrmStaff findById(String staffId);
	
	public boolean updatePassword(CrmStaff crmStaff);
	public boolean addCrmStaff(CrmStaff crmStaff);
	public int findByLoginName(String loginName);
	
}
