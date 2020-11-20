package com.newxton.nxtframework.controller.api.front;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtShoppingCart;
import com.newxton.nxtframework.entity.NxtShoppingCartProduct;
import com.newxton.nxtframework.model.struct.NxtStructShoppingCartItem;
import com.newxton.nxtframework.service.NxtShoppingCartProductService;
import com.newxton.nxtframework.service.NxtShoppingCartService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @author youjian163mail@163.com
 * @time 2020/11/17
 * @address Suzhou, China
 * @copyright NxtFramework
 * 
 * Api接口：购物车-商品加入
 * 
 */
@RestController
public class NxtApiShoppingCartAddController {

	@Resource
	private NxtShoppingCartService nxtShoppingCartService;
	@Resource
	private NxtShoppingCartProductService nxtShoppingCartProductService;

	@RequestMapping(value = "/api/shopping_cart/add_product", method = RequestMethod.POST)
	public Map<String, Object> index(@RequestHeader(value = "user_id", required = false) Long userId,
			@RequestHeader(value = "token", required = false) String token,
			@RequestBody String json) {
		Map<String, Object> result = new HashMap<>();

		// 检查条件user_id有值，还是guestToken有值，如果都有值以user_id为主
		Gson gson = new Gson();
		NxtStructShoppingCartItem shoppingCartItem = gson.fromJson(json, NxtStructShoppingCartItem.class);
		if (userId == null
				&& (shoppingCartItem.getGuestToken() == null || shoppingCartItem.getGuestToken().equals(""))) {
			return this.fail(result, 120001, "user_id、guestToken没传入参数值");
		}

		if (userId != null) {
			// 已登录用户流程
			return this.processUserShoppingCartAddProduct(result, userId, token, shoppingCartItem);
		} else {
			// 匿名用户流程
			return this.processGuestShoppingCartAddProduct(result, shoppingCartItem);
		}
	}

	// 处理已登录用户 移除产品
	private Map<String, Object> processUserShoppingCartAddProduct(Map<String, Object> result, Long userId, String token,
			NxtStructShoppingCartItem shoppingCartItem) {
		// 查询购物车
		NxtShoppingCart shoppingCart = nxtShoppingCartService.queryByUserId(userId);
		String prefixStatusMsg = "当前登录用户user_id:" + userId + ",";
		
		if (shoppingCart == null) {
			NxtShoppingCart newNxtShoppingCart = new NxtShoppingCart();
			newNxtShoppingCart.setUserId(userId);
			newNxtShoppingCart.setToken(token);
			nxtShoppingCartService.insert(newNxtShoppingCart);
			
			shoppingCart = newNxtShoppingCart;
		}

		return this.processShoppingCartAddProduct(result, prefixStatusMsg, shoppingCartItem, shoppingCart);
	}
	
	// 处理匿名购物车 移除产品
	private Map<String, Object> processGuestShoppingCartAddProduct(Map<String, Object> result,
			NxtStructShoppingCartItem shoppingCartItem) {
		// 查询购物车
		String token = shoppingCartItem.getGuestToken();
		NxtShoppingCart shoppingCart = nxtShoppingCartService.queryByToken(token);
		String prefixStatusMsg = "当前匿名用户token:" + token + ",";
		
		if (shoppingCart == null) {
			NxtShoppingCart newNxtShoppingCart = new NxtShoppingCart();
			newNxtShoppingCart.setToken(token);
			nxtShoppingCartService.insert(newNxtShoppingCart);
			
			shoppingCart = newNxtShoppingCart;
		}

		return this.processShoppingCartAddProduct(result, prefixStatusMsg, shoppingCartItem, shoppingCart);
	}

	private Map<String, Object> processShoppingCartAddProduct(Map<String, Object> result, String prefixStatusMsg,
			NxtStructShoppingCartItem shoppingCartItem, NxtShoppingCart shoppingCart) {
		// 产品主键
		Long productId = null;
		try {
			productId = shoppingCartItem.getProduct().getId();
			if (productId == null) {
				return this.fail(result, 120030, prefixStatusMsg + "传入产品主键为宽");
			}
		} catch (Exception ex) {
			return this.fail(result, 120020, prefixStatusMsg + "传入产品信息有误");
		}

		// 查询当前用户当前购物车产品信息
		NxtShoppingCartProduct shoppingCartProduct = nxtShoppingCartProductService
				.queryByShoppingCartIdProductId(shoppingCart.getId(), productId);
		if (shoppingCartProduct == null) {
			// 购物车中无此产品时则添加
			NxtShoppingCartProduct newNxtShoppingCartProduct = new NxtShoppingCartProduct();
			newNxtShoppingCartProduct.setDateline(System.currentTimeMillis());
			newNxtShoppingCartProduct.setShoppingCartId(shoppingCart.getId());
			newNxtShoppingCartProduct.setProductId(productId);
			newNxtShoppingCartProduct.setQuantity(shoppingCartItem.getProduct().getQuantity());
			String skuJsonStr = JSONObject.toJSONString(shoppingCartItem.getProduct().getSku());
			newNxtShoppingCartProduct.setSku(skuJsonStr);
			
			nxtShoppingCartProductService.insert(newNxtShoppingCartProduct);
		} else {
			// 购物车中已有此产品时则更新数量
			// 判读移除产品数量是否合法
			Long quantity = shoppingCartItem.getProduct().getQuantity();
			if (quantity == null) {
				return this.fail(result, 120050, prefixStatusMsg + "产品id:" + productId + "移除数量:传入参数为空");
			}

			Long finalQuantity = shoppingCartProduct.getQuantity() + quantity;
			shoppingCartProduct.setQuantity(finalQuantity);
			nxtShoppingCartProductService.update(shoppingCartProduct);
		}

		return this.success(result);
	}

	private Map<String, Object> success(Map<String, Object> result) {
		result.put("status", 0);
		result.put("message", "");

		return result;
	}

	private Map<String, Object> fail(Map<String, Object> result, int statusCode, String statusMsg) {
		result.put("status", statusCode);
		result.put("message", statusMsg);

		return result;
	}
}