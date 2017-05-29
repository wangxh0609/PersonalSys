package com.hust.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FileAccess {
	private String ftpServer = null;
	private String userName = null;
	private String password = null;
	private String serverPath=null;
	private int _FileStep = 500;
	// private String url=null;
	private FTPClient ftp;

	public FileAccess() {
		ReadConfigUpload rcf = new ReadConfigUpload();
		ftpServer = rcf.getFTPSERVER();
		userName = rcf.getFTPUSERNAME();
		password = rcf.getFTPPASSWORD();
		serverPath=rcf.getENVELOPERESULTROOT();
		// url=ftpServer+":21"
	}

	
	public boolean connect() throws Exception {
		return this.connect(serverPath, ftpServer, 21, userName, password);
	}
	/**
	 * 
	 * @param path
	 *            �ϴ���ftp�������ĸ�·����
	 * @param addr
	 *            ��ַ
	 * @param port
	 *            �˿ں�
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @return
	 * @throws Exception
	 */
	private boolean connect(String path, String addr, int port, String username, String password) throws Exception {
		boolean result = false;
		ftp = new FTPClient();
		int reply;
		ftp.connect(addr, port);
		ftp.login(username, password);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return result;
		}
		ftp.changeWorkingDirectory(path);
		result = true;
		return result;
	}

	//public String GenFileVaultSubDirectory(IFileInfo file, String reserveParam)
   // {
        //return pathname(file.Fileid);
    //}

    public String GenFileVaultSubDirectory(long fileid, String reserveParam)
    {
        return pathname(fileid);
    }

    private String pathname(long fileid)
    {
    	long i = (long)(fileid % 0xFFFFFFFF);
        i = (long)(i / _FileStep);
        String s = ("00000000" + i);
        s = s.substring(s.length() - 8);
        return s;
    }

	public void uploadFile(InputStream input,String fileName,long fileId) throws IOException{
		//���ȵõ������·��
		String subPath=this.GenFileVaultSubDirectory(fileId, "");
		//String szFileName = ftpServer+":21" + "/" + subPath + "/" + fileName;
//		/String szPathName =ftpServer+":21" + "/" + subPath + "/"; 
		ftp.makeDirectory(subPath);
		ftp.changeWorkingDirectory(subPath);
		ftp.storeFile(fileName, input);	
		ftp.logout();
	}
	
	/**
	 * @author
	 * @class ItemFtp
	 * @title upload
	 * @Description :
	 * @time 
	 * @return void
	 * @exception :(Error
	 *                note)
	 * @param file
	 *            �ϴ����ļ����ļ���
	 * @param path
	 *            �ϴ����ļ���·��
	 * @throws Exception
	 */
	private void upload(File file, String path) throws Exception {

		System.out.println(" file.isDirectory() : " + file.isDirectory());

		if (file.isDirectory()) {
			ftp.makeDirectory(file.getName());
			ftp.changeWorkingDirectory(file.getName());
			String[] files = file.list();
			for (int i = 0; i < files.length; i++) {
				File file1 = new File(file.getPath() + "\\" + files[i]);
				if (file1.isDirectory()) {
					upload(file1, path);
					ftp.changeToParentDirectory();
				} else {
					File file2 = new File(file.getPath() + "\\" + files[i]);
					FileInputStream input = new FileInputStream(file2);
					ftp.storeFile(file2.getName(), input);
					input.close();
				}
			}
		} else {
			File file2 = new File(file.getPath());

			System.out.println(" file.getPath() : " + file.getPath() + " | file2.getName() : " + file2.getName());

			InputStream input = new FileInputStream(file2);

			ftp.changeWorkingDirectory(path);
			ftp.storeFile(file2.getName(), input);

			input.close(); // �ر�������
			ftp.logout(); // �˳�����
		}
	}

	/**
	 * @author
	 * @class ItemFtp
	 * @title download
	 * @Description : FPT �����ļ�����
	 * @time 
	 * @return void
	 * @exception :(Error
	 *                note)
	 * @param reomvepath
	 *            ���ص��ļ���·��
	 * @param fileName
	 *            ���ص��ļ���
	 * @param localPath
	 *            ���ص��ļ�����·��
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void download(String reomvepath, String fileName, String localPath) throws Exception {

		ftp.changeWorkingDirectory(reomvepath);

		// �г���Ŀ¼�������ļ�
		FTPFile[] fs = ftp.listFiles();
		// ���������ļ����ҵ�ָ�����ļ�
		for (FTPFile ff : fs) {
			if (ff.getName().equals(fileName)) {
				// ���ݾ���·����ʼ���ļ�
				File localFile = new File(localPath + "/" + ff.getName());
				// �����
				OutputStream is = new FileOutputStream(localFile);
				// �����ļ�
				ftp.retrieveFile(ff.getName(), is);
				System.out.println("���سɹ�!");
				is.close();
			}
		}

		ftp.logout(); // �˳�����

	}

	public static void main(String[] args) throws Exception {

		// ItemFtp t = new ItemFtp();

		// boolean lianjie = t.connect("D:\\", "127.0.0.1", 21, "jiang",
		// "jiang");
		// System.out.println( "���� ��" + lianjie );

		// �ϴ�
		// File file = new File("d:\\test.txt");
		// t.upload(file , "E:\\ftptest\\mulu");

		// ����
		// t.download("E:\\ftptest\\mulu", "test.txt", "D:\\db");

	}

}
