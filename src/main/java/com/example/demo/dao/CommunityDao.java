package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CommunityDto;
import com.example.demo.entity.ECommunity;

@Component
public class CommunityDao {
	private final String JDBC_DRIVEWR="com.mysql.cj.jdbc.Driver";
	private final String URL="jdbc:mySQL://localhost/help_shopping_app";
	private final String USER="root";
	private final String PASS="Masu-Pitti_0118";
	
//	データベースへの接続
	private Connection connectDB(){
		Connection con = null;
		try {
			Class.forName(JDBC_DRIVEWR);
			con = DriverManager.getConnection(URL, USER, PASS);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public List<CommunityDto> getCommunityList(int user_id) {
		List<CommunityDto> list = new ArrayList<>();
		Connection con = connectDB();
		String sql = "SELECT r.id, r.community_id, c.community_name, r.user_id, u.name FROM community_relations AS r INNER JOIN users AS u ON r.user_id=u.id INNER JOIN communities AS c ON r.community_id=c.id WHERE community_id in (SELECT community_id FROM community_relations WHERE user_id = ?) ORDER BY r.community_id ASC";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, user_id);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				CommunityDto comDto = new CommunityDto();
				comDto.setId(rs.getInt("id"));
				comDto.setCommunity_id(rs.getInt("community_id"));
				comDto.setCommunity_name(rs.getString("community_name"));
				comDto.setUser_id(rs.getInt("user_id"));
				comDto.setName(rs.getString("name"));
				list.add(comDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<ECommunity> getAllCommunityList(){
		List<ECommunity> list = new ArrayList<>();
		Connection con = connectDB();
		String sql = "SELECT * FROM communities";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				ECommunity ecom = new ECommunity();
				ecom.setId(rs.getInt("id"));
				ecom.setCommunity_name(rs.getString("community_name"));
				ecom.setCreated_at(rs.getTimestamp("created_at"));
				ecom.setUpdated_at(rs.getTimestamp("updated_at"));
				ecom.setDelete_flg(rs.getBoolean("delete_flg"));
				list.add(ecom);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean joinCommunity(int community_id, int user_id) {
		Timestamp created_at = new Timestamp(System.currentTimeMillis());
		Connection con = connectDB();
		String sql = "INSERT INTO community_relations (community_id, user_id, created_at) VALUES ?,?,?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, community_id);
			pst.setInt(2, user_id);
			pst.setTimestamp(3, created_at);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String createUniqueCode() {
		return RandomStringUtils.random(6);
	}
	
}
