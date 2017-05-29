package com.hust.crm.department.dao;

import java.util.List;

import com.hust.crm.department.domain.CrmDepartment;

public interface DepartmentDao {
	public List<CrmDepartment> findAll();

}
