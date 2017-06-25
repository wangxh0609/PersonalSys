package com.hust.AccountSys.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hust.docMgr.blog.domain.ArticleBean;
import com.hust.toolsbean.DB;

public class ArticleDao {
	
	private ArticleBean articleBean = null;
	private Connection connection = null;
	private PreparedStatement state=null;
	public ArticleDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/wangxh_forever", "javawangxh", "wang20110351");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void connect(){
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/wangxh_forever", "javawangxh", "wang20110351");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @���� ʵ�ֶ����½�������ɾ���ĵĲ���
	 * @���� operΪһ��String���ͱ�����������ʾҪ���еĲ�����singleΪArticleBean����������洢ĳ�����µ���Ϣ
	 * @����ֵ boolean��ֵ
	 */
	public boolean operationArticle(String oper, ArticleBean single) {		
		/* ����SQL��� */
		String sql = null;
		if (oper.equals("add"))					//����������
			sql = "insert into tb_article(article_typeId,article_title,article_content,article_sdTime,article_create,article_info,article_count) values ('" + single.getTypeId() + "','"+ single.getTitle() + "','" + single.getContent() + "','"+ single.getSdTime()+ "','"+single.getCreate()+"','" + single.getInfo()+"',"+single.getCount() + ")";
		if (oper.equals("modify"))				//�޸�����
			sql = "update tb_article set article_typeID=" + single.getTypeId()+ ",article_title='" + single.getTitle() + "',article_content='"+ single.getContent() +"',article_create='"+single.getCreate()+ "',article_info='"+single.getInfo()+"' where id=" + single.getId();
		if (oper.equals("delete"))				//ɾ������
			sql = "delete from tb_article where id=" + single.getId();
		if (oper.equals("readTimes"))			//�ۼ��Ķ�����
			sql = "update tb_article set article_count=article_count+1 where id="+ single.getId();
		
		/* ִ��SQL��� */	
		int mark=0;
		//boolean flag =connection.executeUpdate(sql);
		try{
			state=connection.prepareStatement(sql);			
			mark = state.executeUpdate();
		}catch(Exception e){
			
		}				
		return mark>0?true:false;
	}

	/** 
	 * @���� ��ѯָ����������
	 * @���� typeId��ʾ�������IDֵ
	 * @����ֵ List����
	 */
	public List queryArticle(int typeId,String type) {
		List articlelist = new ArrayList();
		String sql = "";
		if (typeId <=0)	//select * from tablename order by orderfield desc/asc limit 0,15				//��������ѯ����ѯ��ǰ3����¼
			sql = "select * from tb_article order by id DESC limit 0,3" ;
		else							//������ѯ
			if(type==null||type.equals("")||!type.equals("all"))
				sql = "select * from tb_article where article_typeID=" + typeId+ " order by id DESC limit 0,5";
			else
				sql = "select * from tb_article where article_typeID=" + typeId+ " order by id DESC";
		
		ResultSet rs=null;
		try{
			PreparedStatement tempstate=connection.prepareStatement(sql);
			rs =tempstate.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}	
		if(rs!=null){
			try {
				while (rs.next()) {
					/* ��ȡ������Ϣ */
					articleBean = new ArticleBean.ArtiBeanBuilder().setId(rs.getInt(1))
																	.setTypeId(rs.getInt(2))
																	.setTitle(rs.getString(3))
																	.setContent(rs.getString(4))
																	.setSdTime(rs.getString(5))
																	.setCreate(rs.getString(6))
																	.setInfo(rs.getString(7))
																	.setCount(rs.getInt(9)).buildArticle();
					
//					articleBean = new ArticleBean();
//					articleBean.setId(rs.getInt(1));
//					articleBean.setTypeId(rs.getInt(2));
//					articleBean.setTitle(rs.getString(3));
//					articleBean.setContent(rs.getString(4));
//					articleBean.setSdTime(rs.getString(5));
//					articleBean.setCreate(rs.getString(6));
//					articleBean.setInfo(rs.getString(7));					
//					articleBean.setCount(rs.getInt(9));
					
					/* ��ѯtb_review���ݱ�ͳ�Ƶ�ǰ���µ������� */
					sql="select count(id) from tb_review where review_articleId="+articleBean.getId();
					try{
						state=connection.prepareStatement(sql);
						//state.set			
					}catch(Exception e){
						
					}	
					ResultSet rsr=state.executeQuery();
					if(rsr!=null){
						rsr.next();
						articleBean.setReview(rsr.getInt(1));
						rsr.close();						
					}					
					articlelist.add(articleBean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
		return articlelist;
	}

	/**
	 * @���� ��ѯָ�����µ���ϸ����
	 * @���� idΪ����IDֵ
	 * @����ֵ ArticleBean����󣬷�װ��������Ϣ
	 */
	public ArticleBean queryArticleSingle(int id) {
		String sql = "select * from tb_article where id='" + id + "'";
		ResultSet rs=null;
		try{
			state=connection.prepareStatement(sql);
			rs =state.executeQuery();// connection.executeQuery(sql);
			//state.set			
		}catch(Exception e){
			
		}	
		
		try {
			while (rs.next()) {
				articleBean = new ArticleBean.ArtiBeanBuilder()
						.setId(rs.getInt(1))
						.setTypeId(rs.getInt(2))
						.setTitle(rs.getString(3))
						.setContent(rs.getString(4))
						.setSdTime(rs.getString(5))
						.setCreate(rs.getString(6))
						.setInfo(rs.getString(7))
						.setCount(rs.getInt(9)).buildArticle();
				
				/* ��ѯtb_review���ݱ�ͳ�Ƶ�ǰ���µ������� */
				sql="select count(id) from tb_review where review_articleId="+articleBean.getId();
				try{
					state=connection.prepareStatement(sql);
					//state.set			
				}catch(Exception e){
					
				}	
				ResultSet rsr=state.executeQuery();//connection.executeQuery(sql);
				if(rsr!=null){
					rsr.next();
					articleBean.setReview(rsr.getInt(1));
					rsr.close();						
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articleBean;
	}
}
