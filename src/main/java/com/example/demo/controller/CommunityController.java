package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.CommunityDao;
import com.example.demo.dto.CommunityDto;
import com.example.demo.entity.ECommunity;

@RestController
public class CommunityController {

	@Autowired
	CommunityDao comDao;
	
	@GetMapping("/community/get")
	public List<CommunityDto> getCommunityList(@RequestParam int user_id){
		return comDao.getCommunityList(user_id);
	}
	
	@GetMapping("/community/all")
	public List<ECommunity> getAllCommunityList(){
		return comDao.getAllCommunityList();
	}
	
	@GetMapping("/community/join")
	public boolean joinCommunity(@RequestParam int community_id, @RequestParam int user_id) {
		return comDao.joinCommunity(community_id, user_id);
	}
}
