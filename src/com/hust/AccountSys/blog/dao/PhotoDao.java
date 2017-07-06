package com.hust.AccountSys.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hust.core.util.MyPoolManager;
import com.hust.docMgr.blog.domain.PhotoBean;

public class PhotoDao {
	private Connection connection = null;
	private PreparedStatement state=null;
	public PhotoDao() {		
	}
	
	public boolean operationPhoto(String operation, PhotoBean single) {
		String sql = "";
		if (operation.equals("delete"))
			sql = "delete from tb_photo where id=" + single.getId();
		if (operation.equals("upload"))
			sql = "insert into tb_photo(photoAddr,photoTime,photoInfo,photoCreator) values ('"+ single.getPhotoAddr() + "','"+ single.getPhotoTime() + "','"+ single.getPhotoInfo() +"','"+single.getPhotoCreator()+ "')";
		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);				
		}catch(Exception e){
			
		}	
		int mark=0;
		try {
			mark = state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//connection.executeUpdate(sql);
		finally{
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

	public PhotoBean queryPhoto(int id) {
		PhotoBean photoBean = null;
		String sql = "select * from tb_photo where id=" + id;
		ResultSet rs=null;
		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);
			rs =state.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			
		}	
		try {
			if(rs.next()) {
				photoBean = new PhotoBean();
				photoBean.setId(rs.getInt(1));
				photoBean.setPhotoAddr(rs.getString(2));
				photoBean.setPhotoInfo(rs.getString(3));
				photoBean.setPhotoTime(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				MyPoolManager.GetPoolInstance().releaseConn(connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return photoBean;
	}

	public List queryPhoto(String type) {
		if(type==null||type.equals(""))
			type="sub";		
		String sql = "";
		if(type.equals("all"))
			sql="select * from tb_photo order by photoTime DESC";
		else
			sql="select * from tb_photo order by photoTime DESC limit 0,8";
		
		List list = null;
		PhotoBean photoBean = null;
		ResultSet rs=null;
		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);
			rs =state.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			
		}	
		if(rs!=null){
			list=new ArrayList();
			try {
				while (rs.next()) {					
					photoBean = new PhotoBean();
					photoBean.setId(rs.getInt(1));
					photoBean.setPhotoAddr(rs.getString(2));
					photoBean.setPhotoTime(rs.getString(3));
					photoBean.setPhotoInfo(rs.getString(4));
					list.add(photoBean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					rs.close();
					MyPoolManager.GetPoolInstance().releaseConn(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
		return list;
	}

	public int queryMaxId(){
		int maxId = 0;
		String sql = "select max(id) from tb_photo";
		ResultSet rs=null;
		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);
			rs =state.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			
		}	
		if(rs!=null){
			try {
				if(rs.next())
					maxId = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					rs.close();
					MyPoolManager.GetPoolInstance().releaseConn(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return maxId;
	}
}
