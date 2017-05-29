package com.hust.docMgr.infodb.dao;

import java.io.Serializable;
import java.util.List;

import com.hust.docMgr.core.dao.BaseDao;
import com.hust.docMgr.infodb.domain.DocMarInfo;

public interface InfoDao<T> extends BaseDao<T> {
	public void deleteByEntity(T entity);
	
    public List<DocMarInfo> findByCon(DocMarInfo entity);
	
}
