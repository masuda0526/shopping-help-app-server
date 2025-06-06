package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.RequestDao;
import com.example.demo.dto.MypageDto;
import com.example.demo.dto.RequestDto;
import com.example.demo.dto.RequestsDtoPerBuycode;
import com.example.demo.dto.RequestsDtoPerUser;
import com.example.demo.dto.UnitDto;
import com.example.demo.util.UserType;

@RestController
public class RequestController {
	@Autowired
	RequestDao reqDao;
	
	@GetMapping("/request/community")
	public List<RequestDto> getCommunityRequests(@RequestParam int id) {
		return reqDao.getComunityRequests(id);
	}
	
	@GetMapping("/request/add")
	public boolean addRequest(@RequestParam String product_name, @RequestParam int vol, @RequestParam String unit, @RequestParam int request_user_id) {
		return reqDao.addRequest(product_name, vol, unit, request_user_id);
	}
	
	@GetMapping("/request/togglecart")
	public boolean toggleInCart(@RequestParam int request_id, @RequestParam int inCart_user_id, @RequestParam boolean bool) {
		return reqDao.toggleInCart(request_id, inCart_user_id, bool);
	}
	
	@GetMapping("/request/comp")
	public boolean compShopping(@RequestParam int[] r_uids, @RequestParam int buy_user_id) {
		return reqDao.compShopping(r_uids, buy_user_id);
	}
	
	@GetMapping("/request/mypage")
	public MypageDto findRequestListPerUserByLinkUserForMypage(@RequestParam int user_id){
		return reqDao.findRequestListPerUserByLinkUserForMypage(user_id);
	}
	
	@GetMapping("/start/shopping")
	public List<RequestsDtoPerUser> getStartShoppingList(@RequestParam int user_id){
		return reqDao.findRequestListPerUserByLinkUserForStartShopping(user_id);
	}
	
	@GetMapping("/delivery")
	public List<RequestsDtoPerBuycode> getfindReqestListPerUserByUserIdForDelivery(@RequestParam int r_uid, @RequestParam int b_uid){
		return reqDao.findReqestListPerUserByUserIdForDelivery(r_uid, b_uid);
	}
	
	@GetMapping("/getpersonalrequest")
	public List<RequestDto> GetPersonalRequest(@RequestParam int r_uid){
		return reqDao.getPersonalRequest(r_uid);
	}
	
	@GetMapping("/request/remove")
	public int removeRequest(@RequestParam int request_id){
		return reqDao.rmRequest(request_id);
	}
	
	@GetMapping("/getunitlist")
	public List<UnitDto> getUnitList(@RequestParam int order){
		return reqDao.getUnitList(order);
	}

	@GetMapping("/getbuycompreq")
	public List<UnitDto> getBuyCompRequests(@RequestParam int order){
		return reqDao.getBuyCompRequestsList(order);
	}
	
	@GetMapping("/require/peru/b")
	public List<RequestsDtoPerUser> getActionRequiredRequestForBuyerPerUser(@RequestParam int u_id){
		return reqDao.getActionRequiredRequestByUserId(u_id, UserType.BUY_USER.getValue());
	}
	
	@GetMapping("/require/perb/b")
	public List<RequestsDtoPerBuycode> getActionRequiredRequestForBuyerPerBuycode(@RequestParam int u_id){
		return reqDao.getActionRequiredRequestByBuycode(u_id, UserType.BUY_USER.getValue());
	}
	
	@GetMapping("/require/peru/r")
	public List<RequestsDtoPerUser> getActionRequiredRequestForRequestUserPerUser(@RequestParam int u_id){
		return reqDao.getActionRequiredRequestByUserId(u_id, UserType.REQUEST_USER.getValue());
	}
	
	@GetMapping("/require/perb/r")
	public List<RequestsDtoPerBuycode> getActionRequiredRequestForBuyRequestUserPerBuycode(@RequestParam int u_id){
		return reqDao.getActionRequiredRequestByBuycode(u_id, UserType.REQUEST_USER.getValue());
	}
	
}
