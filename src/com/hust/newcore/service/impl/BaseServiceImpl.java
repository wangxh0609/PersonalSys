package com.hust.newcore.service.impl;

import java.io.Serializable;
import java.util.List;

import com.hust.core.page.PageResult;
import com.hust.core.util.QueryHelper;
import com.hust.docMgr.core.daoImpl.BaseDaoImpl;
import com.hust.newcore.dao.BaseDao;
import com.hust.newcore.service.BaseService;


public class BaseServiceImpl<T> implements BaseService<T> {
	
	private BaseDao<T> baseDao;
		
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void save(T entity) {
		baseDao.save(entity);
	}

	@Override
	public void update(T entity) {
		baseDao.update(entity);
	}

	@Override
	public void delete(Serializable id) {
		baseDao.delete(id);
	}

	@Override
	public T findObjectById(Serializable id) {
		return baseDao.findObjectById(id);
	}

	@Override
	public List<T> findObjects() {
		return baseDao.findObjects();
	}

	@Override
	public List<T> findObjects(String hql, List<Object> parameters) {
		return baseDao.findObjects(hql, parameters);
	}

	@Override
	public List<T> findObjects(QueryHelper queryHelper) {
		return baseDao.findObjects(queryHelper);
	}

	@Override
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize) {
		//System.out.println("456");
		//System.out.println(baseDao==null);
		return baseDao.getPageResult(queryHelper, pageNo, pageSize);
	}

}
