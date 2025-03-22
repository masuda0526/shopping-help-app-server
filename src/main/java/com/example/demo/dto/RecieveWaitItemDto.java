package com.example.demo.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Data;

@Data
public class RecieveWaitItemDto {
	
	private String buyUserName;
	private String productName;
	private int vol;
	private String unit;
	private int buyCode;
	private int seq;
	private int isRecieve;

	public RecieveWaitItemDto(ResultSet rs) throws SQLException{
		this.buyUserName = rs.getString("name");
		this.productName = rs.getString("product_name");
		this.vol = rs.getInt("vol");
		this.unit = rs.getString("unit");
		this.buyCode = rs.getInt("buycode");
		this.seq = rs.getInt("seq");
		this.isRecieve = rs.getInt("isrecieve");
	}
}
