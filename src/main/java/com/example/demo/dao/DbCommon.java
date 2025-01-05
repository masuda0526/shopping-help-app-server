package com.example.demo.dao;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.example.demo.common.StartupConstant;

public class DbCommon {

//	データベースへの接続
	public Connection connectDB() {
		Connection con = null;
		try {
			Properties dbProp = readProperties();
			Class.forName(dbProp.getProperty("JDBC_DRIVEWR"));
			con = DriverManager.getConnection(dbProp.getProperty("URL"), dbProp.getProperty("USER"), dbProp.getProperty("PASS"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	private Properties readProperties()  throws Exception {
		File propFile = new File(StartupConstant.DB_PROP_FILE);
		Properties prop = new Properties();
		prop.load(new FileReader(propFile));
		return prop;
	}

}
