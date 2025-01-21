package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.ERequest;

import lombok.Data;

@Data
public class MypageCommunityRequestListDto{
	private int user_id;
	private String user_name;
	private List<ERequest> requests;
	
	public void addRequests(ERequest eReq) {
		if(requests == null) {
			List<ERequest> list = new ArrayList<>();
			setRequests(list);
		}
		if(eReq != null) {			
			requests.add(eReq);
		}
	}
	
}
