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

	private Float productPrice;

	private Boolean selected = true;

	/**
	 * 无效（sku不对、库存无货时，会无效）
	 */
	private Boolean invalid = false;

	/**
	 * 比较是不是同一款产品且同样的sku
	 * @param obj
	 * @return
	 */
	public boolean isSameProductWithSku(Object obj){

		//先判断是不是自己
		if (this == obj) {
			return true;
		}
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NxtStructShoppingCartProduct another = (NxtStructShoppingCartProduct) obj;
		if (this.productId.equals(another.getProductId())){
			if (this.sku.size() != another.getSku().size()){
				return false;
			}
			Collections.sort(this.sku);
			Collections.sort(another.getSku());
			for (int i = 0; i < this.sku.size(); i++) {
				NxtStructShoppingCartProductSku productSku1 = this.sku.get(i);
				NxtStructShoppingCartProductSku productSku2 = another.getSku().get(i);
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

	public Float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Boolean getInvalid() {
		return invalid;
	}

	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}
}