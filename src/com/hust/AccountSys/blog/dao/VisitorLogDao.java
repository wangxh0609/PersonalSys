package com.hust.AccountSys.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hust.core.util.MyPoolManager;
import com.hust.docMgr.blog.domain.VisitorLogBean;



public class VisitorLogDao {
	private VisitorLogBean visitorLogBean = null;
	private Connection connection = null;
	private PreparedStatement state=null;
	public VisitorLogDao() {		
		
	}
		
	
	//insert into tb_visitorlog(id,vistor_name,visitor_time,visitor_info,visitor_ip,revisitor_name)
	
	public boolean operationArticle(String oper, VisitorLogBean visitor) {		
		/* 生成SQL语句 */
		String sql = null;
		if (oper.equals("add"))					//添加访问日志
			sql = "insert into tb_visitorlog(vistor_name,visitor_time,visitor_info,visitor_ip,revisitor_name) values ('" +visitor.getVisitorName()+ "','"+visitor.getVisitorTime()+"','"+ visitor.getVisitorInfo() + "','" + visitor.getVisitorIp() + "','"+ visitor.getReVisitorName()+ "')";
		
		if (oper.equals("delete"))				//删除文章
			sql = "delete from tb_visitorlog where id=" + visitor.getId();
	
		/* 执行SQL语句 */	
		int mark=0;
		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);			
			mark = state.executeUpdate();
		}catch(Exception e){
			
		}finally{
			try {
				state.close();
				MyPoolManager.GetPoolInstance().releaseConn(connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mark>0?true:false;
	}
}
