package com.example.demo.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ELink {
	
	private int id;
	private int uid1;
	private Timestamp uid1_at;
	private int uid2;
	private Timestamp uid2_at;
	private boolean ismatch;
	private Timestamp match_at;
	private boolean delete_flg;
	
}
