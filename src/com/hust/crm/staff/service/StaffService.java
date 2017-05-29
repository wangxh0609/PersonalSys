package com.hust.crm.staff.service;

import java.util.List;

import com.hust.crm.staff.domain.CrmStaff;
import com.hust.newcore.service.BaseService;

public interface StaffService extends BaseService<CrmStaff> {
	public CrmStaff login(CrmStaff staff);
	public List<CrmStaff> findAllStaff();
	public CrmStaff findById(String staffId);
	public boolean updatePassword(CrmStaff crmStaff);
	public boolean addCrmStaff(CrmStaff crmStaff);
	public int findByLoginName(String loginName);
}
