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
import com.hust.docMgr.blog.domain.WordBean;
import com.hust.toolsbean.DB;

public class WordDao {
	private Connection connection = null;
	private PreparedStatement state=null;

	public WordDao() {		
	}
	
	
	public boolean operationWord(String oper, WordBean single) {		
		/* 生成SQL语句 */
		String sql = null;
		if (oper.equals("add"))					//发表留言
			sql = "insert into tb_word(wordTitle,wordContent,wordTime,wordAuthor,parentId,toName,flag,bozName,firstParentId) values(?,?,?,?,?,?,?,?,?)";		
		if (oper.equals("delete"))				//删除留言
			return deleteWord(single);//sql = "delete from tb_word where id=" + single.getId();
		if (oper.equals("update"))				//设置留言已被查看 查看时查看所有所以只要是查看就更新全部为查看的状态
			sql = "update tb_word set flag=0 where flag=-1";// + single.getId()

		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);
			state.setString(1, single.getWordTitle());
			state.setString(2,single.getWordContent());
			state.setString(3,single.getWordTime());
			state.setString(4,single.getWordAuthor());
			state.setInt(5, single.getParentId());
			state.setString(6,single.getToName());
			state.setInt(7, single.getFlag());
			state.setString(8,single.getBozName());
			state.setInt(9, single.getFirstParentId());
			//rs =state.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			
		}	
		/* 执行SQL语句 */	
		int flag=0;
		try {
			flag = state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				state.close();
				MyPoolManager.GetPoolInstance().releaseConn(connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag>0?true:false;
	}
	
	public  boolean deleteWord(WordBean single){
		String sql = null;
	    if(single.getParentId()==-1){
	    	//需要级联删除
	    	sql = "delete from tb_word where id=" + single.getId()+" or firstParentId="+ single.getId();
	    }else{
	    	sql = "delete from tb_word where id=" + single.getId();
	    }
		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);				
		}catch(Exception e){
			
		}	
		/* 执行SQL语句 */	
		int flag=0;
		try {
			flag = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				state.close();
				MyPoolManager.GetPoolInstance().releaseConn(connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return flag>0?true:false;
	}
	public List queryWord(String type){
		String sql="";
		if(type==null||type.equals(""))
			type="sub";
		if(type.equals("all"))
			sql="select * from tb_word where parentId=-1 order by wordTime DESC";
		else if(type.equals("adminAll"))
			sql="select * from tb_word order by wordTime DESC";
		else
			sql="select * from tb_word where parentId=-1 order by wordTime DESC limit 0,5";
		List wordlist = null;
		WordBean wordBean = null;
		ResultSet rs=null;
		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);
			rs =state.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			
		}	
		if(rs!=null){
			wordlist=new ArrayList();
			try {
				while (rs.next()) {
					wordBean = new WordBean();
					wordBean.setId(rs.getInt(1));
					wordBean.setWordTitle(rs.getString(2));
					wordBean.setWordContent(rs.getString(3));
					wordBean.setWordTime(rs.getString(4));
					wordBean.setWordAuthor(rs.getString(5));
					wordBean.setParentId(rs.getInt(6));
					wordBean.setToName(rs.getString(7));
					wordBean.setFlag(rs.getInt(8));
					wordBean.setBozName(rs.getString(9));
					wordBean.setFirstParentId(rs.getInt(10));
					wordlist.add(wordBean);
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
		return wordlist;
	}
	
	public WordBean getWordBeanByid(int wordId){
		String sql="";		
		sql="select * from tb_word where id=?";
		WordBean wordBean = null;
		ResultSet rs=null;
		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);
			state.setInt(1,wordId);
			rs =state.executeQuery();			
			if(rs!=null){
				try {
					while (rs.next()) {
						wordBean = new WordBean();
						wordBean.setId(rs.getInt(1));
						wordBean.setWordTitle(rs.getString(2));
						wordBean.setWordContent(rs.getString(3));
						wordBean.setWordTime(rs.getString(4));
						wordBean.setWordAuthor(rs.getString(5));
						wordBean.setParentId(rs.getInt(6));
						wordBean.setToName(rs.getString(7));
						wordBean.setFlag(rs.getInt(8));	
						wordBean.setBozName(rs.getString(9));
						wordBean.setFirstParentId(rs.getInt(10));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}		
			}
		}catch(Exception e){
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
		return wordBean;
	}
	
	List<WordBean> wordlistAll = null;
	public List<WordBean> getAllByParenWordId(int wordId){
		getWrodListRecursion(wordId);		
		return wordlistAll;
	}
	
	private void getWrodListRecursion(int wordId){
		String sql="";		
		//sql="select * from tb_word where parentId=?";
		sql="select * from tb_word where firstParentId=? order by id asc";
		WordBean wordBean = null;
		ResultSet rs=null;
		try{
			connection=MyPoolManager.GetPoolInstance().getCurrentConnecton();
			state=connection.prepareStatement(sql);
			state.setInt(1, wordId);
			rs =state.executeQuery();
			if(wordlistAll==null){
				wordlistAll=new ArrayList();
			}
			if(rs!=null){
				try {
					while (rs.next()) {
						wordBean = new WordBean();
						wordBean.setId(rs.getInt(1));
						wordBean.setWordTitle(rs.getString(2));
						wordBean.setWordContent(rs.getString(3));
						wordBean.setWordTime(rs.getString(4));
						wordBean.setWordAuthor(rs.getString(5));
						wordBean.setParentId(rs.getInt(6));
						wordBean.setToName(rs.getString(7));
						wordBean.setFlag(rs.getInt(8));
						wordBean.setBozName(rs.getString(9));
						wordBean.setFirstParentId(rs.getInt(10));
						wordlistAll.add(wordBean);
						//getWrodListRecursion(wordBean.getId());
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
		}catch(Exception e){
			
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
	
}
