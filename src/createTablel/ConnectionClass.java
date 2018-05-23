package createTablel;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionClass {
	
	String driverName;
	String url;
	String userName;
	String password;
	
	public ConnectionClass() throws IOException{
		Properties props = new Properties();
		FileInputStream in = new FileInputStream("database.properties");
		props.load(in);
		in.close();
		
		driverName = props.getProperty("jdbc.drivers");
		if(driverName != null) System.setProperty("jdbc.drivers", driverName);
		url = props.getProperty("jdbc.url");
		userName = props.getProperty("jdbc.username");
		password = props.getProperty("jdbc.password");
	}
	 
	 public Connection getConnection(){		 
		 Connection conn = null;		 
		 try {
			 Class.forName(driverName);
		 } catch (ClassNotFoundException e) {
			 System.out.println("No driver.");
			 e.printStackTrace();
		 }
		try {
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			System.out.println("Connection failed.");
			e.getMessage();
		}
		if (conn != null) {
			System.out.println("Connection successful.");
		} else {
			System.out.println("URL, or userName or Password error!");
		}		
		return conn;
	}
	 
	 public void closeConnection(Connection conn){
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Error while closing connection!");
					e.printStackTrace();
				}
			}
		}
	 }
