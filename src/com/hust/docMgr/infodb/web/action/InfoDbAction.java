package com.hust.docMgr.infodb.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.hust.core.action.BaseAction;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.docMgr.infodb.domain.DocMarInfo;
import com.hust.docMgr.infodb.service.InfodbService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class InfoDbAction extends BaseAction implements ModelDriven<DocMarInfo>{

	InfodbService infodbService;
	DocMarInfo docMarInfo=new DocMarInfo();

	@Override
	public DocMarInfo getModel() {
		// TODO Auto-generated method stub
		return docMarInfo;
	}

	public InfodbService getInfodbService() {
		return infodbService;
	}

	public void setInfodbService(InfodbService infodbService) {
		this.infodbService = infodbService;
	}

	public String frame() {
		return "frame";
	}

	public String top() {
		return "top";
	}

	public String left() {
		return "left";
	}
	
	public String addUI(){		
		System.out.println("进入"+docMarInfo);		
		Date current=new Date();
		docMarInfo.setCreateTime(current);
		
		this.infodbService.addInfo(docMarInfo);
		return "addUI";
	}
	public void ViewPwd(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id=request.getParameter("infoId");
		DocMarInfo mydocMarInfo=this.infodbService.findById(id);//有隐患，最好是将用户也添加进去
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		System.out.println("哈哈，我进入了");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("哈哈，我要打印了");
		System.out.println(mydocMarInfo.getContent());
		 out.print(mydocMarInfo.getContent());		 
		 out.flush();
		 out.close();
		 System.out.println("哈哈，我关闭了");
		//return "listUI";//listUI
				
	}
	
	public String deleteSelected(){
				
		HttpServletRequest request = ServletActionContext.getRequest();
		String idStr=request.getParameter("infoId");
		
		if(idStr.contains(",")){
			String[] strArr=idStr.split(",");
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<strArr.length;i++){
				sb.append("\'");
				sb.append(strArr[i]);
				sb.append("\'");
				if(i!=strArr.length-1){
					sb.append(",");
				}
			}
			idStr=sb.toString();
		}else{
			idStr="\'"+idStr+"\'";
		}
		System.out.println(idStr);
		this.infodbService.deleteBatch(idStr);
		return "delSelRes";
	}
	
	public String deleteById(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id=request.getParameter("infoId");
		infodbService.deleteById(id);	
		
		return "delById";
	}

	public String listUI(){
		List<DocMarInfo> allList=null;
		//System.out.println(docMarInfo);
		int allItemCount=0;
		CrmStaff staff=(CrmStaff)ActionContext.getContext().getSession().get("loginStaff");
		String userName=staff.getLoginName();
		//先判断有没有权限操作全部
		docMarInfo.setCreator(userName);
		try {
			if(docMarInfo!=null&&docMarInfo.getAccountNum()!=null){
				
				docMarInfo.setAccountNum(URLDecoder.decode(docMarInfo.getAccountNum(), "utf-8"));
				//System.out.println("进了"+docMarInfo.getAccountNum());
				allList=this.infodbService.findByCon(docMarInfo);
			}else{
				allList=this.infodbService.findByCon(docMarInfo);
				//allList=this.infodbService.findAll();这是管理员的操作
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ActionContext.getContext().put("allItemCount", allList.size());
		ActionContext.getContext().put("allInfoDb", allList);
		return "listUI";
	}

	public String publicInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id=request.getParameter("infoId");
		String state=request.getParameter("state");
		DocMarInfo entity=this.infodbService.findById(id);
		if(entity!=null){
			entity.setState(state);
			this.infodbService.update(entity);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 out.print("更新状态成功");
		 out.flush();
		 out.close();
		return "listUI";
	}

}
