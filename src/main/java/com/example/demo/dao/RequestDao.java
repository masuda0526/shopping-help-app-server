package com.example.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.example.demo.dto.MypageCommunityRequestListDto;
import com.example.demo.dto.MypageDto;
import com.example.demo.dto.RequestDto;
import com.example.demo.dto.RequestsDtoPerBuycode;
import com.example.demo.dto.RequestsDtoPerUser;
import com.example.demo.dto.RequestsJoinBuycode;
import com.example.demo.dto.UnitDto;
import com.example.demo.entity.ERequest;

@Component
public class RequestDao extends BaseDao {
	
	DbCommon db = null;
	
	public RequestDao() {
		db = new DbCommon();
	}

	/**
	 * 全リクエストをリストで取得
	 * @return リクエストのリスト
	 */
	public List<ERequest> getAllRequests(){
		List<ERequest> list = new ArrayList<>();
		Connection con = db.connectDB();
		String sql = "SELECT * FROM requests";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				ERequest req = new ERequest();
				req.setId(rs.getInt("id"));
				req.setProduct_name(rs.getString("product_name"));
				req.setVol(rs.getInt("vol"));
				req.setUnit(rs.getString("unit"));
				req.setRequest_user_id(rs.getInt("request_user_id"));
				req.setInCart(rs.getBoolean("inCart"));
				req.setInCart_user_id(rs.getInt("inCart_user_id"));
				req.setBuycode(rs.getInt("buycode"));
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
		Connection con = db.connectDB();
//		String sql = "SELECT * FROM requests WHERE request_user_id IN (SELECT user_id FROM community_relations WHERE community_id IN (SELECT community_id FROM community_relations WHERE user_id=?));";
		String sql = "select r.id, r.product_name, r.vol, r.unit, r.request_user_id, u.name, r.inCart, r.inCart_user_id, r.buycode, r.created_at, r.updated_at, r.delete_flg from requests as r left outer join users as u on r.request_user_id = u.id where request_user_id in (select user_id from community_relations where community_id in (select community_id from community_relations where user_id=?)) AND r.delete_flg = false";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				RequestDto req = new RequestDto();
				req.setId(rs.getInt("id"));
				req.setProduct_name(rs.getString("product_name"));
				req.setVol(rs.getInt("vol"));
				req.setUnit(rs.getString("unit"));
				req.setRequest_user_id(rs.getInt("request_user_id"));
				req.setName(rs.getString("name"));
				req.setInCart(rs.getBoolean("inCart"));
				req.setInCart_user_id(rs.getInt("inCart_user_id"));
				req.setBuycode(rs.getInt("buycode"));
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
	
	/**
	 * リクエストを追加
	 * @param product_name 商品名
	 * @param vol 数量
	 * @param unit 単位
	 * @param request_user_id 購入者ID
	 * @return 登録完了でtrue、失敗でfalse
	 */
	public boolean addRequest(String product_name, int vol, String unit,  int request_user_id) {
		Connection con = db.connectDB();
		boolean result = false;
		Timestamp created_at = new Timestamp(System.currentTimeMillis());
		String sql = "INSERT INTO requests (product_name, vol, unit, request_user_id, created_at) values (?, ?, ?, ?, ?)";
		try{
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, product_name);
			pst.setInt(2, vol);
			pst.setString(3, unit);
			pst.setInt(4, request_user_id);
			pst.setTimestamp(5, created_at);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				result = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * カートへの追加状態を切り替える
	 * @param request_id リクエストID
	 * @param inCart_user_id 追加したユーザーID
	 * @param bool true カートへ追加 false カートから削除
	 * @return 登録完了でtrue、失敗でfalse
	 */
	public boolean toggleInCart(int request_id, int inCart_user_id, boolean bool) {
		Connection con = db.connectDB();
		boolean result = false;
		String sql = "";
		
		Timestamp updated_at = new Timestamp(System.currentTimeMillis());
		if(bool) {
			sql = "UPDATE requests SET inCart=?, inCart_user_id=?, updated_at=? WHERE id = ?";			
		}else {
			sql = "UPDATE requests SET inCart=?, inCart_user_id=null, updated_at=? WHERE id = ?";
		}
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			if(bool) {
				pst.setBoolean(1, bool);
				pst.setInt(2, inCart_user_id);
				pst.setTimestamp(3, updated_at);
				pst.setInt(4, request_id);				
			}else {
				pst.setBoolean(1, bool);
				pst.setTimestamp(2, updated_at);
				pst.setInt(3, request_id);
			}
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
		boolean result = false;
		Timestamp updated_at = new Timestamp(System.currentTimeMillis());
		String sql = "UPDATE requests SET isbuy=true, updated_at=?, buy_user_id=?, buy_at = ? where inCart=true and inCart_user_id=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setTimestamp(1, updated_at);
			pst.setInt(2, buy_user_id);
			pst.setTimestamp(3, updated_at);
			pst.setInt(4, buy_user_id);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				result = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean compShopping(int[] r_uids, int buy_user_id) {
		BuycodeDao buycodeDao = new BuycodeDao();
		Timestamp updated_at = new Timestamp(System.currentTimeMillis());
		int buycode = buycodeDao.insertListReturnBuycode(r_uids, buy_user_id);
		String sql = "UPDATE requests SET buycode=?, updated_at=? WHERE inCart_user_id=? AND delete_flg=false AND buycode is NULL";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, buycode);
			pst.setTimestamp(2, updated_at);
			pst.setInt(3, buy_user_id);
			int rs = pst.executeUpdate();
			if(rs > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean compDelivery(int request_user_id, int buy_user_id) {
		Connection con = db.connectDB();
		boolean result = false;
		Timestamp updated_at = new Timestamp(System.currentTimeMillis());
		String sql = "UPDATE requests SET isDelivery=true, delivery_at=?, updated_at=? where isbuy=true AND buy_user_id=? AND request_user_id=? AND isDelivery=null";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setTimestamp(1, updated_at);
			pst.setTimestamp(2, updated_at);
			pst.setInt(3, buy_user_id);
			pst.setInt(4, request_user_id);
			int rs = pst.executeUpdate();
			if(rs>0) {
				result=true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	public List<MypageCommunityRequestListDto> getRequestForMypage(int user_id){
		List<RequestDto> reqList = getComunityRequests(user_id);
		List<MypageCommunityRequestListDto> mypageList = new ArrayList<>();
		for(RequestDto reqItem : reqList) {
			Integer listIndex = isContainRequest(reqItem, mypageList);
//			List<ERequest> eReqs = new ArrayList<>();
			if(listIndex == null) {
				MypageCommunityRequestListDto mypageDto = new MypageCommunityRequestListDto();
				mypageDto.setUser_name(reqItem.getName());
				mypageDto.setUser_id(reqItem.getRequest_user_id());
//				eReqs.add(reqItem.castERequest());
				mypageDto.addRequests(reqItem.castERequest());
				mypageList.add(mypageDto);
			}else {
				mypageList.get(listIndex).addRequests(reqItem.castERequest());
			}
		}
		
		return mypageList;
		
	}
	
	/**
	 * パラーメータで受け取ったリクエストが、リストの中に含まれているか
	 * @param item　リクエスト
	 * @param mypageList　含まれているかどうか確認したいリクエストリスト
	 * @return　含まれている場合は、該当するindex番号を返却、含まれていなければnull
	 */
	private Integer isContainRequest(RequestDto item, List<MypageCommunityRequestListDto> mypageList) {
		Integer result = null;
		if(!mypageList.isEmpty()) {
			for(int i = 0; i < mypageList.size(); i++){
				if(item.getRequest_user_id() == mypageList.get(i).getUser_id()) {
					result = i;
					return result;
				}
			}			
		}
		return result;
	}
	
	/**
	 *パラーメータで受け取ったリクエストが、リストの中に含まれているか
	 * @param item　リクエスト
	 * @param mypageList　含まれているかどうか確認したいリクエストリスト
	 * @return　含まれている場合は、該当するindex番号を返却、含まれていなければnull
	 */
	private Integer isContainRequest(RequestsJoinBuycode item, List<RequestsDtoPerUser> li) {
		Integer result = null;
		if(!li.isEmpty()) {
			for(int i = 0; i < li.size(); i++) {
				if(item.getRequest_user_id() == li.get(i).getUser_id()) {
					result = i;
					return result;
				}
			}
		}
		return result;
	}
	
	private Integer isContainRequestByBuycode(RequestsJoinBuycode item, List<RequestsDtoPerBuycode> li) {
		Integer result = null;
		if(!li.isEmpty()) {
			for(int i = 0; i < li.size(); i++) {
				if(item.getBuycode() == li.get(i).getBuycode()) {
					result = i;
					return result;
				}
			}
		}
		return result;
	}
	
	
	/**
	 * ユーザーIDと関係があるユーザーのrequestsテーブルとbuycodeテーブルを組み合わせたデータを取得します。（購入済・未購入・カートのみを区別せずに取得（削除済のものは除く)）
	 * @param uid
	 * @return リクエストのリスト
	 */
	public List<RequestsJoinBuycode> findAllRequestsPerUser(int uid) {
		List<RequestsJoinBuycode> li = new ArrayList<RequestsJoinBuycode>();
		String sql = "SELECT r.id, r.product_name, r.vol, r.unit, r.request_user_id, u.name, r.inCart, r.inCart_user_id, r.buycode, b.seq, r.created_at, r.updated_at, r.delete_flg, b.r_uid, b.b_uid, b.b_at, b.isdelivery, b.d_at, b.isrecieve, b.r_at, b.r_acp_at, b.b_acp_at FROM requests AS r LEFT JOIN buycodes AS b ON r.buycode = b.buycode AND r.request_user_id = b.r_uid LEFT JOIN users AS u ON r.request_user_id = u.id WHERE r.delete_flg = false AND (request_user_id IN (SELECT uid1 FROM userlinks WHERE uid2=? AND ismatch=true) OR request_user_id IN (SELECT uid2 FROM userlinks WHERE uid1=? AND ismatch=true))";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, uid);
			pst.setInt(2, uid);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				RequestsJoinBuycode r = new RequestsJoinBuycode();
				r.setId(rs.getInt("id"));
				r.setProduct_name(rs.getString("product_name"));
				r.setVol(rs.getInt("vol"));
				r.setUnit(rs.getString("unit"));
				r.setRequest_user_id(rs.getInt("request_user_id"));
				r.setUser_name(rs.getString("name"));
				r.setInCart(rs.getBoolean("inCart"));
				r.setInCart_user_id(rs.getInt("inCart_user_id"));
				r.setBuycode(rs.getInt("buycode"));
				r.setSeq(rs.getInt("seq"));
				r.setCreated_at(rs.getTimestamp("created_at"));
				r.setUpdated_at(rs.getTimestamp("updated_at"));
				r.setDelete_flg(rs.getBoolean("delete_flg"));
				r.setR_uid(rs.getInt("r_uid"));
				r.setB_uid(rs.getInt("b_uid"));
				r.setB_at(rs.getTimestamp("b_at"));
				r.setIsdelivery(rs.getBoolean("isdelivery"));
				r.setD_at(rs.getTimestamp("d_at"));
				r.setIsrecieve(rs.getBoolean("isrecieve"));
				r.setR_at(rs.getTimestamp("r_at"));
				r.setR_acp_at(rs.getTimestamp("r_acp_at"));
				r.setB_acp_at(rs.getTimestamp("b_acp_at"));
				li.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return li;
	}
	
	/**
	 * リクエストリストのうち、購入済みのリクエストのリストを返却
	 * @param li buycodesテーブルを組み合わせたリクエストのリスト
	 * @return　購入済みのリクエストリスト
	 */
	public List<RequestsJoinBuycode> findPurchasedRequestsByUser(List<RequestsJoinBuycode> li){
		List<RequestsJoinBuycode> rli = new ArrayList<RequestsJoinBuycode>();
		if(li != null && li.size() > 0) {
			for(RequestsJoinBuycode item : li) {
				if(Objects.nonNull(item.getBuycode())) {
					if(item.getBuycode() > 0) {
						rli.add(item);
					}
				}
			}
		}
		return rli;
	}
	
	/**
	 * リクエストリストのうちパラメータで受け取ったIDのユーザが購入したリクエストのリストを返却
	 * @param li buycodesテーブルを組み合わせたリクエストのリスト
	 * @param uid 購入者のID
	 * @return 購入済みのリクエストリスト
	 */
	public List<RequestsJoinBuycode> findMyPurchasedRequestsByUser(List<RequestsJoinBuycode> li, int uid){
		List<RequestsJoinBuycode> rli = new ArrayList<RequestsJoinBuycode>();
		if(li != null && li.size() > 0) {
			for(RequestsJoinBuycode item : li) {
				if(Objects.nonNull(item.getBuycode())) {
					if(item.getBuycode() > 0 && item.getB_uid() == uid) {
						rli.add(item);
					}
				}
			}
		}
		return rli;
	}
	
	/**
	 * リクエストリストのうち、未購入のリストを返却
	 * @param li buycodesテーブルを組み合わせたリクエストのリスト
	 * @return 未購入のリクエストリスト
	 */
	public List<RequestsJoinBuycode> findNoPurchasedRequestsByUser(List<RequestsJoinBuycode> li){
		List<RequestsJoinBuycode> rli = new ArrayList<RequestsJoinBuycode>();
		if(li != null && li.size() > 0) {
			for(RequestsJoinBuycode item : li) {
				if(Objects.isNull(item.getBuycode()) || item.getBuycode() == 0) {
					rli.add(item);
				}
			}
		}
		return rli;
	}
	
	/**
	 * パラーメータで受け取ったリクエストをユーザーごとに振り分けたリストを返却
	 * @param li buycodesテーブルを組み合わせたリクエストのリスト
	 * @return ユーザーごとのリクエスト一覧
	 */
	public List<RequestsDtoPerUser> convertRequestsListPerUser(List<RequestsJoinBuycode> li){
		List<RequestsDtoPerUser> rli = new ArrayList<RequestsDtoPerUser>();
		if(!li.isEmpty() && li.size() > 0) {
			for(RequestsJoinBuycode item : li) {
				Integer i = isContainRequest(item, rli);
				if(Objects.nonNull(i)) {
					rli.get(i).addRequests(item);
				}else {
					RequestsDtoPerUser dto = new RequestsDtoPerUser();
					dto.setUser_id(item.getRequest_user_id());
					dto.setUser_name(item.getUser_name());
					dto.addRequests(item);
					rli.add(dto);
				}
			}
		}
		return rli;
	}
	
	/**
	 * パラメータで受け取ったリクエストを購入IDごとに振り分けたリストを返却
	 * @param li　buycodesテーブルを組み合わせたリクエストのリスト
	 * @return　購入IDごとのリクエスト一覧
	 */
	public List<RequestsDtoPerBuycode> convertRequestsListPerBuycode(List<RequestsJoinBuycode> li){
		List<RequestsDtoPerBuycode> rli = new ArrayList<RequestsDtoPerBuycode>();
		if(!li.isEmpty() && li.size() > 0) {
			for(RequestsJoinBuycode item : li) {
				Integer i = isContainRequestByBuycode(item, rli);
				if(Objects.nonNull(i)) {
					rli.get(i).addRequests(item);
				}else {
					RequestsDtoPerBuycode dto = new RequestsDtoPerBuycode();
					dto.setUser_id(item.getRequest_user_id());
					dto.setUser_name(item.getUser_name());
					dto.setBuycode(item.getBuycode());
					dto.setSeq(item.getSeq());
					dto.addRequests(item);
					rli.add(dto);
				}
			}
		}
		return rli;
	}
	
	/**
	 * 買い物用画面のリストを返却します。
	 * @param user_id　ユーザーID
	 * @return　リスト
	 */
	public List<RequestsDtoPerUser> findRequestListPerUserByLinkUserForStartShopping(int user_id){
		List<RequestsJoinBuycode> allL = findAllRequestsPerUser(user_id);
		List<RequestsJoinBuycode> selectList = new ArrayList<RequestsJoinBuycode>();
		if(!allL.isEmpty()) {
			for(RequestsJoinBuycode item : allL) {
				if(item.getBuycode() == 0 && (item.getInCart_user_id() == user_id || item.getInCart_user_id() == 0)) {
					selectList.add(item);
				}
			}
		}
		List<RequestsDtoPerUser> rL = convertRequestsListPerUser(selectList);
		return rL;
	}
	
	/**
	 * マイページ用の購入リスト・未購入リストを返却します。
	 * @param user_id　ユーザーID
	 * @return　購入リスト・未購入リスト
	 */
	public MypageDto findRequestListPerUserByLinkUserForMypage(int user_id){
		List<RequestsJoinBuycode> allL = findAllRequestsPerUser(user_id);
		List<RequestsJoinBuycode> purchasedList = new ArrayList<RequestsJoinBuycode>();
		List<RequestsJoinBuycode> nonpurchasedList = new ArrayList<RequestsJoinBuycode>();
		if(!allL.isEmpty()) {
			for(RequestsJoinBuycode item : allL) {
				if(item.getBuycode() != 0 && !item.isIsdelivery() && item.getInCart_user_id() == user_id) {
					purchasedList.add(item);
				}else if(item.getBuycode() == 0 && !item.isIsdelivery()) {
					nonpurchasedList.add(item);
				}
			}
		}
		MypageDto mpdto = new MypageDto();
		mpdto.addPurchasedList(convertRequestsListPerUser(purchasedList));
		mpdto.addNonPurchasedList(convertRequestsListPerUser(nonpurchasedList));
		return mpdto;
	}
	
	/**
	 * 配達ページ用の購入済リストを購入IDごとのリストにして返却します。
	 * @param r_uid　リクエストユーザーID
	 * @param b_uid　購入ユーザーID
	 * @return　購入IDごとのリスト
	 */
	public List<RequestsDtoPerBuycode> findReqestListPerUserByUserIdForDelivery(int r_uid, int b_uid) {
		List<RequestsJoinBuycode> allL = findAllRequestsPerUser(b_uid);
		List<RequestsJoinBuycode> selectL = new ArrayList<RequestsJoinBuycode>();
		if(!allL.isEmpty()) {
			for(RequestsJoinBuycode item : allL) {
				if(item.getRequest_user_id() == r_uid && item.getBuycode() != 0 && !item.isIsdelivery()){
					selectL.add(item);
				}
			}
		}
		return convertRequestsListPerBuycode(selectL);
		
	}

	/**
	 * 依頼者用のリクエストページのリストを返却
	 * @param r_uid　リクエストユーザーID
	 * @return　リクエストIDにひもづくリクエストプロダクト 
	 */
	public List<RequestDto> getPersonalRequest(int r_uid) {
		List<RequestDto> returnList = new ArrayList<RequestDto>();
		String sql = "select Distinct r.id, r.product_name, r.vol, r.unit, r.request_user_id, r.inCart, r.buycode from requests as r LEFT OUTER JOIN buycodes as b ON r.buycode = b.buycode where request_user_id = ? and (isrecieve is NULL or isrecieve = 0) and r.delete_flg = 0;";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, r_uid);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				RequestDto record = convertRequestDto(rs);
				returnList.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	private RequestDto convertRequestDto(ResultSet rs) throws SQLException {
		RequestDto rdto = new RequestDto();
		rdto.setId(rs.getInt("id"));
		rdto.setProduct_name(rs.getString("product_name"));
		rdto.setVol(rs.getInt("vol"));
		rdto.setUnit(rs.getString("unit"));
		rdto.setRequest_user_id(rs.getInt("request_user_id"));
		rdto.setInCart(rs.getBoolean("inCart"));
		rdto.setBuycode(rs.getInt("buycode"));
		return rdto;
	}

	/**
	 * 依頼者用のリクエストページの削除ボタンを押下されたアイテムのdelete_flgを"1"に更新
	 * @param request_id　削除するリクエストのID
	 * @return　更新成功⇒0 購入済み⇒1 エラー⇒100以上
	 */
	public int rmRequest(int request_id) {
		try {
			// TODO 購入済み確認実装
			
			String sql = "update requests set delete_flg = 1 where id = ?;";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, request_id);
			int rs = pst.executeUpdate();
			if(rs == 1) {
				return 0;
			} else {
				return 100;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 101;
		}
	}
	
	/**
	 * 単位の一覧を取得
	 * @param order　並び順（0：昇順 0以外：降順）
	 * @return　単位のList
	 */
	public List<UnitDto> getUnitList(int order) {
		List<UnitDto> returnList = new ArrayList<UnitDto>();
		String sql = "select * from units;";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				UnitDto ud = new UnitDto();
				ud.setId(rs.getInt("id"));
				ud.setSort_col(rs.getInt("sort_col"));
				ud.setUnit_name(rs.getString("unit_name"));
				returnList.add(ud);
			}
			if (order == 0) {
				Collections.sort(returnList);
			}else {
				Collections.sort(returnList, Comparator.reverseOrder());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnList;
	}
	
	/**
	 * 単位の一覧を取得
	 * @param order　並び順（0：昇順 0以外：降順）
	 * @return　単位のList
	 */
	public List<UnitDto> getBuyCompRequestsList(int order) {
		List<UnitDto> returnList = new ArrayList<UnitDto>();
		String sql = "select * from units;";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				UnitDto ud = new UnitDto();
				ud.setId(rs.getInt("id"));
				ud.setSort_col(rs.getInt("sort_col"));
				ud.setUnit_name(rs.getString("unit_name"));
				returnList.add(ud);
			}
			if (order == 0) {
				Collections.sort(returnList);
			}else {
				Collections.sort(returnList, Comparator.reverseOrder());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnList;
	}
	
}
	
