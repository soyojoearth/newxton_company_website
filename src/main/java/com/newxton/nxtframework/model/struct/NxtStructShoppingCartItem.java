package com.newxton.nxtframework.model.struct;

import java.util.List;

/**
 * @author youjian163mail@163.com
 * @time 2020/11/17
 * @address Suzhou, China
 * @copyright NxtFramework
 * 
 * Api接口数据结构：购物车添加每个产品信息
 * 
 */
public class NxtStructShoppingCartItem {
	private String guestToken; // 匿名用户token
    private NxtStructShoppingCartProduct product; // 加入产品
    private List<NxtStructProductSku> sku; // 选择的sku值
    
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
	public List<NxtStructProductSku> getSku() {
		return sku;
	}
	public void setSku(List<NxtStructProductSku> sku) {
		this.sku = sku;
	}
}
