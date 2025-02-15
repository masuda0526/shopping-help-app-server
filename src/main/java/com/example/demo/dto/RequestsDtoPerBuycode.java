package com.example.demo.dto;

import lombok.Data;

@Data
public class RequestsDtoPerBuycode extends RequestsDtoPerUser {
	
	protected int buycode;
	protected int seq;

}
