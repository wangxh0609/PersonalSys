package com.hust.blog.web.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.hust.AccountSys.blog.dao.LogonDao;
import com.hust.core.action.BaseAction;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.crm.staff.service.StaffService;
import com.hust.docMgr.blog.domain.MasterBean;
import com.opensymphony.xwork2.ActionContext;

public class BlogLoginAction extends BaseAction {

	private CrmStaff staff;
	private StaffService staffService;

	public StaffService getStaffService() {
		return staffService;
	}

	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
	}

	public CrmStaff getStaff() {
		return staff;
	}

	public void setStaff(CrmStaff staff) {
		this.staff = staff;
	}

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;

	public String islogon() {
		//loginStaff 登陆者信息    revisitorStaff 被访问者信息
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		Object obj = ActionContext.getContext().getSession().get("loginStaff");
		Object obj1 = ActionContext.getContext().getSession().get("boZhuStaff");
		if (obj != null) {
			if (obj instanceof CrmStaff) {
				CrmStaff loginStaff = (CrmStaff) obj;
				if (loginStaff.getLoginName().equals("wangxh")) {
					if(obj1==null){						
						ActionContext.getContext().getSession().put("boZhuStaff", loginStaff);
					}
					return "adminIndex";
				} else {
					if ((obj1 != null)) {
						if (obj1 instanceof CrmStaff) {
							CrmStaff tempStaff = (CrmStaff) obj1;
							if (tempStaff.getLoginName().equals("wangxh")) {
								return "adminIndex";
							}
						}
					}
				}
			}
		}
		return "logon";
	}

	public String logon() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		if (staff != null) {
			if (staff.getLoginName().equals("wangxh")) {
				CrmStaff findStaff = staffService.login(staff);
				if (findStaff != null) {
					//为了博主登陆后回复留言不需要再次登陆，需要设置revisitorStaff setNeedEncry为false,
					//不然在返回博客首页的时候会认为是从网页上直接登陆，就需要再次登陆
					Object revisitor=ActionContext.getContext().getSession().get("revisitorStaff");
					if(revisitor!=null){
						if(revisitor instanceof CrmStaff){
							CrmStaff temstaff=(CrmStaff)revisitor;
							temstaff.setNeedEncry(false);
							ActionContext.getContext().getSession().put("revisitorStaff",temstaff);
						}
					}
					ActionContext.getContext().getSession().put("loginStaff", findStaff);
					ActionContext.getContext().getSession().put("boZhuStaff", findStaff);
					return "adminIndex";
				}
			}
		}
		request.setAttribute("messages", "<li>输入的用户名或密码错误！</li>");
		return "logon";
	}

	public boolean validateLogon(HttpServletRequest request, HttpServletResponse response) {
		boolean mark = true;
		String messages = "";
		String name = request.getParameter("userName");
		String password = request.getParameter("userPass");
		if (name == null || name.equals("")) {
			mark = false;
			messages += "<li>请输入 <b>用户名！</b></li>";
		}
		if (password == null || password.equals("")) {
			mark = false;
			messages += "<li>请输入 <b>密&nbsp;&nbsp;码！</b></li>";
		}
		request.setAttribute("messages", messages);
		return mark;
	}

	public String logout() {
		ActionContext.getContext().getSession().remove("boZhuStaff");
		ActionContext.getContext().getSession().remove("loginStaff");		
		return "adminFirstPage";
		// HttpSession session=request.getSession();
		// session.removeAttribute("logoner");
		// RequestDispatcher
		// rd=request.getRequestDispatcher("/front/FrontIndex.jsp");
		// rd.forward(request,response);
	}
}
