package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.LinkDao;

@RestController
public class UserlinkController {
	
	@Autowired
	LinkDao ld;
	
	@GetMapping("/userlink/regist")
	public boolean registUserLink(@RequestParam int myid , @RequestParam String lp) {
		return ld.registUserLink(myid, lp);
	}
	
	@GetMapping("/userlink/getname")
	public String getUserNameByLinkPass(@RequestParam String lp) {
		return ld.getUserNameByLinkId(lp);
	}
	
}
