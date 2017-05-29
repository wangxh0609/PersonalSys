package com.hust.crm.staff.web.action;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hust.core.action.BaseAction;
import com.hust.core.util.QueryHelper;
import com.hust.crm.department.domain.CrmDepartment;
import com.hust.crm.department.service.DepartmentService;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.crm.staff.service.StaffService;
import com.hust.crm.utils.MyStringUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class StaffAction extends BaseAction{
	
	private CrmStaff staff;
	

	public CrmStaff getStaff() {
		return staff;
	}

	public void setStaff(CrmStaff staff) {
		this.staff = staff;
	}

	private File headImg;
	private String headImgContentType;
	private String headImgFileName;
	
	public File getHeadImg() {
		return headImg;
	}

	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}

	public String getHeadImgContentType() {
		return headImgContentType;
	}

	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}

	public String getHeadImgFileName() {
		return headImgFileName;
	}

	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
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
	
	public String listUI(){
		
		QueryHelper queryHelper = new QueryHelper(CrmStaff.class, "u");
		try {
			System.out.println("123");
			if (staff != null) {
				System.out.println("进入");
				if (StringUtils.isNotBlank(staff.getLoginName())) {
					staff.setLoginName(URLDecoder.decode(staff.getLoginName(), "utf-8"));
					queryHelper.addCondition("u.loginName like ?", "%" + staff.getLoginName() + "%");
				}
			}
			pageResult = staffService.getPageResult(queryHelper, getPageNo(), getPageSize());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "listUI";
	}
	public String addUI(){
		return "addUI";
	}
	
	public String editUI(){
		
		CrmStaff findStaff=this.staffService.findById(staff.getStaffId());		
	
		ActionContext.getContext().getValueStack().push(findStaff);
		
	
		List<CrmDepartment> allDepartment=departmentService.findAll();
	
		ActionContext.getContext().getValueStack().set("allDepartment", allDepartment);
		
		return "editUI";
	}
	
	public String logout(){
		ActionContext.getContext().getSession().clear();
		return "login";
	}
	
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
	
	/*
	public String register(){
		if(staff.getLoginName()!=null&&!staff.getLoginName().trim().equals("")&&staff.getStaffName()!=null&&!staff.getStaffName().trim().equals("")){						
			if(staff.getLoginPwd().equals(getReNewPassword())&&getReNewPassword()!=null&&getReNewPassword().trim()!=""){
				if(staffService.findByLoginName(staff.getLoginName())>0){
					this.addFieldError("registerErr", "用户名已被注册！");
					return "register";
				}
				if(staffService.addCrmStaff(staff))						
					return "login";
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
*/
}
