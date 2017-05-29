package com.hust.crm.utils;

import java.math.BigInteger;
import java.security.MessageDigest;


public class MyStringUtils {
	
	/*	
	 * 获得MD5加密后的数据
	 * 
	 */
	public static String getMD5Value(String value){		
		try {
			MessageDigest messageDigest=MessageDigest.getInstance("MD5");
			byte[] md5ValueByteArray=messageDigest.digest(value.getBytes());
			//Arrays.toString(md5ValueByteArray);
			BigInteger bigInteger=new BigInteger(1,md5ValueByteArray);			
			return bigInteger.toString(16);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		//return null;
		
	}

}
