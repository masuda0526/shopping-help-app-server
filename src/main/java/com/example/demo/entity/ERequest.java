package com.example.demo.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ERequest {
	
	protected int id;
	protected String product_name;
	protected int vol;
	protected String unit;
	protected int request_user_id;
	protected boolean inCart;
	protected int inCart_user_id;
	protected int buycode;
	protected Timestamp created_at;
	protected Timestamp updated_at;
	protected boolean delete_flg;
	
	
	
}
