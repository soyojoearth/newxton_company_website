package com.newxton.nxtframework.model.struct;

import java.util.List;

/**
 * @author youjian163mail@163.com
 * @time 2020/11/17
 * @address Suzhou, China
 * @copyright NxtFramework
 * 
 * Api接口数据结构：购物车购买的单个产品信息
 * 
 */
public class NxtStructShoppingCartProduct {
	private Long id; // 产品id
    private Long quantity; // 购买数量
    private List<NxtStructProductSku> sku; // 选择的sku值
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}	
	
	public List<NxtStructProductSku> getSku() {
		return sku;
	}
	public void setSku(List<NxtStructProductSku> sku) {
		this.sku = sku;
	}
}