package com.example.demo.util;

public enum UserType {
	//ユーザータイプ
	//頼む人
	REQUEST_USER("頼む人", 1),
	BUY_USER("購入者",0);
	
	private String typeName;
	private int value;
	
	private UserType(String typeName, int value) {
		this.typeName = typeName;
		this.value = value;
	}
	
	public String getTypeName() {
		return this.typeName;
	}
	
	public int getValue() {
		return this.value;
	}
	
}
