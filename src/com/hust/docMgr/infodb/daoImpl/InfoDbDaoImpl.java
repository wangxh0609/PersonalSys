package com.hust.docMgr.infodb.daoImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.hust.crm.staff.domain.CrmStaff;
import com.hust.docMgr.core.daoImpl.BaseDaoImpl;
import com.hust.docMgr.infodb.dao.InfoDao;
import com.hust.docMgr.infodb.domain.DocMarInfo;

public class InfoDbDaoImpl extends BaseDaoImpl<DocMarInfo> implements InfoDao<DocMarInfo> {
	
	@Override
	public DocMarInfo find(DocMarInfo entity) {
		
		List<DocMarInfo> allList=this.getHibernateTemplate().find("from DocMarInfo where accountNum=?",entity.getAccountNum());
		if(allList.size()==1){
			return allList.get(0);
		}
		return null;
	}

	@Override
	public List<DocMarInfo> findAll() {
		// TODO Auto-generated method stub
		List<DocMarInfo> allList=null;
		try{
			allList=this.getHibernateTemplate().find("from DocMarInfo where 1=1");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return allList;
	}

	@Override
	public DocMarInfo findById(Serializable id) {
		List<DocMarInfo> allList=this.getHibernateTemplate().find("from DocMarInfo where infoId=?",id);
		if(allList.size()==1){
			return allList.get(0);
		}
		return null;
	}

	@Override
	public void update(DocMarInfo entity) {
		try{
			this.getHibernateTemplate().update(entity);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}		

	}

	@Override
	public void save(DocMarInfo entity) {
		try{			
			this.getHibernateTemplate().save(entity);				
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}		

	}

	@Override
	public void deleteById(String id) {
		try{
			System.out.println("哈哈"+id);
			System.out.println(id);
			DocMarInfo entity=this.findById(id);
			this.getHibernateTemplate().delete(entity);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void deleteByEntity(DocMarInfo entity) {
		try{			
			this.getHibernateTemplate().delete(entity);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public List<DocMarInfo> findByCon(DocMarInfo entity) {
		List<DocMarInfo> allList=null;
		try{
			if(entity.getAccountNum()!=null&&!entity.getAccountNum().equals("")){
			    System.out.println(entity.getAccountNum());
				allList=this.getHibernateTemplate().find("from DocMarInfo where creator=? and accountNum like ?",entity.getCreator(),"%"+entity.getAccountNum()+"%");
			}else{
				//查找本人的全部
				allList=this.getHibernateTemplate().find("from DocMarInfo where creator=?",entity.getCreator());
			}
		}catch(Exception e){			
			System.out.println(e.getMessage());
		}
		return allList;
	}
	
	@Override
	public void deleteBatch(String idStr){
		try{			
			SessionFactory sf=this.getHibernateTemplate().getSessionFactory();
			Session session=sf.openSession();
			session.beginTransaction();		
			Query query=session.createQuery("delete from DocMarInfo where infoId in ("+idStr+")");
			int count=query.executeUpdate();			
			session.getTransaction().commit();
			session.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
