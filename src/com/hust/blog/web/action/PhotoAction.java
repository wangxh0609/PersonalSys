package com.hust.blog.web.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.hust.AccountSys.blog.dao.PhotoDao;
import com.hust.core.action.BaseAction;
import com.hust.crm.staff.domain.CrmStaff;
import com.hust.docMgr.blog.domain.PhotoBean;
import com.hust.toolsbean.MyTools;
import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.opensymphony.xwork2.ActionContext;

public class PhotoAction extends BaseAction {

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private java.io.File photoImg;
	private String photoImgContentType;
	private String photoImgFileName;
	
	/**
	 * @ 功能 后台,验证是否登录
	 * @return
	 */
	private Boolean isHaveLogin(){		
		Object obj=ActionContext.getContext().getSession().get("boZhuStaff");//
		if(obj!=null){
			if(obj instanceof CrmStaff){
				CrmStaff staff=(CrmStaff)obj;
				if(!staff.getLoginName().equals("匿名用户")){
					return true;
				}
			}
		}
		return false;
	}
	
	
	public java.io.File getPhotoImg() {
		return photoImg;
	}

	public void setPhotoImg(java.io.File photoImg) {
		this.photoImg = photoImg;
	}

	public String getPhotoImgContentType() {
		return photoImgContentType;
	}

	public void setPhotoImgContentType(String photoImgContentType) {
		this.photoImgContentType = photoImgContentType;
	}

	public String getPhotoImgFileName() {
		return photoImgFileName;
	}

	public void setPhotoImgFileName(String photoImgFileName) {
		this.photoImgFileName = photoImgFileName;
	}

	public String photoList() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		try {
			return this.selectPhoto(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "photoList";
	}

	public String photoUpload() {

		return "photoUpload";
	}

	/**
	 * @功能 前台-查询所有图片
	 */
	public String selectPhoto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PhotoDao photoDao = new PhotoDao();
		List photoList = photoDao.queryPhoto("all");
		request.setAttribute("photoList", photoList);
		return "photoList";
		// RequestDispatcher
		// rd=request.getRequestDispatcher("/front/photo/photoList.jsp");
		// rd.forward(request,response);
	}

	/**
	 * @功能 后台-查询所有图片
	 */
	public String adminphotoList() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		if(isHaveLogin()){
			PhotoDao photoDao = new PhotoDao();
			List photoList = photoDao.queryPhoto("all");
			request.setAttribute("photoList", photoList);
			return "adminphotoList";
		}else{
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}	
	}

	/**
	 * @功能 查看某个图片详细内容
	 */
	public String photoSingle() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		PhotoDao photoDao = new PhotoDao();
		String strId = request.getParameter("id");
		int id = MyTools.strToint(strId);
		PhotoBean photoSingle = photoDao.queryPhoto(id);
		request.setAttribute("photoSingle", photoSingle);
		return "photoSingle";
		// RequestDispatcher rd =
		// request.getRequestDispatcher("/front/photo/PhotoSingle.jsp");
		// rd.forward(request, response);
	}

	/**
	 * @功能 删除图片
	 */
	public String deletePhoto() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		if(isHaveLogin()){
			String messages = "";
			String forward = "";
			String href = "";
	
			RequestDispatcher rd = null;
			PhotoDao photoDao = new PhotoDao();
			int id = MyTools.strToint(request.getParameter("id"));
			String fileAddr = photoDao.queryPhoto(id).getPhotoAddr();
			String photoDir = uploadPath;
			String delFile = photoDir + fileAddr;
	
			java.io.File file = new java.io.File(delFile);
	
			PhotoBean photoBean = new PhotoBean();
			photoBean.setId(id);
			photoDao.connect();
			if (photoDao.operationPhoto("delete", photoBean)) {
				boolean result = file.delete();
				if (result) {
					messages = "<li>删除照片成功！</li>";
					forward = "/admin/success.jsp";
					href = "<a href='" + request.getContextPath() + "/sys/photo_admin_adminphotoList'>[继续删除其他照片]</a>";
					request.setAttribute("messages", messages);
					request.setAttribute("href", href);
					return "success";
				} else {
					messages = "<li>删除照片失败！</li>";
					forward = "/admin/error.jsp";
					href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
				}
	
			} else {
				messages = "<li>删除照片失败！</li>";
				forward = "/admin/error.jsp";
				href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
			}
			request.setAttribute("messages", messages);
			request.setAttribute("href", href);
			return "fail";
		}else{
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}

	public boolean validatePhotoType(String filename) {
		String reg = "(?i).+?\\.(jpg|jpeg|gif|bmp|png|ico)";
		return filename.matches(reg);

	}

	/**
	 * @功能 上传图片功能
	 */
	public String addPhoto() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		if(isHaveLogin()){
			String filePath = null;
			if (uploadPath != null) {
				filePath = uploadPath + "/myPhoto";
			}
			String messages = "";
			String forward = "";
			String href = "";
			PhotoDao photoDao = new PhotoDao();
			PhotoBean photoBean = new PhotoBean();
	
			String photoInfo = request.getParameter("info");
	
			if (photoInfo == null || photoInfo.equals("")) { // 验证照片描述信息，若没有输入，则提示输入照片描述信息
				messages = "请输入照片描述信息！";
				href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
			} else {
				if (photoImg != null) {
					if(photoImg.exists() && photoImg.isFile()){
					    long fileS = photoImg.length();
					    if(fileS>5*1024*1024){
					    	messages = "不能能上传大于5M的文件的文件！";
							href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
							request.setAttribute("messages", messages);
							request.setAttribute("href", href);
							return "fail";
					    }
					}
					if (!validatePhotoType(photoImgFileName)) {					
						messages = "只能上传jpg|jpeg|gif|bmp|png|ico格式的文件！";
						href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
					} else {
						int dotIndex = photoImgFileName.lastIndexOf(".");
						String ext = photoImgFileName.substring(dotIndex + 1);
	
						String now = MyTools.changeTime(new Date()); // 获取当前时间并格式化为字符串
						String photoAddr = photoDao.queryMaxId() + "." + ext;
						photoBean.setPhotoAddr("/myPhoto/" + photoAddr);
						photoBean.setPhotoTime(now);
						photoBean.setPhotoInfo(photoInfo);
						CrmStaff staff=(CrmStaff)ActionContext.getContext().getSession().get("boZhuStaff");
						if(staff!=null){
							photoBean.setPhotoCreator(staff.getLoginName());
						}
						photoDao.connect();
						boolean mark = photoDao.operationPhoto("upload", photoBean);
						if (mark) {
							try {
								FileUtils.copyFile(photoImg, new java.io.File(filePath, photoAddr));
								messages = "上传文件成功！";
								href = "<a href='" + request.getContextPath() + "/sys/photo_admin_photoUpload'>[继续上传]</a>";
								request.setAttribute("messages", messages);
								request.setAttribute("href", href);
								return "success";
							} catch (Exception ee) {
								messages = "上传文件失败！";
								href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
								ee.printStackTrace();
							}
						} else {
							messages = "保存文件信息失败！";
							href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
						}
					}
				} else {
					messages = "请选择要上传的文件！";
					href = "<a href='javascript:window.history.go(-1)'>[返回]</a>";
				}
			}
			request.setAttribute("messages", messages);
			request.setAttribute("href", href);
			return "fail";
		}else{
			ActionContext.getContext().getSession().put("loginType", "blog");
			return "login";
		}
	}

	
}
