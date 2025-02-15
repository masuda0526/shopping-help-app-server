package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.BuycodeDao;

@RestController
public class BuycodeController {

	@Autowired
	BuycodeDao bDao;
	
	@GetMapping("/complete/delivary/buycode")
	public boolean compDeliveryUseBuycode(@RequestParam int buycode, @RequestParam int seq) {
		return bDao.compDeliveryUseBuycode(buycode, seq);
	}
	
	@GetMapping("/complete/delivary/request_id")
	public boolean compDeliveryUseRequestUseId(@RequestParam int r_uid, @RequestParam int b_uid) {
		return bDao.compDeliveryUseRequestUseId(r_uid, b_uid);
	}
	
	@GetMapping("/accept/delivary/buycode")
	public boolean acceptDeliveryByBuycode(@RequestParam int buycode, @RequestParam int seq) {
		return bDao.acceptDeliveryByBuycode(buycode, seq);
	}
	
	@GetMapping("/accept/delivary/request_id")
	public boolean acceptDeliveryByRequestUserId(@RequestParam int r_uid, @RequestParam int b_uid) {
		return bDao.acceptDeliveryByRequestUserId(r_uid, b_uid);
	}
	
}
