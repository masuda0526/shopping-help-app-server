package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.EUser;

@RestController
public class AuthController {
	
	@Autowired
	UserDao userdao;
	
	@GetMapping("/auth")
	public EUser authCheck(@RequestParam String auth_info, @RequestParam String pass) {
		return userdao.authCheck(auth_info, pass);
	}
	
	@GetMapping("/signup")
	public boolean signup(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String tel) {
		return userdao.signupUser(name, email, password, tel);
	}
	
}
