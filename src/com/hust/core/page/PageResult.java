package com.hust.core.page;

import java.util.ArrayList;
import java.util.List;

public class PageResult {

	//总数量
	private long totalCount;
	//第几页
	private int pageNo;
	//总页数
	private int totalPageCount;
	//一页大小
	private int pageSize;
	//所有的项
	private List items;
	
	//计算相关
	public PageResult(long totalCount, int pageNo, int pageSize, List items) {
		this.items = items==null?new ArrayList():items;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		if(totalCount != 0){
			//得到总页数
			int tem = (int)totalCount/pageSize;
			this.totalPageCount = (totalCount%pageSize==0)?tem:(tem+1);
			this.pageNo = pageNo;
		} else {
			this.pageNo = 0;
		}
	}
	
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}
	
}
