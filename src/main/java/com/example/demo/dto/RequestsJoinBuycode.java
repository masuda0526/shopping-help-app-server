package com.example.demo.dto;

import java.sql.Timestamp;

import com.example.demo.entity.ERequest;

import lombok.Data;

@Data
public class RequestsJoinBuycode extends ERequest  {
	
	private String user_name;
	private int seq;
	private int r_uid;
	private int b_uid;
	private Timestamp b_at;
	private boolean isdelivery;
	private Timestamp d_at;
	private boolean isrecieve;
	private Timestamp r_at;
	private Timestamp r_acp_at;
	private Timestamp b_acp_at;
	
	
}
