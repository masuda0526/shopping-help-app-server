package com.example.demo.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ECommunity {
	
	private int id;
	private String community_name;
	private Timestamp created_at;
	private Timestamp updated_at;
	private boolean delete_flg;
}
