package com.newxton.nxtframework.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youjian163mail@163.com
 * @time 2020/11/17
 * @address Suzhou, China
 * @copyright NxtFramework
 */
public class NxModelShoppingCart {
	public Long id;
    public Long userId;
    public String guestToken;
    
    public List<NxtModelProduct> shoppingCartProductList = new ArrayList<>();
    
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

	public List<NxtModelProduct> getShoppingCartProductList() {
		return shoppingCartProductList;
	}

	public void setShoppingCartProductList(List<NxtModelProduct> shoppingCartProductList) {
		this.shoppingCartProductList = shoppingCartProductList;
	}
}
