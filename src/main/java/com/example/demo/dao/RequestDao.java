package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.dto.RequestDto;
import com.example.demo.entity.ERequest;

@Component
public class RequestDao {
	
	private final String JDBC_DRIVEWR="com.mysql.cj.jdbc.Driver";
	private final String URL="jdbc:mySQL://localhost/help_shopping_app";
	private final String USER="root";
	private final String PASS="Masu-Pitti_0118";
	
//	データベースへの接続
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
//	全リクエストを取得
	public List<ERequest> getAllRequests(){
		List<ERequest> list = new ArrayList<>();
		Connection con = connectDB();		
		String sql = "SELECT * FROM requests";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				ERequest req = new ERequest();
				req.setId(rs.getInt("id"));
				req.setProduct_name(rs.getString("product_name"));
				req.setRequest_user_id(rs.getInt("request_user_id"));
				req.setIsbuy(rs.getBoolean("isbuy"));
				req.setBuy_user_id(rs.getInt("buy_user_id"));
				req.setCreated_at(rs.getTimestamp("created_at"));
				req.setUpdated_at(rs.getTimestamp("updated_at"));
				req.setDelete_flg(rs.getBoolean("delete_flg"));
				list.add(req);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(con != null)con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
//	関係するコミュニティー全員のリクエストを取得
	public List<RequestDto> getComunityRequests(int id){
		
		List<RequestDto> list = new ArrayList<>();
		Connection con = connectDB();		
//		String sql = "SELECT * FROM requests WHERE request_user_id IN (SELECT user_id FROM community_relations WHERE community_id IN (SELECT community_id FROM community_relations WHERE user_id=?));";
		String sql = "select r.id,r.product_name,r.request_user_id,u.name,r.created_at,r.isbuy,r.delete_flg,r.buy_user_id, r.inCart, r.inCart_user_id from requests as r left outer join users as u on r.request_user_id = u.id where request_user_id in (select user_id from community_relations where community_id in (select community_id from community_relations where user_id=?)) AND r.delete_flg = false AND r.isbuy=false";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				RequestDto req = new RequestDto();
				req.setId(rs.getInt("id"));
				req.setProduct_name(rs.getString("product_name"));
				req.setRequest_user_id(rs.getInt("request_user_id"));
				req.setName(rs.getString("name"));
				req.setIsbuy(rs.getBoolean("isbuy"));
				req.setBuy_user_id(rs.getInt("buy_user_id"));
				req.setCreated_at(rs.getTimestamp("created_at"));
				req.setDelete_flg(rs.getBoolean("delete_flg"));
				req.setInCart(rs.getBoolean("inCart"));
				req.setInCart_user_id(rs.getInt("inCart_user_id"));
				list.add(req);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(con != null)con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
			
	}
	
	public boolean addRequest(String product_name, int request_user_id) {
		Connection con = connectDB();
		boolean result = false;
		Timestamp created_at = new Timestamp(System.currentTimeMillis());
		String sql = "INSERT INTO requests (product_name, request_user_id, created_at) values (?, ?, ?)";
		try{
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, product_name);
			pst.setInt(2, request_user_id);
			pst.setTimestamp(3, created_at);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				result = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean toggleInCart(int request_id, int inCart_user_id, boolean bool) {
		Connection con = connectDB();
		boolean result = false;
		
		Timestamp updated_at = new Timestamp(System.currentTimeMillis());
		String sql = "UPDATE requests SET inCart=?, inCart_user_id=?, updated_at=? WHERE id = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setBoolean(1, bool);
			pst.setInt(2, inCart_user_id);
			pst.setTimestamp(3, updated_at);
			pst.setInt(4, request_id);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				result = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	public boolean compShopping(int buy_user_id) {
		Connection con = connectDB();
		boolean result = false;
		Timestamp updated_at = new Timestamp(System.currentTimeMillis());
		String sql = "UPDATE requests SET isbuy=true, updated_at=?, buy_user_id=? where inCart=true and inCart_user_id=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setTimestamp(1, updated_at);
			pst.setInt(2, buy_user_id);
			pst.setInt(3, buy_user_id);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				result = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
}
	
