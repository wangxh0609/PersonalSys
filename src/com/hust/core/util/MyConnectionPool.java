package com.hust.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;


public class MyConnectionPool implements IConnectionPool{
	
	  // ���ӳ���������  
    private DBConfig dbConfig;  
    private boolean isActive = false; // ���ӳػ״̬  
    private volatile int  contActive = 0;// ��¼�������ܵ�������  
      
    // ��������  
    private List<Connection> freeConnection = new Vector<Connection>();  
    // �����  
    private List<Connection> activeConnection = new Vector<Connection>();  
    // ���̺߳����Ӱ󶨣���֤������ͳһִ��  
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();  
      
    private MyConnectionPool(DBConfig dbConfig) {  
        super();  
        this.dbConfig = dbConfig;  
        init();  
       // cheackPool();  
    }  
    // ��ʼ��  
    private void init() {  
        try {  
            Class.forName(dbConfig.getDriverName());  
            for (int i = 0; i < dbConfig.getInitConnections(); i++) {  
                Connection conn;  
                conn = newConnection();  
                // ��ʼ����С������  
                if (conn != null) {  
                    freeConnection.add(conn);  
                    contActive++;  
                }  
            }  
            isActive = true;  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        }  
    }  
    
    private synchronized  Connection newConnection(){
    	Connection conn = null;  
        if (dbConfig != null) {  
            try {
				Class.forName(dbConfig.getDriverName());
			
            conn = DriverManager.getConnection(dbConfig.getUrl(),  
            		dbConfig.getUserName(), dbConfig.getPassword());
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }  
        return conn;
    }

    
	@Override
	public Connection getConnection() {
		Connection conn = null;  
        try {  
        	synchronized(freeConnection){
	            // �ж��Ƿ񳬹��������������  
	            if(contActive < this.dbConfig.getMaxActiveConnections()){  
	                if (freeConnection.size() > 0) {  
	                    conn = freeConnection.get(0);  
	                    if (conn != null) {  
	                        threadLocal.set(conn);  
	                    }  
	                    freeConnection.remove(0);  
	                } else {  
	                    conn = newConnection();  
	                }  
	                  
	            }else{  
	                // �����������,ֱ�����»������  
	            	freeConnection.wait();  //this.dbConfig.getConnTimeOut()
	                conn = getConnection();  
	            }  
	            if (isValid(conn)) {  
	                activeConnection.add(conn);  
	                contActive ++;  
	            }  
        	}
        } catch(Exception e) {  
            e.printStackTrace();  
        } 
        return conn;  
	}
	 // �ж������Ƿ����  
    private boolean isValid(Connection conn) {  
        try {  
            if (conn == null || conn.isClosed()) {  
                return false;  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return true;  
    }  

	@Override
	public Connection getCurrentConnecton() {
		 // Ĭ���߳�����ȡ  
        Connection conn = threadLocal.get();  
        if(!isValid(conn)){  
            conn = getConnection();  
        }  
        return conn; 
	}

	@Override
	public void releaseConn(Connection conn) throws SQLException {
		synchronized(freeConnection){
			if (isValid(conn)&& !(freeConnection.size() > dbConfig.getMaxConnections())) {  
	            freeConnection.add(conn);  
	            activeConnection.remove(conn);  
	            contActive --;  
	            threadLocal.remove();  
	            // �������������ȴ����̣߳�ȥ������  
	            freeConnection.notifyAll();  
	        }  
		}
		
	}

	@Override
	public void destroy() {
		synchronized(freeConnection){
			for (Connection conn : freeConnection) {  
	            try {  
	                if (isValid(conn)) {  
	                    conn.close();  
	                }  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        for (Connection conn : activeConnection) {  
	            try {  
	                if (isValid(conn)) {  
	                    conn.close();  
	                }  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        isActive = false;  
	        contActive = 0;  	
		}
	}

	@Override
	public boolean isActive() {
		return isActive;  
	}

	@Override
	public void cheackPool() {
		 if(dbConfig.isCheakPool()){  
	            new Timer().schedule(new TimerTask() {
	            @Override  
	            public void run() {  
		            System.out.println("���߳���������"+freeConnection.size());  
		            System.out.println("�����������"+activeConnection.size());  
		            System.out.println("�ܵ���������"+contActive);  
	               }  
	            },dbConfig.getLazyCheck(),dbConfig.getPeriodCheck());  
	        }  
		
	}
	
	//����ģʽ
	public static MyConnectionPool GetPoolInstance(){  
	   return InstanceClass.poolInstance;
	}  
    private static class InstanceClass{
    	private static DBConfig dbconfig=null;
    	private static MyConnectionPool poolInstance=null;
		static{
			dbconfig=new DBConfig();
			Properties prop = new Properties();
			try {
				InputStream inStream = MyConnectionPool.class.getResourceAsStream("/jdbcInfo.properties");
			
				prop.load(inStream);
				dbconfig.setDriverName(prop.get("jdbc.driverClass").toString().trim());
				dbconfig.setUrl(prop.get("jdbc.jdbcUrl").toString().trim());
				dbconfig.setUserName(prop.get("jdbc.user").toString().trim());
				dbconfig.setPassword(prop.get("jdbc.password").toString().trim());
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			poolInstance = new MyConnectionPool(dbconfig); 
		}

		 
	}
		
}
