 package com.example.demo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.example.demo.entity.ELink;

@Component
public class LinkDao extends BaseDao{
	
	/**
	 * userlinkを登録します。
	 * @param myid　
	 * @param lp
	 * @return　登録完了でtrue、失敗でfalse;
	 */
	public boolean registUserLink(int myid, String lp) {
		int youid = getUserIdByLinkId(lp);
		int lid = checkInputPartner(myid, youid);
		boolean rt = false;
		if(!checkAlradyRegist(myid, youid)) {
			if(lid > 0) {
				rt = insertUser2(lid, myid);
			}else {
				rt = insertUser1(myid, youid);
			}
		}
		return rt;			
	}
	
	/**
	 * uid1に登録するメソッド
	 * @param uid1
	 * @param uid2
	 * @return　登録成功でtrue、失敗でfalse
	 */
	public boolean insertUser1(int uid1, int uid2) {
		Timestamp uid1_at = new Timestamp(System.currentTimeMillis());
		String sql = "INSERT INTO userlinks (uid1, uid1_at, uid2) VALUE (?,?,?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, uid1);
			pst.setTimestamp(2, uid1_at);
			pst.setInt(3,  uid2);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * uid2に登録するメソッド
	 * @param lid
	 * @param uid2
	 * @return　登録成功でtrue、失敗でfalse
	 */
	public boolean insertUser2(int lid, int uid2) {
		Timestamp uid2_at = new Timestamp(System.currentTimeMillis());
		String sql = "UPDATE userlinks SET uid2=?, uid2_at=?, ismatch=true, match_at = ? WHERE id=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, uid2);
			pst.setTimestamp(2, uid2_at);
			pst.setTimestamp(3, uid2_at);
			pst.setInt(4, lid);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * uid1に相手のユーザーIDが含まれるリストを返却
	 * @param youid　相手のユーザーID
	 * @return　パラメータに渡したユーザーIDに関連するリスト
	 */
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
	
	/**
	 * 相手が認証コードを入力しているかチェックするリスト
	 * @param myid
	 * @param youid
	 * @return　入力済ならIDを返却、未入力なら0
	 */
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
	
	/**
	 * 認証コードから相手のユーザーIDを返却
	 * @param lp　相手の認証コード
	 * @return　
	 */
	private int getUserIdByLinkId(String lp) {
		UserDao ud = new UserDao();
		return ud.returnUserIdByLinkPass(lp);
	}
	
	/**
	 * 認証コードから相手のユーザー名を返却
	 * @param lp　認証コード
	 * @param uid　ユーザーID
	 * @return　
	 */
	public String getUserNameByLinkId(String lp) {
		String rnm = "";
		try {
			if(Objects.isNull(rnm) || lp.length() != 6) {
				return rnm;
			}
			UserDao ud = new UserDao();
			int id = getUserIdByLinkId(lp);
			if(id > 0) {
				rnm = ud.getUserNameById(id);				
			}else {
				return rnm;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rnm;
	}
	
	/**
	 * ２つユーザーIDを含むユーザーリンクのリストを返却
	 * @param uid1
	 * @param uid2
	 * @return　ユーザーリンクのリスト
	 */
	public List<ELink> findListUid1OrUid2(int uid1, int uid2) {
		String sql = "SELECT * FROM userlinks WHERE uid1 = ? OR uid2 = ?";
		List<ELink> li = new ArrayList<ELink>();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, uid1);
			pst.setInt(2, uid2);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				ELink link = new ELink();
				link.setId(rs.getInt("id"));
				link.setUid1(rs.getInt("uid1"));
				link.setUid1_at(rs.getTimestamp("uid1_at"));
				link.setUid2(rs.getInt("uid2"));
				link.setUid2_at(rs.getTimestamp("uid2_at"));
				link.setIsmatch(rs.getBoolean("ismatch"));
				link.setMatch_at(rs.getTimestamp("match_at"));
				link.setDelete_flg(rs.getBoolean("delete_flg"));
				li.add(link);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return li;
	}
	
	/**
	 * 指定したユーザーIDを含むリンクリストを返却
	 * @param uid
	 * @return　ユーザーリンクのリスト
	 */
	public List<ELink> findListContainUid(int uid){
		return findListUid1OrUid2(uid, uid);
	}
	
	/**
	 * ユーザーID
	 * @param myId
	 * @param youId
	 * @return
	 */
	public boolean checkAlradyRegist(int myId, int youId) {
		List<ELink> li = findListContainUid(myId);
		if(Objects.nonNull(li) && !li.isEmpty()) {
			for(ELink link : li) {
				if(link.getUid1() == youId) {
					return true;
				}else if(link.getUid2() == youId) {
					if(Objects.nonNull(link.getUid2_at())) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
