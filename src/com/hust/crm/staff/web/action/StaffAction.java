package com.hust.crm.staff.web.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.hust.core.action.BaseAction;
import com.hust.core.util.QueryHelper;
import com.hust.crm.department.domain.CrmDepartment;
import com.hust.crm.department.service.DepartmentService;
import com.hust.crm.post.domain.CrmPost;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.crm.staff.service.StaffService;
import com.hust.crm.utils.MyStringUtils;
import com.hust.docMgr.infodb.domain.DocMarInfo;
import com.hust.docMgr.role.service.RoleService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class StaffAction extends BaseAction {// implements ModelDriven<CrmStaff>

	private CrmStaff staff;
	@Resource
	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	// @Override
	// public CrmStaff getModel() {
	// return staff ;
	// }
	public CrmStaff getStaff() {
		return staff;
	}

	public void setStaff(CrmStaff staff) {
		this.staff = staff;
	}
    
	private File headImg;
	private String headImgContentType;
	private String headImgFileName;

	private String[] userRoleIds;

	
	private String operation;
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

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

	public String[] getUserRoleIds() {
		return userRoleIds;
	}

	public void setUserRoleIds(String[] userRoleIds) {
		this.userRoleIds = userRoleIds;
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
	
	public String frame() {
		return "frame";
	}

	public String top() {
		return "top";
	}

	public String left() {
		return "left";
	}
	private Boolean isHaveLogin() {
		Object obj = ActionContext.getContext().getSession().get("loginStaff");
		if (obj != null) {
			if (obj instanceof CrmStaff) {
				CrmStaff staff = (CrmStaff) obj;
				if (staff.getLoginName().equals("wangxh")) {
					return true;
				}
			}
		}
		return false;
	}

	public String listUI() {

		QueryHelper queryHelper = new QueryHelper(CrmStaff.class, "u");
		// queryHelper.addFromCon(CrmPost.class, "p");
		try {
			System.out.println(ActionContext.getContext().getSession().get("uploadPath"));
			if (staff != null) {				
				System.out.println(staff.getStaffName());
				if (StringUtils.isNotBlank(staff.getStaffName())) {
					staff.setStaffName(URLDecoder.decode(staff.getStaffName(), "utf-8"));
					queryHelper.addCondition("u.staffName like ?", "%" + staff.getStaffName() + "%");
				}
			}
			// queryHelper.addCondition("u.postId=?","p.postId");
			pageResult = staffService.getPageResult(queryHelper, getPageNo(), getPageSize());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "listUI";
	}

	public String addUI() {
		// ActionContext.getContext().getContextMap().put("roleList",
		// roleService.findObjects());
		return "addUI";
	}
   public void verifyAccount(){
	    HttpServletRequest request = ServletActionContext.getRequest();
		String loginName=request.getParameter("loginName");
		System.out.println(loginName);
		System.out.println(staff==null);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");			
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("哈哈，我要打印了");
		
		if(staffService.findByLoginName(loginName)!=null){
			 out.print("false");					 
		}else{
			out.print("true");
		}
	
	   out.flush();
	   out.close();
   }
   
   public boolean validatePhotoType(String filename) {
	   if(filename==null||filename.equals("")){
		   return true;
	   }
	    boolean result=false;
		String reg = "(?i).+?\\.(jpg|jpeg|gif|bmp|png|ico)";
		if(headImg!=null){
			if(headImg.exists()&&headImg.isFile()){
				long fsize=headImg.length();
				if(fsize>1*1024*1024){
					result=false;
				}else{
				   result=true;
				}
			}
		}else{
			result=true;
		}
		return filename.matches(reg)&&result;

	}

   
	public String addUser() {
		if(!isHaveLogin()){
			return "list";
		}
		try {
		//	System.out.println(headImg == null);
			if (staff != null) {
			    if(validatePhotoType(headImgFileName)){
					if (headImg != null) {
						String filePath = null;
						//System.out.println(uploadPath == null);
						if (uploadPath != null) {
							filePath = uploadPath + "/user";
						}
	
						System.out.println(filePath);
						// System.out.println(ServletActionContext.getServletContext().getContextPath());
	
						String fileName = UUID.randomUUID().toString().replaceAll("-", "")
								+ headImgFileName.substring(headImgFileName.lastIndexOf("."));
	
						FileUtils.copyFile(headImg, new File(filePath, fileName));
	
						staff.setHeadImg("/user/" + fileName);
					}
					staffService.addCrmStaff(staff);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}

	public String editUI() {
		if(!isHaveLogin()){
			return "editUI";
		}

		staff= this.staffService.findById(staff.getStaffId());

		//ActionContext.getContext().getValueStack().push(findStaff);

		//List<CrmDepartment> allDepartment = departmentService.findAll();

		//ActionContext.getContext().getValueStack().set("allDepartment", allDepartment);

		return "editUI";
	}
	public String editSave(){
		if(!isHaveLogin()){
			return "list";
		}
		CrmStaff temStaff=this.staffService.findByLoginName(staff.getLoginName());
		temStaff.setEmail(staff.getEmail());
		temStaff.setGender(staff.getGender());
		temStaff.setStaffName(staff.getStaffName());
		 if(!validatePhotoType(headImgFileName)){
			 return "list";
		 }
		if (headImg != null) {
			String filePath = null;			
			if (uploadPath != null) {
				filePath = uploadPath + "/user";
			}		

			//System.out.println(filePath);			

			String fileName = UUID.randomUUID().toString().replaceAll("-", "")
					+ headImgFileName.substring(headImgFileName.lastIndexOf("."));

			try {
				String lastPath=temStaff.getHeadImg();
				System.out.println("前路径"+lastPath);
				if(lastPath!=null&&!lastPath.equals("")){
					System.out.println("改头像"+staff.getHeadImg());
					File temFile=new File(uploadPath+lastPath);
					
					FileUtils.copyFile(headImg, new File(filePath, fileName));
					FileUtils.deleteQuietly(temFile);
				}else{
					FileUtils.copyFile(headImg, new File(filePath, fileName));
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}

			temStaff.setHeadImg("/user/" + fileName);
			
		}
		if(staff.getLoginPwd().contains("****")){
			temStaff.setLoginPwd(temStaff.getLoginPwd());
			temStaff.setNeedEncry(false);
		}else{
			temStaff.setLoginPwd(staff.getLoginPwd());
		}
		temStaff.setPhoneNum(staff.getPhoneNum());
		temStaff.setOnDutyDate(staff.getOnDutyDate());
		temStaff.setState(staff.getState());
		this.staffService.updatePassword(temStaff);
		
		return "list";
	}
	public String delete(){
		if(!isHaveLogin()){
			return "list";
		}
		this.staffService.delete(staff.getStaffId());
		return "list";
	}
	

	
}
