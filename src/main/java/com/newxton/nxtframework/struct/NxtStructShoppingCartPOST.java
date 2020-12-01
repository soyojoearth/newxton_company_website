package com.newxton.nxtframework.struct;

/**
 * @author youjian163mail@163.com
 * @time 2020/11/17
 * @address Suzhou, China
 * @copyright NxtFramework
 * 
 * Api接口数据结构：购物车添加每个产品信息
 * 
 */
public class NxtStructShoppingCartPOST {
	private String guestToken; // 匿名用户token
    private NxtStructShoppingCartProduct product; // 加入产品
    
    public String getGuestToken() {
		return guestToken;
	}
	public void setGuestToken(String guestToken) {
		this.guestToken = guestToken;
	}
	public NxtStructShoppingCartProduct getProduct() {
		return product;
	}
	public void setProduct(NxtStructShoppingCartProduct product) {
		this.product = product;
	}
}
