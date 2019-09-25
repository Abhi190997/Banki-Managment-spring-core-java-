package com.abiBanking.bankMangment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;


public class Dao {
    
    private String url;
    private String user;
    private String pass;
	private String driver;
	 
    public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
  
    Connection con;
     
    
    public Connection getCon() throws ClassNotFoundException, SQLException {
    	
    	Class.forName(driver);
    	con = DriverManager.getConnection(url,user,pass);
    	return con;
    }
    
}


