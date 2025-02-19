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

import com.example.demo.entity.EUser;

@Component
public class UserDao extends BaseDao {
	
	private int LINKPASS_LEN = 6;
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
				user.setLinkpass(rs.getString("linkpass"));
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
	
	public EUser findUserByLinkPass(String lp) {
		EUser user = new EUser();
		String sql = "SELECT * FROM users WHERE linkpass=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, lp);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setTel(rs.getString("tel"));
				user.setUser_type(rs.getInt("user_type"));
				user.setLinkpass(rs.getString("linkpass"));
				user.setCreated_at(rs.getTimestamp("created_at"));
				user.setCreated_at(rs.getTimestamp("updated_at"));
				user.setDelete_flg(rs.getBoolean("delete_flg"));				
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 認証コードからユーザーIDを返却
	 * @param lp　認証コード
	 * @return
	 */
	public int returnUserIdByLinkPass(String lp) {
		EUser eu = findUserByLinkPass(lp);
		return eu.getId();
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
		String lp = returnLinkPass();
		try {
			String sql = "INSERT INTO users (name, email, password, tel, user_type, linkpass, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
			Connection con = db.connectDB();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, email);
			pst.setString(3, password);
			pst.setString(4, tel);
			pst.setInt(5, user_type);
			pst.setString(6, lp);
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
	
	/**
	 * emailの重複チェックをする
	 * @param email　メールアドレスのString
	 * @return　重複があればtrue、なければfalse
	 */
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
	
	/**
	 * 重複のないlinkpassを返却する
	 * @return　linkpassのString
	 */
	private String returnLinkPass() {
		String linkpass = "";
		boolean isDup = true;
		while(!isDup) {
			linkpass = createLinkPass();
			isDup = checkLinkPassDuplicate(linkpass);
		}
		return linkpass;
	}
	
	/**
	 * linkpassを作成する
	 * @return　linkpassのString
	 */
	private String createLinkPass() {
		String pass = "";
		Random rm = new Random();
		for(int i = 0; i < LINKPASS_LEN; i++) {
			Integer r = rm.nextInt(10);
			pass += r.toString();
		}
		return pass;
	}
	
	/**
	 * linkpassが重複しているかどうか確認
	 * @param str　linkpassのString
	 * @return　重複ありならtrue、なしならfalse
	 */
	private boolean checkLinkPassDuplicate(String str) {
		int cnt = 0;
		String sql = "SELECT linkpass FROM users WHERE linkpass=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, str);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				cnt++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(cnt > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * ユーザーIDからユーザーネームを取得する
	 * @param uid　ユーザーID
	 * @return　ユーザーネーム
	 */
	public String getUserNameById(int uid) {
		String name = "";
		String sql = "SELECT name FROM users WHERE id = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, uid);
			ResultSet rs = pst.executeQuery();
			rs.next();
			name = rs.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}
	
}
