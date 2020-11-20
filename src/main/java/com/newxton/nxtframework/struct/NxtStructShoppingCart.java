package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youjian163mail@163.com
 * @time 2020/11/17
 * @address Suzhou, China
 * @copyright NxtFramework
 * 
 * Api接口数据结构：购物车
 * 
 */
public class NxtStructShoppingCart {
	private Long id; // 购物车主键
	private Long userId; // 已登录用户user_id
	private String guestToken; // 匿名用户token
	private List<NxtStructShoppingCartProduct> itemList = new ArrayList<>(); // 产品列表
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getGuestToken() {
		return guestToken;
	}
	public void setGuestToken(String guestToken) {
		this.guestToken = guestToken;
	}

	public List<NxtStructShoppingCartProduct> getItemList() {
		return itemList;
	}

	public void setItemList(List<NxtStructShoppingCartProduct> itemList) {
		this.itemList = itemList;
	}
}