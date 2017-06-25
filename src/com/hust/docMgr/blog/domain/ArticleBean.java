package com.hust.docMgr.blog.domain;

import java.io.Serializable;

public class ArticleBean implements Serializable{
	private int id=-1;
	private int typeId=-1;
	private String title="";
	private String content="";
	private String sdTime="";
	private String create="";//or
	private String info="";
	private int review=0;
	private int count=0;	
	
	public String getCreate() {
		return create;
	}
	public void setCreate(String create) {
		this.create = create;
	}
	public String getContent() {
		return content;
	}
	public String getContent(int len){
		if(len<=0||len>content.length())
			len=content.length();
		return content.substring(0,len)+"...";
	}
	public int getReview() {
		return review;
	}
	public void setReview(int review) {
		this.review = review;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getSdTime() {
		return sdTime;
	}
	public void setSdTime(String sdTime) {
		this.sdTime = sdTime;
	}
	public String getTitle() {
		return title;
	}
	public String getTitle(int len){
		if(len<=0||len>title.length())
			len=title.length();
		return title.substring(0,len)+"...";
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}	
	public ArticleBean(){
		
	}
	public ArticleBean(ArtiBeanBuilder builder){
		this.id=builder.id;
		this.typeId=builder.typeId;
		this.title=builder.title;
		this.content=builder.content;
		this.sdTime=builder.sdTime;
		this.create=builder.create;
		this.info=builder.info;
		this.review=builder.review;
		this.count=builder.count;
	}
	public static class ArtiBeanBuilder{
		private int id=-1;
		private int typeId=-1;
		private String title="";
		private String content="";
		private String sdTime="";
		private String create="";
		private String info="";
		private int review=0;
		private int count=0;
	
		public ArtiBeanBuilder setId(int id) {
			this.id = id;
			return this;
		}
		
		public ArtiBeanBuilder setTypeId(int typeId) {
			this.typeId = typeId;
			return this;
		}
		
		public ArtiBeanBuilder setTitle(String title) {
			this.title = title;
			return this;
		}
		
		public ArtiBeanBuilder setContent(String content) {
			this.content = content;
			return this;
		}
		public ArtiBeanBuilder setSdTime(String sdTime) {
			this.sdTime = sdTime;
			return this;
		}
		
		public ArtiBeanBuilder setCreate(String create) {
			this.create = create;
			return this;
		}
	
		public ArtiBeanBuilder setInfo(String info) {
			this.info = info;
			return this;
		}
		
		public ArtiBeanBuilder setReview(int review) {
			this.review = review;
			return this;
		}
		
		public ArtiBeanBuilder setCount(int count) {
			this.count = count;
			return this;
		}
		
		public ArticleBean buildArticle(){
			return new ArticleBean(this);
		}
		
		
	}
}
