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
		// �����жϱ��Ƿ�֧���ļ��ϴ�
		boolean ismultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!ismultipartContent) {
			throw new RuntimeException("your form is not multipart");
		}
		// *����һ��DiskFileItemFactory������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// ����һ��ServletFileUpload���Ķ���
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setHeaderEncoding("UTF-8");// ����ϴ���������
		sfu.setFileSizeMax(1024 * 1024 * 3);
		// ����request���󣬲��õ�һ�������
		try {
			List<FileItem> fileItems = sfu.parseRequest(request);
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					// ��ͨ����
					processFormField(fileItem);
				} else {
					// �ϴ�����
					String storePath=processUploadFiels(fileItem);
					//��Ҫ�����ݿ��м�¼
					
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

	}

	private String processUploadFiels(FileItem fileItem) {

		try {
			InputStream is = fileItem.getInputStream();
			// ͨ����������ϴ����ļ����浽����
			// ����һ���ļ����̵�Ŀ¼
			String directoryRealPath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload");
			File storeDirectory = new File(directoryRealPath);// �ȴ����ļ��ִ���·��
			if (!storeDirectory.exists()) {
				storeDirectory.mkdirs();// ����һ��ָ����Ŀ¼
			}
			String filename = fileItem.getName();// �ļ����е����� a.txt
			// �����ļ���
			// filename=filename.substring(filename.lastIndexOf(File.separator)+1);
			// //������
			if (filename != null) {
				filename = FilenameUtils.getName(filename);// ���filename ��ȫ·��������
			}
			// ����ļ�����
			filename = UUID.randomUUID() + "_" + filename;

			// Ŀ¼��ɢ
			// ���ڴ�ɢ
			// String childDirectory=makeChildDirectory(storeDirectory);

			String childDirectory = makeChildDirectory(storeDirectory, filename);

			// ��storeDirectory����һ������Ŀ¼���ļ�
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
			// ͨ����������ϴ����ļ����浽����
			// ����һ���ļ����̵�Ŀ¼
			/*
			 * String
			 * directoryRealPath=ServletActionContext.getServletContext().
			 * getRealPath("/WEB-INF/upload"); File storeDirectory=new
			 * File(directoryRealPath);//�ȴ����ļ��ִ���·��
			 * if(!storeDirectory.exists()){ storeDirectory.mkdirs();//����һ��ָ����Ŀ¼
			 * } String filename=fileItem.getName();//�ļ����е����� a.txt //�����ļ���
			 * //filename=filename.substring(filename.lastIndexOf(File.separator
			 * )+1); //������ if(filename!=null){
			 * filename=FilenameUtils.getName(filename);//���filename ��ȫ·�������� }
			 * //����ļ����� filename=UUID.randomUUID()+"_"+filename;
			 * 
			 * //Ŀ¼��ɢ //���ڴ�ɢ //String
			 * childDirectory=makeChildDirectory(storeDirectory);
			 * 
			 * String
			 * childDirectory=makeChildDirectory(storeDirectory,filename);
			 * 
			 * //��storeDirectory����һ������Ŀ¼���ļ� File file=new
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
			// ͨ����������ϴ����ļ����浽����
			// ����һ���ļ����̵�Ŀ¼
			String directoryRealPath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload");// this.getServletContext().getRealPath();
			File storeDirectory = new File(directoryRealPath);// �ȴ����ļ��ִ���·��
			if (!storeDirectory.exists()) {
				storeDirectory.mkdirs();// ����һ��ָ����Ŀ¼
			}
			String filename = fileItem.getName();// �ļ����е����� a.txt
			// �����ļ���
			// filename=filename.substring(filename.lastIndexOf(File.separator)+1);
			// //������
			if (filename != null) {
				filename = FilenameUtils.getName(filename);// ���filename ��ȫ·��������
			}
			// ����ļ�����
			filename = UUID.randomUUID() + "_" + filename;

			// Ŀ¼��ɢ
			// ���ڴ�ɢ
			// String childDirectory=makeChildDirectory(storeDirectory);

			String childDirectory = makeChildDirectory(storeDirectory, filename);
			// �ϴ��ļ�
			fileItem.write(new File(storeDirectory, childDirectory + File.separator + filename));
			fileItem.delete();// �Զ�ɾ����ʱ�ļ�
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ����Ŀ¼��ɢ
	private String makeChildDirectory(File storeDirectory, String filename) {

		int hashcode = filename.hashCode();// �����ַ���ת����32λhashcode��
		String code = Integer.toHexString(hashcode);// ��hashcodeת��Ϊ16���Ƶı���
		String childDirectory = code.charAt(0) + File.separator + code.charAt(1);
		// ����ָ��Ŀ¼
		File file = new File(storeDirectory, childDirectory);
		if (!file.exists()) {
			file.mkdirs();
		}
		return childDirectory;
	}

	// //�������ڴ�ɢ
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

		String fieldname = fileItem.getFieldName();// �ֶ���

		String fieldvalue = fileItem.getString();// �ֶ�ֵ
		try {
			fieldvalue = new String(fieldvalue.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(fieldname + "=" + fieldvalue);
	}
}
