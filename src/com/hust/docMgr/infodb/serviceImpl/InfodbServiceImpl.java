package com.hust.docMgr.infodb.serviceImpl;

import java.io.Serializable;
import java.util.List;

import com.hust.docMgr.infodb.dao.InfoDao;
import com.hust.docMgr.infodb.domain.DocMarInfo;
import com.hust.docMgr.infodb.service.InfodbService;

public class InfodbServiceImpl implements InfodbService {

	private InfoDao<DocMarInfo>  infoDbDao;
	public InfoDao<DocMarInfo> getInfoDbDao() {
		return infoDbDao;
	}
	public void setInfoDbDao(InfoDao<DocMarInfo> infoDbDao) {
		this.infoDbDao = infoDbDao;
	}

	@Override
	public void deleteByEntity(DocMarInfo entity) {
		
		this.infoDbDao.deleteByEntity(entity);
	}

	@Override
	public DocMarInfo find(DocMarInfo entity) {
		return this.infoDbDao.find(entity);
	}

	@Override
	public List<DocMarInfo> findAll() {
		// TODO Auto-generated method stub
		return this.infoDbDao.findAll();
	}

	@Override
	public DocMarInfo findById(Serializable id) {
		return this.infoDbDao.findById(id);
	}

	@Override
	public void update(DocMarInfo entity) {
		this.infoDbDao.update(entity);

	}

	@Override
	public void addInfo(DocMarInfo entity) {
		this.infoDbDao.save(entity);

	}

	@Override
	public void deleteById(String id) {
		this.infoDbDao.deleteById(id);
	}
	@Override
	public List<DocMarInfo> findByCon(DocMarInfo entity) {
		// TODO Auto-generated method stub
		return this.infoDbDao.findByCon(entity);
	}
	
	public void deleteBatch(String idStr){		
		this.infoDbDao.deleteBatch(idStr);
	}

}
