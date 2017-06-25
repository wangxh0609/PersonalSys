package com.hust.AccountSys.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hust.docMgr.blog.domain.MasterBean;
import com.hust.toolsbean.DB;

// π”√crm_staff

public class LogonDao {
	private Connection connection = null;
	private PreparedStatement state=null;

	public LogonDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/wangxh_forever", "javawangxh", "wang20110351");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MasterBean getMaster(){
		MasterBean master=null;
		String sql="select * from tb_master";
		ResultSet rs=null;
		try{
			state=connection.prepareStatement(sql);
			rs =state.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			
		}	
		try{
			if(rs!=null&&rs.next()){
				master=new MasterBean();	
				master.setMasterName(rs.getString(1));
				master.setMasterSex(rs.getString(3));
				master.setMasterOicq(rs.getString(4));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return master;
	}
	
	public boolean logon(MasterBean logoner){
		boolean mark=false;
		if(logoner!=null){
			String sql="select * from tb_master where master_name='"+logoner.getMasterName()+"' and master_password='"+logoner.getMasterPass()+"'";
			ResultSet rs=null;
			try{
				state=connection.prepareStatement(sql);
				rs =state.executeQuery();// connection.executeQuery(sql);
				//state.set			
			}catch(Exception e){
				
			}	
			try {
				if(rs!=null&&rs.next())
					mark=true;
				else
					mark=false;
			} catch (SQLException e) {
				mark=false;
				e.printStackTrace();
			}
			try {
				rs.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return mark;		
	}
}
