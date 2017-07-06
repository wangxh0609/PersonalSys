package com.hust.AccountSys.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hust.core.util.MyPoolManager;
import com.hust.docMgr.blog.domain.FriendBean;
import com.hust.toolsbean.DB;

public class FriendDao {
	private Connection connection = null;
	private PreparedStatement state=null;
	public FriendDao() {
		
	}

	// 修改朋友
	public boolean operationFriend(String operation,FriendBean single) {
		String sql="";
		if(operation==null)
			operation="";		
		if(operation.equals("add"))
			sql="insert into tb_friend(friend_name,friend_sex,friend_oicq,friend_blog) values('"+single.getName()+"','"+single.getSex()+"','"+single.getOicq()+"','"+single.getBlog()+"')";
		if(operation.equals("modify"))
			sql="update tb_friend set friend_name='"+single.getName()+"',friend_sex='"+single.getSex()+"',friend_OICQ='"+single.getOicq()+"',friend_blog='"+single.getBlog()+"' where id="+single.getId();
		if(operation.equals("delete"))
			sql="delete from tb_friend where id="+single.getId();
		
		int mark=0;
		
		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);			
			mark = state.executeUpdate();
		}catch(Exception e){
			
		}finally{
			try {
				MyPoolManager.GetPoolInstance().releaseConn(connection);
				state.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mark>0?true:false;
	}

	/**
	 * @功能 查询所有好友
	 */
	public List queryFriend(String type) {
		String sql="";
		if(type==null||type.equals("")||!type.equals("all"))
			sql="select * from tb_friend order by friend_name DESC limit 0,5";
		else
			sql="select * from tb_friend order by friend_name DESC";
		List list = new ArrayList();
		FriendBean friendBean = null;
		ResultSet rs=null;
		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);
			rs =state.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			
		}	
		try {
			while (rs.next()) {
				friendBean = new FriendBean();
				friendBean.setId(rs.getInt(1));
				friendBean.setName(rs.getString(2));
				friendBean.setSex(rs.getString(3));
				friendBean.setOicq(rs.getString(4));
				friendBean.setBlog(rs.getString(5));
				list.add(friendBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				MyPoolManager.GetPoolInstance().releaseConn(connection);
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * @throws SQLException 
	 * @功能 查询某个好友的详细信息
	 */
	public FriendBean queryFriendSingle(int id){
		FriendBean friendBean = null;
		String sql = "select * from tb_friend where id=" + id;
		ResultSet rs=null;
		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);
			rs =state.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			
		}	
		try {
			while (rs.next()) {
				friendBean = new FriendBean();
				friendBean.setId(Integer.valueOf(rs.getString(1)));
				friendBean.setName(rs.getString(2));
				friendBean.setSex(rs.getString(3));
				friendBean.setOicq(rs.getString(4));
				friendBean.setBlog(rs.getString(5));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				MyPoolManager.GetPoolInstance().releaseConn(connection);				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return friendBean;
	}
	

}
