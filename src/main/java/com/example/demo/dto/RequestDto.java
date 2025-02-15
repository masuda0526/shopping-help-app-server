package com.example.demo.dto;

import com.example.demo.entity.ERequest;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestDto extends ERequest {

	private String name;
	
	public ERequest castERequest() {
		ERequest eReq = new ERequest();
		eReq.setId(id);
		eReq.setProduct_name(product_name);
		eReq.setVol(vol);
		eReq.setUnit(unit);
		eReq.setRequest_user_id(request_user_id);
		eReq.setInCart(inCart);
		eReq.setInCart_user_id(inCart_user_id);
		eReq.setBuycode(buycode);
		eReq.setCreated_at(created_at);
		eReq.setUpdated_at(updated_at);
		eReq.setDelete_flg(delete_flg);
		return eReq;
	}
}
