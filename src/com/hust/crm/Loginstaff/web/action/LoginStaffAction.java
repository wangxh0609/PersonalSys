package com.hust.crm.Loginstaff.web.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hust.AccountSys.blog.serviceImpl.VisitorLogService;
import com.hust.core.action.BaseAction;
import com.hust.crm.department.domain.CrmDepartment;
import com.hust.crm.department.service.DepartmentService;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.crm.staff.service.StaffService;
import com.hust.crm.utils.MyStringUtils;
import com.hust.docMgr.blog.domain.VisitorLogBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class LoginStaffAction extends BaseAction implements ModelDriven<CrmStaff> {
	
	
	private CrmStaff staff = new CrmStaff();
	@Override
	public CrmStaff getModel() {
	
		return staff ;
	}

	private StaffService staffService;
	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
	}

	private DepartmentService departmentService;
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	private String oldPassword;
	private String newPassword;
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getReNewPassword() {
		return reNewPassword;
	}

	public void setReNewPassword(String reNewPassword) {
		this.reNewPassword = reNewPassword;
	}

	private String reNewPassword;
	
	///////////////////////////////////////////////
	
	
	/**
	 *
	 * @return
	 */
	public String login(){
		Object obj=ActionContext.getContext().getSession().get("loginType");//, "blog"
		CrmStaff findStaff = staffService.login(staff);
		
		if(findStaff != null){
			VisitorLogService visitorService=new VisitorLogService();
			VisitorLogBean visitorBean=new VisitorLogBean();
			visitorBean.setVisitorName(findStaff.getLoginName());
			visitorBean.setVisitorInfo(findStaff.getLoginName()+"登陆了系统！");
			
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss " );
			String datestr = sdf.format(new Date());
			visitorBean.setVisitorTime(datestr);
			visitorService.operationArticle("add", visitorBean);
			ActionContext.getContext().getSession().put("loginStaff", findStaff);
			if(obj!=null){
				String loginType=(String)obj;
				if(loginType.equals("blog")){
					Object teobj=ActionContext.getContext().getSession().get("revisitorStaff");
					if(teobj!=null){
						CrmStaff revisitorStaff=(CrmStaff)teobj;
						revisitorStaff.setNeedEncry(false);//表示登陆了
						ActionContext.getContext().getSession().put("revisitorStaff",revisitorStaff);
					}
					return "blogSuccess";
				}
			}
			return "success";
		} 
		
		
		this.addFieldError("", "用户名或密码错误！");

		return "login";
	}
	
	/**
	 * 
	 * @return
	 */
	public String home(){
		return "home";
	}
	
	
	public String findAll(){
		List<CrmStaff> allStaff=staffService.findAllStaff();
		
		ActionContext.getContext().put("allStaff", allStaff);
		return "findAll";
	}
	
	/*
	public String editUI(){
		
		CrmStaff findStaff=this.staffService.findById(staff.getStaffId());		
	
		ActionContext.getContext().getValueStack().push(findStaff);
		
	
		List<CrmDepartment> allDepartment=departmentService.findAll();
	
		ActionContext.getContext().getValueStack().set("allDepartment", allDepartment);
		
		return "editUI";
	}*/
	
	public String logout(){
		ActionContext.getContext().getSession().clear();
		return "login";
	}
	/*
	public String editPassword(){
		Object obj=ActionContext.getContext().getSession().get("loginStaff");
		CrmStaff crmStaff=null;
		if(obj!=null){
			if(obj instanceof CrmStaff){
				crmStaff=(CrmStaff)obj;
			}			
		}
		if(crmStaff!=null){
			System.out.println(crmStaff.getLoginPwd());
			if(MyStringUtils.getMD5Value(getOldPassword()).equals(crmStaff.getLoginPwd())&&getNewPassword().equals(getReNewPassword())){
				crmStaff.setLoginPwd(getNewPassword());
				boolean isUpdate=staffService.updatePassword(crmStaff);
				if(isUpdate){
					return  "pwd";
				}
			}			
		}		
		return "editpwd";
	}
	*/
	public String register(){
		if(staff.getLoginName()!=null&&!staff.getLoginName().trim().equals("")&&staff.getStaffName()!=null&&!staff.getStaffName().trim().equals("")){						
			if(staff.getLoginPwd().equals(getReNewPassword())&&getReNewPassword()!=null&&getReNewPassword().trim()!=""){
				if(staffService.findByLoginName(staff.getLoginName())!=null){
					this.addFieldError("registerErr", "用户名已被注册！");
					return "register";
				}
				if(staffService.addCrmStaff(staff))	{	
					Object obj=ActionContext.getContext().getSession().get("loginType");
					if(obj!=null){
						String loginType=(String)obj;
						if(loginType.equals("blog")){
							ActionContext.getContext().getSession().put("loginStaff", staff);
							Object teobj=ActionContext.getContext().getSession().get("revisitorStaff");
							if(teobj!=null){
								CrmStaff revisitorStaff=(CrmStaff)teobj;
								revisitorStaff.setNeedEncry(false);//表示登陆了
								ActionContext.getContext().getSession().put("revisitorStaff",revisitorStaff);
							}
							return "blogSuccess";
						}
					}
					return "login";
				}
			}
			else{
				this.addFieldError("pwdFail", "密码输入不一致，请重新输入！");
				return "register";
			}
		}else{			
			this.addFieldError("inputErr", "输入不合法！不能为空");
			return "register";
		}
		this.addFieldError("failedErr", "注册失败请重新注册！");
		return "register";
	}


}
