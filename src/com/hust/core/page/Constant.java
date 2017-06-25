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
		PRIVILEGE_MAP.put(PRIVILEGE_GRZY, "������ҳ");
		PRIVILEGE_MAP.put(PRIVILEGE_GRZL, "�������Ϲ���");
		PRIVILEGE_MAP.put(PRIVILEGE_ZXXX, "����ѧϰ");
		PRIVILEGE_MAP.put(PRIVILEGE_ZXLT, "������̳");
		PRIVILEGE_MAP.put(PRIVILEGE_XXCK, "��Ϣ�ֿ�");
		PRIVILEGE_MAP.put(PRIVILEGE_SPACE, "�ҵĿռ�");
	}
}
