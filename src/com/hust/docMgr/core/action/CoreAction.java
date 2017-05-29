package com.hust.docMgr.core.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;

import com.hust.core.action.BaseAction;
import com.hust.core.util.FileAccess;
import com.opensymphony.xwork2.ActionContext;

public class CoreAction extends BaseAction {
	
	public void upLoadFile() throws UnsupportedEncodingException {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		// 首先判断表单是否支持文件上传
		boolean ismultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!ismultipartContent) {
			throw new RuntimeException("your form is not multipart");
		}
		// *创建一个DiskFileItemFactory工厂类
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 创建一个ServletFileUpload核心对象
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setHeaderEncoding("UTF-8");// 解决上传表单项乱码
		sfu.setFileSizeMax(1024 * 1024 * 3);
		// 解析request对象，并得到一个表单项集合
		try {
			List<FileItem> fileItems = sfu.parseRequest(request);
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					// 普通表单项
					processFormField(fileItem);
				} else {
					// 上传表单项
					String storePath=processUploadFiels(fileItem);
					//需要在数据库中记录
					
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

	}

	private String processUploadFiels(FileItem fileItem) {

		try {
			InputStream is = fileItem.getInputStream();
			// 通过输出流将上传的文件保存到磁盘
			// 创建一个文件存盘的目录
			String directoryRealPath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload");
			File storeDirectory = new File(directoryRealPath);// 既代表文件又代表路径
			if (!storeDirectory.exists()) {
				storeDirectory.mkdirs();// 创建一个指定的目录
			}
			String filename = fileItem.getName();// 文件项中的名字 a.txt
			// 处理文件名
			// filename=filename.substring(filename.lastIndexOf(File.separator)+1);
			// //可以用
			if (filename != null) {
				filename = FilenameUtils.getName(filename);// 解决filename 是全路径的问题
			}
			// 解决文件重名
			filename = UUID.randomUUID() + "_" + filename;

			// 目录打散
			// 日期打散
			// String childDirectory=makeChildDirectory(storeDirectory);

			String childDirectory = makeChildDirectory(storeDirectory, filename);

			// 在storeDirectory创建一个完整目录的文件
			File file = new File(storeDirectory, childDirectory + File.separator + filename);
			FileOutputStream fos = new FileOutputStream(file);
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			is.close();
			fileItem.delete();
			System.out.println(file.getName()+" "+file.getAbsolutePath()+"  "+file.getCanonicalPath()+" "+file.getPath());
			return file.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void processUploadFielsTEST(FileItem fileItem) {

		try {
			InputStream is = fileItem.getInputStream();
			FileAccess fileAccess = new FileAccess();
            int fileId=0;
			if (fileAccess.connect()) {				
				fileAccess.uploadFile(is, fileItem.getName(), fileId);
			}

			is.close();
			fileItem.delete();
			// 通过输出流将上传的文件保存到磁盘
			// 创建一个文件存盘的目录
			/*
			 * String
			 * directoryRealPath=ServletActionContext.getServletContext().
			 * getRealPath("/WEB-INF/upload"); File storeDirectory=new
			 * File(directoryRealPath);//既代表文件又代表路径
			 * if(!storeDirectory.exists()){ storeDirectory.mkdirs();//创建一个指定的目录
			 * } String filename=fileItem.getName();//文件项中的名字 a.txt //处理文件名
			 * //filename=filename.substring(filename.lastIndexOf(File.separator
			 * )+1); //可以用 if(filename!=null){
			 * filename=FilenameUtils.getName(filename);//解决filename 是全路径的问题 }
			 * //解决文件重名 filename=UUID.randomUUID()+"_"+filename;
			 * 
			 * //目录打散 //日期打散 //String
			 * childDirectory=makeChildDirectory(storeDirectory);
			 * 
			 * String
			 * childDirectory=makeChildDirectory(storeDirectory,filename);
			 * 
			 * //在storeDirectory创建一个完整目录的文件 File file=new
			 * File(storeDirectory,childDirectory+File.separator+filename);
			 * FileOutputStream fos=new FileOutputStream(file); int len =0;
			 * byte[] buffer=new byte[1024]; while((len=is.read(buffer))!=-1){
			 * fos.write(buffer, 0, len); } fos.close(); is.close();
			 * fileItem.delete();
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void processUploadFiels1(FileItem fileItem) {

		try {
			InputStream is = fileItem.getInputStream();
			// 通过输出流将上传的文件保存到磁盘
			// 创建一个文件存盘的目录
			String directoryRealPath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload");// this.getServletContext().getRealPath();
			File storeDirectory = new File(directoryRealPath);// 既代表文件又代表路径
			if (!storeDirectory.exists()) {
				storeDirectory.mkdirs();// 创建一个指定的目录
			}
			String filename = fileItem.getName();// 文件项中的名字 a.txt
			// 处理文件名
			// filename=filename.substring(filename.lastIndexOf(File.separator)+1);
			// //可以用
			if (filename != null) {
				filename = FilenameUtils.getName(filename);// 解决filename 是全路径的问题
			}
			// 解决文件重名
			filename = UUID.randomUUID() + "_" + filename;

			// 目录打散
			// 日期打散
			// String childDirectory=makeChildDirectory(storeDirectory);

			String childDirectory = makeChildDirectory(storeDirectory, filename);
			// 上传文件
			fileItem.write(new File(storeDirectory, childDirectory + File.separator + filename));
			fileItem.delete();// 自动删除临时文件
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 按照目录打散
	private String makeChildDirectory(File storeDirectory, String filename) {

		int hashcode = filename.hashCode();// 返回字符串转换的32位hashcode码
		String code = Integer.toHexString(hashcode);// 将hashcode转化为16进制的编码
		String childDirectory = code.charAt(0) + File.separator + code.charAt(1);
		// 创建指定目录
		File file = new File(storeDirectory, childDirectory);
		if (!file.exists()) {
			file.mkdirs();
		}
		return childDirectory;
	}

	// //按照日期打散
	// private String makeChildDirectory(File storeDirectory) {
	// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	// String dateDirectory=sdf.format(new Date());
	// File file=new File(storeDirectory,dateDirectory);
	// if(!file.exists()){
	// file.mkdirs();
	// }
	// return dateDirectory;
	// }

	private void processFormField(FileItem fileItem) {

		String fieldname = fileItem.getFieldName();// 字段名

		String fieldvalue = fileItem.getString();// 字段值
		try {
			fieldvalue = new String(fieldvalue.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(fieldname + "=" + fieldvalue);
	}
}
