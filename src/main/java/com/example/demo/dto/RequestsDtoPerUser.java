package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RequestsDtoPerUser {
	private int user_id;
	private String user_name;
	private List<RequestsJoinBuycode> requests;
	
	public void addRequests(RequestsJoinBuycode eReq) {
		if(requests == null) {
			List<RequestsJoinBuycode> list = new ArrayList<>();
			setRequests(list);
		}
		if(eReq != null) {			
			requests.add(eReq);
		}
	}
}
