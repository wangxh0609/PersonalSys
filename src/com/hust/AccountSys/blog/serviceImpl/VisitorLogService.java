package com.hust.AccountSys.blog.serviceImpl;

import com.hust.AccountSys.blog.dao.VisitorLogDao;
import com.hust.docMgr.blog.domain.VisitorLogBean;
import com.hust.newcore.service.impl.BaseServiceImpl;

public class VisitorLogService extends BaseServiceImpl<VisitorLogBean> {
	private VisitorLogDao visitorLogDao=new VisitorLogDao();
	public boolean operationArticle(String oper, VisitorLogBean visitor) {
		return this.visitorLogDao.operationArticle(oper, visitor);
	}

}
