package com.hust.crm.Loginstaff.web.action;

import java.util.List;

import com.hust.core.action.BaseAction;
import com.hust.crm.department.domain.CrmDepartment;
import com.hust.crm.department.service.DepartmentService;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.crm.staff.service.StaffService;
import com.hust.crm.utils.MyStringUtils;
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
		
		CrmStaff findStaff = staffService.login(staff);
		
		if(findStaff != null){
			
			ActionContext.getContext().getSession().put("loginStaff", findStaff);
			
			return "success";
		} 
		
		
		this.addFieldError("", "�û������������");

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
				if(staffService.findByLoginName(staff.getLoginName())>0){
					this.addFieldError("registerErr", "�û����ѱ�ע�ᣡ");
					return "register";
				}
				if(staffService.addCrmStaff(staff))						
					return "login";
			}
			else{
				this.addFieldError("pwdFail", "�������벻һ�£����������룡");
				return "register";
			}
		}else{			
			this.addFieldError("inputErr", "���벻�Ϸ�������Ϊ��");
			return "register";
		}
		this.addFieldError("failedErr", "ע��ʧ��������ע�ᣡ");
		return "register";
	}


}
