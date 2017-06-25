package com.hust.AccountSys.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.hust.docMgr.blog.domain.VisitorLogBean;



public class VisitorLogDao {
	private VisitorLogBean visitorLogBean = null;
	private Connection connection = null;
	private PreparedStatement state=null;
	public VisitorLogDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/wangxh_forever", "javawangxh", "wang20110351");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void connect(){
		try {			
			connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/wangxh_forever", "javawangxh", "wang20110351");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			state=connection.prepareStatement(sql);			
			mark = state.executeUpdate();
		}catch(Exception e){
			
		}				
		return mark>0?true:false;
	}
}
