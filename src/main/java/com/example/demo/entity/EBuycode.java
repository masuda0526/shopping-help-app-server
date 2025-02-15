package com.example.demo.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class EBuycode {
	protected int id;
	protected int buycode;
	protected int seq;
	protected int r_uid;
	protected int b_uid;
	protected Timestamp b_at;
	protected boolean isdelivery;
	protected Timestamp d_at;
	protected boolean isrecieve;
	protected Timestamp r_at;
	protected Timestamp r_acp_at;
	protected Timestamp b_acp_at;
}
