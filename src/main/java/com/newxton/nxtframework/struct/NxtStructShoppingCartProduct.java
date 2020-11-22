package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.Collections;
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

	public boolean isSameProductWithSku(Object another){

		//先判断是不是自己
		if (this == another) {
			return true;
		}
		//再判断是不是NxtStructShoppingCartProduct类
		if (another instanceof NxtStructShoppingCartProduct) {
			NxtStructShoppingCartProduct anotherObject = (NxtStructShoppingCartProduct) another;
			if (this.productId.equals(anotherObject.getProductId())){
				if (this.sku.size() != anotherObject.getSku().size()){
					return false;
				}
				Collections.sort(this.sku);
				Collections.sort(anotherObject.getSku());
				for (int i = 0; i < this.sku.size(); i++) {
					NxtStructShoppingCartProductSku productSku1 = this.sku.get(i);
					NxtStructShoppingCartProductSku productSku2 = this.sku.get(i);
					if (!productSku1.equals(productSku2)){
						return false;
					}
				}
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}

	}

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