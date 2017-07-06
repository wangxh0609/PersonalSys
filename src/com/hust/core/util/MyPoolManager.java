package com.hust.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 单例模式得到线程池
 * @author wanxh
 *
 */
public class MyPoolManager {
//	public static void main(String[] args){
//		MyPoolManager.GetPoolInstance();
//	}
	private MyPoolManager(){
	 
	}; 
	   
	public static MyConnectionPool GetPoolInstance(){  
	   return MyConnectionPool.GetPoolInstance();
	}  
  
}


