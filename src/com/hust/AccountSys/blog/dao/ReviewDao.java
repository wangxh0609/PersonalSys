package com.hust.AccountSys.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hust.docMgr.blog.domain.ReviewBean;
import com.hust.toolsbean.DB;

public class ReviewDao {
	private Connection connection = null;
	private PreparedStatement state=null;

	public ReviewDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/wangxh_forever", "javawangxh", "wang20110351");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean operationReview(String operation, ReviewBean single) {
		boolean flag = false;
		String sql = "";
		if (operation.equals("add"))
			sql = "insert into tb_review(review_articleId,reAuthor,reContent,reSdTime,flag,toName,parentId) values (?,?,?,?,?,?,?)";
		if (operation.equals("É¾³ý"))
			sql = "delete from tb_review where id='" + single.getId() + "'";
		if (operation.equals("update"))
			sql = "update tb_review set flag=0 where id='" + single.getId() + "'";
		int mark=0;
		//boolean flag =connection.executeUpdate(sql);
		try{
			state=connection.prepareStatement(sql);	
			state.setInt(1, single.getArticleId());
			state.setString(2,single.getReAuthor());
			state.setString(3,single.getReContent());
			state.setString(4,single.getReSdTime());
			state.setInt(5, single.getFlag());
			state.setString(6,single.getToName());
			state.setInt(7, single.getParentId());
			
			
			mark = state.executeUpdate();
		}catch(Exception e){
			
		}				
		return mark>0?true:false;
	}

	public List queryReview(int articleId) {
		List list = new ArrayList();
		String sql = "select * from tb_review where review_articleId=" + articleId + " order by reSdTime DESC";
		ReviewBean reviewBean = null;
		ResultSet rs=null;
		try{
			state=connection.prepareStatement(sql);
			rs =state.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			
		}	
		try {
			while (rs.next()) {
				reviewBean = new ReviewBean();
				reviewBean.setId(rs.getInt(1));
				reviewBean.setArticleId(rs.getInt(2));
				reviewBean.setReAuthor(rs.getString(3));
				reviewBean.setReContent(rs.getString(4));
				reviewBean.setReSdTime(rs.getString(5));
				reviewBean.setFlag(rs.getInt(6));
				reviewBean.setToName(rs.getString(7));
				reviewBean.setParentId(rs.getInt(8));
				list.add(reviewBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
