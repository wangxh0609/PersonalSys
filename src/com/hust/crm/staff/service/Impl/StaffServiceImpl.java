package com.hust.crm.staff.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hust.crm.staff.dao.StaffDao;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.crm.staff.service.StaffService;
import com.hust.crm.utils.MyStringUtils;
import com.hust.newcore.service.impl.BaseServiceImpl;

@Service("staffService")
public class StaffServiceImpl extends BaseServiceImpl<CrmStaff> implements StaffService {
	private StaffDao staffDao;
	public StaffDao getStaffDao() {		
		return staffDao;
	}
	public void setStaffDao(StaffDao staffDao) {
		super.setBaseDao(staffDao);
		this.staffDao = staffDao;
	}
	@Override
	public CrmStaff login(CrmStaff staff) {
		// TODO Auto-generated method stub
		String loginPwd=MyStringUtils.getMD5Value(staff.getLoginPwd());
		return staffDao.find(staff.getLoginName(), loginPwd);
	}
	@Override
	public List<CrmStaff> findAllStaff() {
		// TODO Auto-generated method stub
		return staffDao.findAll();
	}
	
	@Override
	public CrmStaff findById(String staffId) {
		// TODO Auto-generated method stub
		return staffDao.findById(staffId);
	}
	@Override
	public boolean updatePassword(CrmStaff crmStaff) {
		if(crmStaff.isNeedEncry()){
			String loginPwd=MyStringUtils.getMD5Value(crmStaff.getLoginPwd());
			crmStaff.setLoginPwd(loginPwd);
		}
		return staffDao.updatePassword(crmStaff);
	}
	@Override
	public boolean addCrmStaff(CrmStaff crmStaff){
		//œ»≈–∂œ «∑Ò¥Ê‘⁄
		String loginPwd=MyStringUtils.getMD5Value(crmStaff.getLoginPwd());	
		crmStaff.setLoginPwd(loginPwd);
		return staffDao.addCrmStaff(crmStaff);
	}
	@Override
	public CrmStaff findByLoginName(String loginName) {
		
		return staffDao.findByLoginName(loginName);
	}
	
}
