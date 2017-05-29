package com.hust.core.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ReadConfigUpload {
	private static Logger log = Logger.getLogger(ReadConfigUpload.class);
	public static String FTPSERVER;// ftp服务器ip地址
	public static String FTPUSERNAME;// ftp服务器用户名
	public static String FTPPASSWORD;// ftp服务器用户密码
	public static String ENVELOPERESULTROOT;// 存放ftp服务器的路径
	
	public ReadConfigUpload() {
		try {
			InputStream in = new BufferedInputStream(new FileInputStream("classpath:ftpConfig.properties"));
			Properties prop = new Properties();
			prop.load(in);
			ReadConfigUpload.FTPSERVER = prop.getProperty("ftpServer", "none");
			ReadConfigUpload.FTPUSERNAME = prop.getProperty("ftpUserName", "none");
			ReadConfigUpload.FTPPASSWORD = prop.getProperty("ftpPassword", "none");
			ReadConfigUpload.ENVELOPERESULTROOT = prop.getProperty("envelopeResultRoot", "none");
			log.debug("读取ftp配置信息成功！");
		} catch (IOException e) {
			log.debug("读取ftp配置信息失败！");
			e.printStackTrace();
		}
	}

	public static String getFTPSERVER() {
		return FTPSERVER;
	}

	public static void setFTPSERVER(String ftpserver) {
		FTPSERVER = ftpserver;
	}

	public static String getFTPUSERNAME() {
		return FTPUSERNAME;
	}

	public static void setFTPUSERNAME(String ftpusername) {
		FTPUSERNAME = ftpusername;
	}

	public static String getFTPPASSWORD() {
		return FTPPASSWORD;
	}

	public static void setFTPPASSWORD(String ftppassword) {
		FTPPASSWORD = ftppassword;
	}

	public static String getENVELOPERESULTROOT() {
		return ENVELOPERESULTROOT;
	}

	public static void setENVELOPERESULTROOT(String enveloperesultroot) {
		ENVELOPERESULTROOT = enveloperesultroot;
	}
	// public static void main(String args[]) {
	// new ReadConfigUpload();
	// }

}
