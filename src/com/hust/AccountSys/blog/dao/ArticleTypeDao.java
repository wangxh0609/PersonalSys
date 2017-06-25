package com.hust.AccountSys.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hust.docMgr.blog.domain.ArticleTypeBean;
import com.hust.toolsbean.DB;

public class ArticleTypeDao {
	private Connection connection = null;
	private PreparedStatement state=null;

	public ArticleTypeDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/wangxh_forever", "javawangxh", "wang20110351");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean operationArticleType(String operation, ArticleTypeBean single) {		
		String sql = null;
		if (operation.equals("add"))
			sql = "insert into tb_articleType(articleType_typeName,articleType_typeInfo) values ('" + single.getTypeName()+ "','" + single.getTypeInfo() + "')";
		if (operation.equals("modify"))
			sql="update tb_articleType set articleType_typeName='"+single.getTypeName()+"',articleType_typeInfo='"+single.getTypeInfo()+"' where articleType_id="+single.getId();
		if (operation.equals("delete"))
			sql = "delete from tb_articleType where articleType_id=" + single.getId() ;
		int mark=0;
		//boolean flag =connection.executeUpdate(sql);
		try{
			state=connection.prepareStatement(sql);			
			mark = state.executeUpdate();
		}catch(Exception e){
			
		}				
		return mark>0?true:false;
	}
	
	public ArticleTypeBean queryTypeSingle(int id) {
		ArticleTypeBean typeBean=null;
		String sql = "select * from tb_articleType where articleType_id=" + id;
		ResultSet rs=null;
		try{
			state=connection.prepareStatement(sql);
			rs =state.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			
		}	
		if(rs!=null){
			try {
				if(rs.next()){
					typeBean=new ArticleTypeBean();
					typeBean.setId(rs.getInt(1));
					typeBean.setTypeName(rs.getString(2));
					typeBean.setTypeInfo(rs.getString(3));
				}
			} catch (SQLException e) {
				typeBean=null;
				e.printStackTrace();
			}			
		}
		return typeBean;
	}

	public List queryTypeList() {
		List typelist = null;		
		String sql = "select * from tb_articleType";
		ResultSet rs=null;
		try{
			state=connection.prepareStatement(sql);
			rs =state.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			
		}	
		if(rs!=null){
			typelist=new ArrayList();
			try {
				while(rs.next()){
					ArticleTypeBean typeBean=new ArticleTypeBean();
					typeBean.setId(rs.getInt(1));
					typeBean.setTypeName(rs.getString(2));
					typeBean.setTypeInfo(rs.getString(3));
					typelist.add(typeBean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		return typelist;
	}

}
