package com.hust.crm.department.service.impl;

import java.util.List;

import com.hust.crm.department.dao.DepartmentDao;
import com.hust.crm.department.domain.CrmDepartment;
import com.hust.crm.department.service.DepartmentService;

public class DepartmentServiceImpl implements DepartmentService {

	
	private DepartmentDao departmentDao;
	
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	@Override
	public List<CrmDepartment> findAll() {
		// TODO Auto-generated method stub
		return departmentDao.findAll();
	}

}
