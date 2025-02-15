package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;

@Data
public class MypageDto {

	
	private List<RequestsDtoPerUser> purchasedList;
	private List<RequestsDtoPerUser> nonpurchasedList;
	
	/**
	 * リストにユーザーごとのリクエストデータを追加します。
	 * @param li　追加対象のリスト
	 * @param dto　追加したいユーザーごとのリクエストデータ
	 */
	private void addRequestsDtoPerUser(List<RequestsDtoPerUser> li, RequestsDtoPerUser dto) {
		li.add(dto);
	}
	
	/**
	 * リストにユーザーごとのリクエストデータリストを追加します。
	 * @param li　追加対象のリスト
	 * @param dtos　追加したいユーザーごとのリクエストデータのリスト
	 */
	private void addRequestsDtoPerUser(List<RequestsDtoPerUser> li, List<RequestsDtoPerUser> dtos) {
		if(!dtos.isEmpty() && Objects.nonNull(dtos)) {
			for(RequestsDtoPerUser dto : dtos) {
				addRequestsDtoPerUser(li, dto);
			}
		}
	}
	
	/**
	 * 購入リストデータを追加します。
	 * @param dto　追加したいユーザーごとのリクエストデータ
	 */
	public void addPurchasedList(RequestsDtoPerUser dto) {
		if(Objects.isNull(this.purchasedList)) {
			List<RequestsDtoPerUser> li = new ArrayList<RequestsDtoPerUser>();
			setPurchasedList(li);
		}
		addRequestsDtoPerUser(this.purchasedList, dto);
	}
	
	/**
	 * 購入リストデータを追加します。
	 * @param dto　追加したいユーザーごとのリクエストデータのリスト
	 */
	public void addPurchasedList(List<RequestsDtoPerUser> dtos) {
		if(Objects.isNull(this.purchasedList)) {
			List<RequestsDtoPerUser> li = new ArrayList<RequestsDtoPerUser>();
			setPurchasedList(li);
		}
		addRequestsDtoPerUser(this.purchasedList, dtos);
	}
	
	/**
	 * 未購入リストデータを追加します。
	 * @param dto　追加したいユーザーごとのリクエストデータ
	 */
	public void addNonPurchasedList(RequestsDtoPerUser dto) {
		if(Objects.isNull(this.nonpurchasedList)) {
			List<RequestsDtoPerUser> li = new ArrayList<RequestsDtoPerUser>();
			setNonpurchasedList(li);
		}
		addRequestsDtoPerUser(this.nonpurchasedList, dto);
	}
	
	/**
	 * 未購入リストデータを追加します。
	 * @param dto　追加したいユーザーごとのリクエストデータのリスト
	 */
	public void addNonPurchasedList(List<RequestsDtoPerUser> dtos) {
		if(Objects.isNull(this.nonpurchasedList)) {
			List<RequestsDtoPerUser> li = new ArrayList<RequestsDtoPerUser>();
			setNonpurchasedList(li);
		}
		addRequestsDtoPerUser(this.nonpurchasedList, dtos);
	}
	
	
	
	
}
