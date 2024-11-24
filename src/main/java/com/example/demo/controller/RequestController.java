package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.RequestDao;
import com.example.demo.dto.RequestDto;

@RestController
public class RequestController {
	@Autowired
	RequestDao reqDao;
	
	@GetMapping("/request/community")
	public List<RequestDto> getCommunityRequests(@RequestParam int id) {
		return reqDao.getComunityRequests(id);
	}
	
	@GetMapping("/request/add")
	public boolean addRequest(@RequestParam String product_name, @RequestParam int request_user_id) {
		return reqDao.addRequest(product_name, request_user_id);
	}
	
	@GetMapping("/request/togglecart")
	public boolean toggleInCart(@RequestParam int request_id, @RequestParam int inCart_user_id, @RequestParam boolean bool) {
		return reqDao.toggleInCart(request_id, inCart_user_id, bool);
	}
	
	@GetMapping("/request/comp")
	public boolean compShopping(@RequestParam int buy_user_id) {
		return reqDao.compShopping(buy_user_id);
	}
	
}
