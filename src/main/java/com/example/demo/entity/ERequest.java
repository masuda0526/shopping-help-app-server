package com.example.demo.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ERequest {
	
	private int id;
	private String product_name;
	private int request_user_id;
	private boolean isbuy;
	private int buy_user_id;
	private Timestamp created_at;
	private Timestamp updated_at;
	private boolean delete_flg;
	private boolean inCart;
	private int inCart_user_id;
	
	
	
}
