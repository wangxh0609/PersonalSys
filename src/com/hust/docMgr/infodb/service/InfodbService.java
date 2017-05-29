package com.hust.docMgr.infodb.service;

import java.io.Serializable;
import java.util.List;

import com.hust.docMgr.infodb.domain.DocMarInfo;
public interface InfodbService {
	public void deleteByEntity(DocMarInfo entity);
	public DocMarInfo find(DocMarInfo entity);
	public List<DocMarInfo> findAll();	
	public DocMarInfo findById(Serializable id);
	public void update(DocMarInfo entity);
	public void addInfo(DocMarInfo entity);
	public void deleteById(String id);
	public List<DocMarInfo> findByCon(DocMarInfo entity);
	public void deleteBatch(String idStr);
}
