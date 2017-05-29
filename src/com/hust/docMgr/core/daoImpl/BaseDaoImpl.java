package com.hust.docMgr.core.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hust.crm.staff.domain.CrmStaff;
import com.hust.docMgr.core.dao.BaseDao;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	@Override
	public T find(T entity) {
		
		return null;
	}

	@Override
	public List<T> findAll() {
		return null;		
	}

	@Override
	public T findById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBatch(String idStr) {
		// TODO Auto-generated method stub
		
	}

}
