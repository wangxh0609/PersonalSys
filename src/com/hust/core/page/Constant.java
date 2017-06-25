package com.hust.core.page;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	
	//public static String USER = "SYS_USER";

	public static String PRIVILEGE_GRZY = "grzy"; 
	public static String PRIVILEGE_GRZL = "grzl"; 
	public static String PRIVILEGE_ZXXX = "zxxx"; 
	public static String PRIVILEGE_ZXLT = "zxlt"; 
	public static String PRIVILEGE_XXCK = "xxck"; 
	public static String PRIVILEGE_SPACE = "spaces"; 

	public static Map<String, String> PRIVILEGE_MAP;
	static {		
		PRIVILEGE_MAP = new HashMap<String, String>();
		PRIVILEGE_MAP.put(PRIVILEGE_GRZY, "个人主页");
		PRIVILEGE_MAP.put(PRIVILEGE_GRZL, "个人资料管理");
		PRIVILEGE_MAP.put(PRIVILEGE_ZXXX, "在线学习");
		PRIVILEGE_MAP.put(PRIVILEGE_ZXLT, "在线论坛");
		PRIVILEGE_MAP.put(PRIVILEGE_XXCK, "信息仓库");
		PRIVILEGE_MAP.put(PRIVILEGE_SPACE, "我的空间");
	}
}
