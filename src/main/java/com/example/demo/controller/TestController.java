package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.RequestDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.ERequest;
import com.example.demo.entity.EUser;

@RestController
public class TestController {

	@Autowired
	UserDao userDao;
	@Autowired
	RequestDao reqDao;
	
	@GetMapping("/test")
	public List<EUser> getUserList(){
		return userDao.getUserList();
	}
	
	@GetMapping("/hello")
	public String test() {
		return "アクセス成功です！";
	}
	
	@GetMapping("/request")
	public List<ERequest> getRequestAll(){
		return reqDao.getAllRequests();
	}
	
}
