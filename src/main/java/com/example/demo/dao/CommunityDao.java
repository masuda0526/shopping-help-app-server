package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.example.demo.dto.CommunityDto;
import com.example.demo.entity.ECommunity;

@Component
public class CommunityDao {
	DbCommon db = null;
	// コミュニティー認証用番号の桁数
	int CODE_LENGTH = 6;
	
	public CommunityDao() {
		db = new DbCommon();
	}

	public List<CommunityDto> getCommunityList(int user_id) {
		List<CommunityDto> list = new ArrayList<>();
		Connection con = db.connectDB();
		String sql = "SELECT r.id, r.community_id, c.community_name, c.com_expl, c.com_auth_id, r.user_id, u.name FROM community_relations AS r INNER JOIN users AS u ON r.user_id=u.id INNER JOIN communities AS c ON r.community_id=c.id WHERE community_id in (SELECT community_id FROM community_relations WHERE user_id = ?) ORDER BY r.community_id ASC";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, user_id);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				CommunityDto comDto = new CommunityDto();
				comDto.setId(rs.getInt("id"));
				comDto.setCommunity_id(rs.getInt("community_id"));
				comDto.setCommunity_name(rs.getString("community_name"));
				comDto.setCom_expl(rs.getString("com_expl"));
				comDto.setCom_auth_id(rs.getString("com_auth_id"));
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
		Connection con = db.connectDB();
		String sql = "SELECT * FROM communities";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				ECommunity ecom = new ECommunity();
				ecom.setId(rs.getInt("id"));
				ecom.setCommunity_name(rs.getString("community_name"));
				ecom.setCom_auth_id(rs.getString("com_auth_id"));
				ecom.setCom_expl(rs.getString("com_expl"));
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
		Connection con = db.connectDB();
		String sql = "INSERT INTO community_relations (community_id, user_id, created_at) VALUES (?,?,?)";
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
	
	public boolean createCommunity(String community_name, String com_expl) {
		Timestamp created_at = new Timestamp(System.currentTimeMillis());
		Connection con = db.connectDB();
		String sql = "INSERT INTO communities (community_name, com_auth_id, com_expl, created_at) VALUES (?,?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, community_name);
			pst.setString(2, createUniqueCode());
			pst.setString(3, com_expl);
			pst.setTimestamp(4, created_at);
			int rs = pst.executeUpdate();
//			int rs = 0;
			if(rs > 0) {
				return true;
			}
			return false;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String createUniqueCode() {
		StringBuilder code = new StringBuilder();
		Random rand = new Random();
		for(int i = 0; i < this.CODE_LENGTH; i++) {
			code.append(rand.nextInt(10));			
		}
		
		return code.toString();
	}
	
}
