package com.example.demo.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class EUser {
	private int id;
	private String name;
	private String email;
	private String password;
	private String tel;
	private Timestamp created_at;
	private Timestamp updated_at;
	private boolean delete_flg;
}
