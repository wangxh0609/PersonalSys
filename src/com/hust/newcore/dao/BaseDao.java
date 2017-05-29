package com.hust.newcore.dao;

import java.io.Serializable;
import java.util.List;

import com.hust.core.page.PageResult;
import com.hust.core.util.QueryHelper;


public interface BaseDao<T> {
	
	public void save(T entity);

	public void update(T entity);

	public void delete(Serializable id);

	public T findObjectById(Serializable id);

	public List<T> findObjects();

	public List<T> findObjects(String hql, List<Object> parameters);

	public List<T> findObjects(QueryHelper queryHelper);

	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize);

}
