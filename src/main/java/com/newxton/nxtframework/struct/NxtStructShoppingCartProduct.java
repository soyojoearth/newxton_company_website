package com.newxton.nxtframework.struct;

import java.util.ArrayList;
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

	private Long id;//nxt_shopping_cart_product表主键
	private Long productId; // 产品id
    private Long quantity; // 购买数量
    private List<NxtStructShoppingCartProductSku> sku = new ArrayList<>(); // 选择的sku值

	private String picUrl;
	private String productName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public List<NxtStructShoppingCartProductSku> getSku() {
		return sku;
	}

	public void setSku(List<NxtStructShoppingCartProductSku> sku) {
		this.sku = sku;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}