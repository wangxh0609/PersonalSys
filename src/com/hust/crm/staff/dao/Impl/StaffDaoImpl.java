package com.hust.crm.staff.dao.Impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hust.crm.staff.dao.StaffDao;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.newcore.dao.impl.BaseDaoImpl;


public class StaffDaoImpl extends BaseDaoImpl<CrmStaff> implements StaffDao {

	@Override
	public CrmStaff find(String loginName, String loginPwd) {
		List<CrmStaff> allStaff=this.getHibernateTemplate().find("from CrmStaff where loginName=? and loginPwd=?", loginName,loginPwd);
		if(allStaff.size()==1){
			return allStaff.get(0);
		}
		return null;
	}

	@Override
	public List<CrmStaff> findAll() {
		// TODO Auto-generated method stub
		List<CrmStaff> allStaff=this.getHibernateTemplate().find("from CrmStaff where 1=1");
		return allStaff;		
	}

	@Override
	public CrmStaff findById(String staffId) {
		// TODO Auto-generated method stub
		
		return this.getHibernateTemplate().get(CrmStaff.class, staffId);	
	}

	@Override
	public boolean updatePassword(CrmStaff crmStaff) {
		// TODO Auto-generated method stub
		try{
			this.getHibernateTemplate().update(crmStaff);
		}
		catch(Exception e){
			return false;
		}
		return true;
	} 
	
	public boolean addCrmStaff(CrmStaff crmStaff){
		boolean isInserted=false;
		try{			
			this.getHibernateTemplate().save(crmStaff);	
			isInserted=true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}		
		return isInserted;
	}

	@Override
	public CrmStaff findByLoginName(String loginName) {
		// TODO Auto-generated method stub
		
		List<CrmStaff> list=this.getHibernateTemplate().find("from CrmStaff where loginName=?",loginName);
		if(list==null||list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}

}
