 package com.example.demo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entity.ELink;

@Component
public class LinkDao extends BaseDao{
	
	public boolean registUserLink(int myid, String lp) {
		int youid = getUserIdByLinkId(lp);
		int lid = checkInputPartner(myid, youid);
		boolean rt = false;
		if(lid > 0) {
			rt = insertUser2(lid, myid);
		}else {
			rt = insertUser1(myid, youid);
		}
		return rt;
	}
	
	public boolean insertUser1(int uid1, int uid2) {
		Timestamp uid1_at = new Timestamp(System.currentTimeMillis());
		String sql = "INSERT INTO userlinks (uid1, uid1_at, uid2) VALUE (?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, uid1);
			pst.setTimestamp(2, uid1_at);
			pst.setInt(2,  uid2);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertUser2(int lid, int uid2) {
		Timestamp uid2_at = new Timestamp(System.currentTimeMillis());
		String sql = "UPDATE userlinks SET uid2=?, uid2_at=? WHERE id=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, uid2);
			pst.setTimestamp(2, uid2_at);
			pst.setInt(3, lid);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<ELink> findListByPartnerIdAtUid1(int youid) {
		List<ELink> elL = new ArrayList<ELink>();
		String sql = "SELECT * FROM userlinks where uid1=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, youid);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				ELink el = new ELink();
				el.setId(rs.getInt("id"));
				el.setUid1(rs.getInt("uid1"));
				el.setUid1_at(rs.getTimestamp("uid1_at"));
				el.setUid2(rs.getInt("uid2"));
				el.setUid2_at(rs.getTimestamp("uid2_at"));
				el.setIsmatch(rs.getBoolean("ismatch"));
				el.setMatch_at(rs.getTimestamp("match_at"));
				elL.add(el);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elL;
	}
	
	public int checkInputPartner(int myid, int youid) {
		List<ELink> elL = findListByPartnerIdAtUid1(youid);
		if(elL != null && elL.size() > 0) {
			for(ELink el : elL) {
				if(el.getUid2() == myid) {
					return el.getId();
				}
			}
		}
		return 0;
	}
	
	private int getUserIdByLinkId(String lp) {
		UserDao ud = new UserDao();
		return ud.returnUserIdByLinkPass(lp);
	}
	
}
