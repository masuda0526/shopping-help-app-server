package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RequestDto {

	private int id;
	private String product_name;
	private int request_user_id;
	private String name;
	private Timestamp created_at;
	private boolean isbuy;
	private boolean delete_flg;
	private int buy_user_id;
	private boolean inCart;
	private int inCart_user_id;
	
}
