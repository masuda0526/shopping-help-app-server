package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
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
	
	private final String JDBC_DRIVEWR="com.mysql.cj.jdbc.Driver";
	private final String URL="jdbc:mySQL://localhost/help_shopping_app";
	private final String USER="root";
	private final String PASS="Masu-Pitti_0118";
	
	
	private Connection connectDB() {
		Connection con = null;
		try {
			Class.forName(JDBC_DRIVEWR);
			con = DriverManager.getConnection(URL, USER, PASS);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public List<EUser> getUserList(){
		
		List<EUser> list = new ArrayList<>();
		String sql = "SELECT * FROM users";
		try {
			Connection con = connectDB();
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				EUser user = new EUser();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setTel(rs.getString("tel"));
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
	
	public boolean signupUser(String name, String email, String password ,String tel) {
		
		Timestamp created_at = new Timestamp(System.currentTimeMillis());
		try {
			String sql = "INSERT INTO users (name, email, password, tel, created_at) VALUES (?, ?, ?, ?, ?)";
			Connection con = connectDB();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, email);
			pst.setString(3, password);
			pst.setString(4, tel);
			pst.setTimestamp(5, created_at);
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
	
	
	
	
}
