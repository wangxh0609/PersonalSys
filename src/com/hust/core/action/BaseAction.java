package com.hust.core.action;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;

import com.hust.core.page.PageResult;
import com.hust.core.util.ReadConfigUpload;
import com.hust.crm.staff.domain.CrmStaff;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport {
    protected static String uploadPath=null;
    static{
    	try{
    		InputStream in = new BufferedInputStream(
					new FileInputStream(ServletActionContext.getServletContext().getRealPath("/")+"/WEB-INF/classes/ftpConfig.properties"));					
			Properties prop = new Properties();
			prop.load(in);							
			uploadPath = prop.getProperty("uploadFile");
			in.close();
    	}catch(Exception e){
    		
    	}
    }
	
	protected String[] selectedRow;
	protected PageResult pageResult;
	private int pageNo;
	private int pageSize;
	public static int DEFAULT_PAGE_SIZE = 10;
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	public PageResult getPageResult() {
		return pageResult;
	}
	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		if(pageSize < 1) pageSize = DEFAULT_PAGE_SIZE;
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
