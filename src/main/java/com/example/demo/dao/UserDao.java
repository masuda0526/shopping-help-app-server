package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entity.EUser;

@Component
public class UserDao {
	
	DbCommon db = null;
	
	public UserDao() {
		db = new DbCommon();
	}
	
	public List<EUser> getUserList(){
		
		List<EUser> list = new ArrayList<>();
		String sql = "SELECT * FROM users";
		try {
			Connection con = db.connectDB();
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				EUser user = new EUser();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setTel(rs.getString("tel"));
				user.setUser_type(rs.getInt("user_type"));
				user.setCreated_at(rs.getTimestamp("created_at"));
				user.setCreated_at(rs.getTimestamp("updated_at"));
				user.setDelete_flg(rs.getBoolean("delete_flg"));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return list;
		
	}
	
	public EUser authCheck(String auth_info, String pass) {
		
		List<EUser> userList = this.getUserList();
		
		for(EUser user : userList) {
			if(user.getEmail().equals(auth_info) || user.getTel().equals(auth_info) ) {
				if(user.getPassword().equals(pass)) {
					return user;
				}
			}
		}
		return null;
		
	}
	
	public boolean signupUser(String name, String email, String password ,String tel, int user_type) {
		
		Timestamp created_at = new Timestamp(System.currentTimeMillis());
		try {
			String sql = "INSERT INTO users (name, email, password, tel, user_type, created_at) VALUES (?, ?, ?, ?, ?, ?)";
			Connection con = db.connectDB();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, email);
			pst.setString(3, password);
			pst.setString(4, tel);
			pst.setInt(5, user_type);
			pst.setTimestamp(6, created_at);
			int rs = pst.executeUpdate();
			if(rs <= 0) {
				return false;
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return true;
		
	}
	
	public boolean checkEmailDuplicate(String email) {
		String sql = "SELECT count(email) FROM users where email = ?";
		boolean rst = false;
		try {
			Connection con = db.connectDB();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, email);
			ResultSet rs = pst.executeQuery();
			rs.next();
			int intRs = rs.getInt(1);
			if(intRs > 0) {
				rst = true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return rst;
	}
	
	
	
}
