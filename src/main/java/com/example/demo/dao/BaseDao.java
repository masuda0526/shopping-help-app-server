package com.example.demo.dao;

import java.sql.Connection;
import java.sql.Timestamp;

public class BaseDao {

	DbCommon db = null;
	Connection con = null;
	
	public BaseDao() {
		db = new DbCommon();
		con = db.connectDB();
	}
	
	public Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	
}
