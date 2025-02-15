package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.example.demo.entity.EBuycode;

@Component
public class BuycodeDao extends BaseDao {
	
	/**
	 * 購入情報の全データの取得
	 * @return　購入情報のリスト
	 */
	public List<EBuycode> findAll(){
		List<EBuycode> l = new ArrayList<EBuycode>();
		String sql = "SELECT * FROM buycodes";
		Connection con =db.connectDB();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				EBuycode en = new EBuycode();
				en.setId(rs.getInt("id"));
				en.setBuycode(rs.getInt("buycode"));
				en.setBuycode(rs.getInt("seq"));
				en.setR_uid(rs.getInt("r_uid"));
				en.setB_uid(rs.getInt("b_uid"));
				en.setB_at(rs.getTimestamp("b_at"));
				en.setIsdelivery(rs.getBoolean("isdelicery"));
				en.setD_at(rs.getTimestamp("d_at"));
				en.setIsrecieve(rs.getBoolean("isrecieve"));
				en.setR_at(rs.getTimestamp("r_at"));
				en.setR_acp_at(rs.getTimestamp("r_acp_at"));
				en.setB_acp_at(rs.getTimestamp("b_acp_at"));
				l.add(en);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}
	
	/**
	 * 購入IDから購入データを取得します。
	 * @param buycode 購入ID
	 * @return 購入データ
	 */
	public EBuycode findByBuycode(int buycode){
		EBuycode en = new EBuycode();
		String sql = "SELECT * FROM buycodes where buycode=?";
		Connection con =db.connectDB();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				en.setBuycode(rs.getInt("buycode"));
				en.setR_uid(rs.getInt("r_uid"));
				en.setB_uid(rs.getInt("b_uid"));
				en.setB_at(rs.getTimestamp("b_at"));
				en.setIsdelivery(rs.getBoolean("isdelicery"));
				en.setD_at(rs.getTimestamp("d_at"));
				en.setIsrecieve(rs.getBoolean("isrecieve"));
				en.setR_at(rs.getTimestamp("r_at"));
				en.setR_acp_at(rs.getTimestamp("r_acp_at"));
				en.setB_acp_at(rs.getTimestamp("b_acp_at"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return en;
	}
	
	/**
	 * リクエストユーザーIDから購入情報を取得します。
	 * @param r_uid　リクエストユーザーID
	 * @return　購入データのリスト
	 */
	public List<EBuycode> findByRUser(int r_uid){
		List<EBuycode> l = new ArrayList<EBuycode>();
		String sql = "SELECT * FROM buycodes where r_uid=?";
		Connection con =db.connectDB();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, r_uid);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				EBuycode en = new EBuycode();
				en.setBuycode(rs.getInt("buycode"));
				en.setR_uid(rs.getInt("r_uid"));
				en.setB_uid(rs.getInt("b_uid"));
				en.setB_at(rs.getTimestamp("b_at"));
				en.setIsdelivery(rs.getBoolean("isdelicery"));
				en.setD_at(rs.getTimestamp("d_at"));
				en.setIsrecieve(rs.getBoolean("isrecieve"));
				en.setR_at(rs.getTimestamp("r_at"));
				en.setR_acp_at(rs.getTimestamp("r_acp_at"));
				en.setB_acp_at(rs.getTimestamp("b_acp_at"));
				l.add(en);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}
	
	/**
	 * 購入者IDから購入データを取得します
	 * @param b_uid　購入者のユーザーID
	 * @return　購入データのリスト
	 */
	public List<EBuycode> findByBUser(int b_uid){
		List<EBuycode> l = new ArrayList<EBuycode>();
		String sql = "SELECT * FROM buycodes where b_uid=?";
		Connection con =db.connectDB();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, b_uid);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				EBuycode en = new EBuycode();
				en.setBuycode(rs.getInt("buycode"));
				en.setR_uid(rs.getInt("r_uid"));
				en.setB_uid(rs.getInt("b_uid"));
				en.setB_at(rs.getTimestamp("b_at"));
				en.setIsdelivery(rs.getBoolean("isdelicery"));
				en.setD_at(rs.getTimestamp("d_at"));
				en.setIsrecieve(rs.getBoolean("isrecieve"));
				en.setR_at(rs.getTimestamp("r_at"));
				en.setR_acp_at(rs.getTimestamp("r_acp_at"));
				en.setB_acp_at(rs.getTimestamp("b_acp_at"));
				l.add(en);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}
	
	/**
	 * 購入者のユーザーIDとリクエストIDから購入データを登録します。
	 * @param buycode 購入データID
	 * @param seq seq
	 * @param r_uid リクエストユーザーID
	 * @param b_uid 購入者ユーザーID
	 * @return　登録完了でtrue、失敗でfalse
	 */
	public boolean insert(int buycode, int seq, int r_uid, int b_uid ) {
		Connection con = db.connectDB();
		Timestamp nd = new Timestamp(System.currentTimeMillis());
		String sql = "INSERT INTO buycodes (buycode, seq, r_uid, b_uid, b_at) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, buycode);
			pst.setInt(2, seq);
			pst.setInt(3, r_uid);
			pst.setInt(4, b_uid);
			pst.setTimestamp(5, nd);
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
	 * 購入データIDを自動作成し、購入者のユーザーIDとリクエストIDから購入データを登録します。
	 * @param seq
	 * @param r_uid
	 * @param b_uid
	 * @return　登録完了でtrue、失敗でfalse
	 */
	public boolean insert(int seq, int r_uid, int b_uid) {
		int buycode = createNewBuycode();
		return insert(buycode, seq, r_uid, b_uid);
	}
	
	/**
	 * 購入者のユーザーIDとリクエストIDから購入データを登録します。
	 * @param buycode
	 * @param seq
	 * @param r_uid
	 * @param b_uid
	 * @return 登録した購入データIDを返却。失敗は0を返却
	 */
	public int insertReturnBuycode(int buycode, int seq, int r_uid, int b_uid) {
		int i = 0;
		if(insert(buycode, seq, r_uid, b_uid)) {
			i = buycode;
		}
		return i;
	}
	
	/**
	 * 購入データIDを自動作成し、購入者のユーザーIDとリクエストIDから購入データを登録します。
	 * @param seq
	 * @param r_uid
	 * @param b_uid
	 * @return　登録した購入データIDを返却。失敗は0を返却
	 */
	public int insertReturnBuycode(int seq, int r_uid, int b_uid) {
		int buycode = createNewBuycode();
		return insertReturnBuycode(buycode, seq, r_uid, b_uid);
	}
	
	/**
	 * 購入データIDの最大値を取得します。
	 * @return　buycodeの最大値
	 */
	public int getMaxBuycode() {
		int ri = 0;
		String sql = "SELECT MAX(buycode) as maxbuycode FROM buycodes";
		Connection con = db.connectDB();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			rs.next();
			int n = rs.getInt("maxbuycode");
			if(n > 0) {
				ri = n;
			}else {
				ri = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ri;
		
	}
	
	/**
	 * 購入データIDを新規作成します。
	 * @return　新規作成したbuycode
	 */
	public int createNewBuycode() {
		int code = getMaxBuycode();
		if(code != 0) {
			return code + 1;
		}
		return code;
		
	}
	
	/**
	 * 複数の購入データを登録します。
	 * @param buycode　購入データID
	 * @param sr_uids　リクエストユーザーIDのリスト（String）
	 * @param b_uid　購入者ID
	 * @return　登録完了でtrue, 失敗でfalse
	 */
	public boolean insertList(int buycode, int[] r_uids, int b_uid) {
		boolean rflg = true;
		int seq = 1;
		if(Objects.nonNull(r_uids) && r_uids.length > 0) {
			for(int r_uid : r_uids) {
				boolean flg = insert(buycode, seq, r_uid, b_uid);
				seq++;
				if(!flg) {
					rflg = false;
				}
			}			
		}
		return rflg;
		
	}
	
	/**
	 * 複数の購入データを登録します
	 * @param buycode　購入データID
	 * @param sr_uids　リクエストユーザーIDのリスト（String）
	 * @param b_uid　購入者ID
	 * @return　登録成功でbuycodeを返却、失敗で0
	 */
	public int insertListReturnBuycode(int buycode, int[] r_uids, int b_uid) {
		boolean flg = false;
		flg = insertList(buycode, r_uids, b_uid);
		if(flg) {
			return buycode;
		}
		return 0;
	}
	
	/**
	 * buycodeを新規作成し、複数の購入データを登録します。
	 * @param sr_uids　リクエストユーザーIDのリスト（String）
	 * @param b_uid　購入者ID
	 * @return　登録完了でtrue, 失敗でfalse
	 */
	public boolean insertList(int[] r_uids, int b_uid) {
		int buycode = createNewBuycode();
		return insertList(buycode, r_uids, b_uid);
	}
	
	/**
	 * buycodeを新規作成し、複数の購入データを登録します。
	 * @param sr_uids　リクエストユーザーIDのリスト（String）
	 * @param b_uid　購入者ID
	 * @return　登録成功でbuycodeを返却、失敗で0
	 */
	public int insertListReturnBuycode(int[] r_uids, int b_uid) {
		int buycode = createNewBuycode();
		return insertListReturnBuycode(buycode, r_uids, b_uid);
	}
	
	
	public List<EBuycode> getBuycodesByUsers(int r_uid, int b_uid) {
		List<EBuycode> li = new ArrayList<EBuycode>();
		String sql = "SELECT buycode, seq FROM buycodes WHERE r_uid=? AND b_uid=? AND isdelivery = false";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, r_uid);
			pst.setInt(2, b_uid);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				EBuycode eb = new EBuycode();
				eb.setId(rs.getInt("id"));
				eb.setBuycode(rs.getInt("buycode"));
				eb.setSeq(rs.getInt("seq"));
				eb.setR_uid(rs.getInt("r_uid"));
				eb.setB_uid(rs.getInt("b_uid"));
				eb.setB_at(rs.getTimestamp("b_at"));
				eb.setIsdelivery(rs.getBoolean("isdelivery"));
				eb.setD_at(rs.getTimestamp("d_at"));
				eb.setIsrecieve(rs.getBoolean("isrecieve"));
				eb.setR_at(rs.getTimestamp("r_at"));
				eb.setR_acp_at(rs.getTimestamp("r_acp_at"));
				eb.setB_acp_at(rs.getTimestamp("b_acp_at"));
				li.add(eb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return li;
		
	}
	
	/**
	 * 該当する購入データを配達完了にします。
	 * @param buycode　購入データID
	 * @param seq　seq
	 * @return 配達完了でtrue、失敗でfalse
	 */
	public boolean compDeliveryUseBuycode(int buycode, int seq) {
		String sql = "UPDATE buycodes SET isdelivery=true, d_at=? WHERE buycode=? AND seq=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setTimestamp(1, getNowTimestamp());
			pst.setInt(2, buycode);
			pst.setInt(3, seq);
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
	 * 該当する購入データを配達完了にします。
	 * @param r_uid　リクエストユーザーID
	 * @param b_uid　購入ユーザーID
	 * @return　配達完了でtrue、失敗でfalse
	 */
	public boolean compDeliveryUseRequestUseId(int r_uid, int b_uid) {
		String sql = "UPDATE buycodes SET isdelivery=true, d_at=? WHERE r_uid=? AND b_uid=? AND isdelivery=false";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setTimestamp(1, getNowTimestamp());
			pst.setInt(2, r_uid);
			pst.setInt(3, b_uid);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				return  true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 最終確認日時を登録します。
	 * @param buycode　購入ID
	 * @param seq　seq
	 * @return　登録完了でtrue、失敗でfalse
	 */
	public boolean acceptDeliveryByBuycode(int buycode, int seq) {
		String sql = "UPDATE buycodes SET b_acp_at=? WHERE buycode=? AND seq=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setTimestamp(1, getNowTimestamp());
			pst.setInt(2, buycode);
			pst.setInt(3, seq);
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
	 * 最終確認日時を登録します。
	 * @param r_uid　リクエストユーザーID
	 * @param b_uid　購入ユーザーID
	 * @return　登録完了でtrue、失敗でfalse
	 */
	public boolean acceptDeliveryByRequestUserId(int r_uid, int b_uid) {
		String sql = "UPDATE buycodes SET b_acp_at=? WHERE r_uid=? AND b_uid=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setTimestamp(1, getNowTimestamp());
			pst.setInt(2, r_uid);
			pst.setInt(3, b_uid);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
}
