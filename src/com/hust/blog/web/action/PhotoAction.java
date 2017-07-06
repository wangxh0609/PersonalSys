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
	 * @ ���� ��̨,��֤�Ƿ��¼
	 * @return
	 */
	private Boolean isHaveLogin(){		
		Object obj=ActionContext.getContext().getSession().get("boZhuStaff");//
		if(obj!=null){
			if(obj instanceof CrmStaff){
				CrmStaff staff=(CrmStaff)obj;
				if(!staff.getLoginName().equals("�����û�")){
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
	 * @���� ǰ̨-��ѯ����ͼƬ
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
	 * @���� ��̨-��ѯ����ͼƬ
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
	 * @���� �鿴ĳ��ͼƬ��ϸ����
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
	 * @���� ɾ��ͼƬ
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
			//photoDao.connect();
			if (photoDao.operationPhoto("delete", photoBean)) {
				boolean result = file.delete();
				if (result) {
					messages = "<li>ɾ����Ƭ�ɹ���</li>";
					forward = "/admin/success.jsp";
					href = "<a href='" + request.getContextPath() + "/sys/photo_admin_adminphotoList'>[����ɾ��������Ƭ]</a>";
					request.setAttribute("messages", messages);
					request.setAttribute("href", href);
					return "success";
				} else {
					messages = "<li>ɾ����Ƭʧ�ܣ�</li>";
					forward = "/admin/error.jsp";
					href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
				}
	
			} else {
				messages = "<li>ɾ����Ƭʧ�ܣ�</li>";
				forward = "/admin/error.jsp";
				href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
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
	 * @���� �ϴ�ͼƬ����
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
	
			if (photoInfo == null || photoInfo.equals("")) { // ��֤��Ƭ������Ϣ����û�����룬����ʾ������Ƭ������Ϣ
				messages = "��������Ƭ������Ϣ��";
				href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
			} else {
				if (photoImg != null) {
					if(photoImg.exists() && photoImg.isFile()){
					    long fileS = photoImg.length();
					    if(fileS>5*1024*1024){
					    	messages = "�������ϴ�����5M���ļ����ļ���";
							href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
							request.setAttribute("messages", messages);
							request.setAttribute("href", href);
							return "fail";
					    }
					}
					if (!validatePhotoType(photoImgFileName)) {					
						messages = "ֻ���ϴ�jpg|jpeg|gif|bmp|png|ico��ʽ���ļ���";
						href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
					} else {
						int dotIndex = photoImgFileName.lastIndexOf(".");
						String ext = photoImgFileName.substring(dotIndex + 1);
	
						String now = MyTools.changeTime(new Date()); // ��ȡ��ǰʱ�䲢��ʽ��Ϊ�ַ���
						String photoAddr = photoDao.queryMaxId() + "." + ext;
						photoBean.setPhotoAddr("/myPhoto/" + photoAddr);
						photoBean.setPhotoTime(now);
						photoBean.setPhotoInfo(photoInfo);
						CrmStaff staff=(CrmStaff)ActionContext.getContext().getSession().get("boZhuStaff");
						if(staff!=null){
							photoBean.setPhotoCreator(staff.getLoginName());
						}
						//photoDao.connect();
						boolean mark = photoDao.operationPhoto("upload", photoBean);
						if (mark) {
							try {
								FileUtils.copyFile(photoImg, new java.io.File(filePath, photoAddr));
								messages = "�ϴ��ļ��ɹ���";
								href = "<a href='" + request.getContextPath() + "/sys/photo_admin_photoUpload'>[�����ϴ�]</a>";
								request.setAttribute("messages", messages);
								request.setAttribute("href", href);
								return "success";
							} catch (Exception ee) {
								messages = "�ϴ��ļ�ʧ�ܣ�";
								href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
								ee.printStackTrace();
							}
						} else {
							messages = "�����ļ���Ϣʧ�ܣ�";
							href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
						}
					}
				} else {
					messages = "��ѡ��Ҫ�ϴ����ļ���";
					href = "<a href='javascript:window.history.go(-1)'>[����]</a>";
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