package com.example.demo.dto;

import java.sql.Timestamp;

import com.example.demo.entity.ERequest;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestDto {

	private int id;
	private String product_name;
	private int vol;
	private String unit;
	private int request_user_id;
	private String name;
	private Timestamp created_at;
	private boolean isbuy;
	private boolean delete_flg;
	private int buy_user_id;
	private boolean inCart;
	private int inCart_user_id;
	
	public ERequest castERequest() {
		ERequest eReq = new ERequest();
		eReq.setId(id);
		eReq.setProduct_name(product_name);
		eReq.setVol(vol);
		eReq.setUnit(unit);
		eReq.setRequest_user_id(request_user_id);
		eReq.setIsbuy(isbuy);
		eReq.setBuy_user_id(buy_user_id);
		eReq.setInCart(inCart);
		eReq.setInCart_user_id(inCart_user_id);
		return eReq;
	}
}
