package com.hust.docMgr.core.dao;
import java.io.Serializable;
import java.util.List;

public interface BaseDao<T>{
	public T find(T entity);
	public List<T> findAll();	
	public T findById(Serializable id);
	public void update(T entity);
	public void save(T entity);
	public void deleteById(String id);
	public void deleteBatch(String idStr);
}
