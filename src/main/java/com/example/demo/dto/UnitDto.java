package com.example.demo.dto;

import lombok.Data;

@Data
public class UnitDto implements Comparable<UnitDto> {

	private int id;
	private int sort_col;
	private String unit_name;
	
	@Override
	public int compareTo(UnitDto compareTarget) {
		// TODO 自動生成されたメソッド・スタブ
		return this.sort_col - compareTarget.getSort_col();
	}
}
