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
	
	  // 连接池配置属性  
    private DBConfig dbConfig;  
    private boolean isActive = false; // 连接池活动状态  
    private volatile int  contActive = 0;// 记录创建的总的连接数  
      
    // 空闲连接  
    private List<Connection> freeConnection = new Vector<Connection>();  
    // 活动连接  
    private List<Connection> activeConnection = new Vector<Connection>();  
    // 将线程和连接绑定，保证事务能统一执行  
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();  
      
    private MyConnectionPool(DBConfig dbConfig) {  
        super();  
        this.dbConfig = dbConfig;  
        init();  
       // cheackPool();  
    }  
    // 初始化  
    private void init() {  
        try {  
            Class.forName(dbConfig.getDriverName());  
            for (int i = 0; i < dbConfig.getInitConnections(); i++) {  
                Connection conn;  
                conn = newConnection();  
                // 初始化最小连接数  
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
	            // 判断是否超过最大连接数限制  
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
	                // 继续获得连接,直到从新获得连接  
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
	 // 判断连接是否可用  
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
		 // 默认线程里面取  
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
	            // 唤醒所有正待等待的线程，去抢连接  
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
		            System.out.println("空线池连接数："+freeConnection.size());  
		            System.out.println("活动连接数：："+activeConnection.size());  
		            System.out.println("总的连接数："+contActive);  
	               }  
	            },dbConfig.getLazyCheck(),dbConfig.getPeriodCheck());  
	        }  
		
	}
	
	//单例模式
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
