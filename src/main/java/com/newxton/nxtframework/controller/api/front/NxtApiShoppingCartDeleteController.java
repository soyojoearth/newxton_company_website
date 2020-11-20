package com.newxton.nxtframework.controller.api.front;

import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtShoppingCart;
import com.newxton.nxtframework.entity.NxtShoppingCartProduct;
import com.newxton.nxtframework.struct.NxtStructShoppingCartItem;
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
 * Api接口：购物车-移除商品
 * 
 */
@RestController
public class NxtApiShoppingCartDeleteController {

	@Resource
	private NxtShoppingCartService nxtShoppingCartService;
	@Resource
	private NxtShoppingCartProductService nxtShoppingCartProductService;

	@RequestMapping(value = "/api/shopping_cart/del_product", method = RequestMethod.POST)
	public Map<String, Object> index(@RequestHeader(value = "user_id", required = false) Long userId,
			@RequestBody String json) {
		Map<String, Object> result = new HashMap<>();

		// 检查条件user_id有值，还是guestToken有值，如果都有值以user_id为主
		Gson gson = new Gson();
		NxtStructShoppingCartItem shoppingCartItem = gson.fromJson(json, NxtStructShoppingCartItem.class);
		if (userId == null
				&& (shoppingCartItem.getGuestToken() == null || shoppingCartItem.getGuestToken().equals(""))) {
			return this.fail(result, 100001, "user_id、guestToken没传入参数值");
		}

		if (userId != null) {
			// 已登录用户流程
			return this.processUserShoppingCartDelProduct(result, userId, shoppingCartItem);
		} else {
			// 匿名用户流程
			return this.processGuestShoppingCartDelProduct(result, shoppingCartItem);
		}
	}

	// 处理已登录用户 移除产品
	private Map<String, Object> processUserShoppingCartDelProduct(Map<String, Object> result, Long userId,
			NxtStructShoppingCartItem shoppingCartItem) {
		// 查询购物车
		NxtShoppingCart shoppingCart = nxtShoppingCartService.queryByUserId(userId);
		String prefixStatusMsg = "当前登录用户user_id:" + userId + ",";

		return this.processShoppingCartDelProduct(result, prefixStatusMsg, shoppingCartItem, shoppingCart);
	}
	
	// 处理匿名购物车 移除产品
	private Map<String, Object> processGuestShoppingCartDelProduct(Map<String, Object> result,
			NxtStructShoppingCartItem shoppingCartItem) {
		// 查询购物车
		String token = shoppingCartItem.getGuestToken();
		NxtShoppingCart shoppingCart = nxtShoppingCartService.queryByToken(token);
		String prefixStatusMsg = "当前匿名用户token:" + token + ",";

		return this.processShoppingCartDelProduct(result, prefixStatusMsg, shoppingCartItem, shoppingCart);
	}

	private Map<String, Object> processShoppingCartDelProduct(Map<String, Object> result, String prefixStatusMsg,
			NxtStructShoppingCartItem shoppingCartItem, NxtShoppingCart shoppingCart) {
		// 查询购物车
		if (shoppingCart == null) {
			return this.fail(result, 100010, prefixStatusMsg + "无购物车记录");
		}

		// 产品主键
		Long productId = null;
		try {
			productId = shoppingCartItem.getProduct().getId();
			if (productId == null) {
				return this.fail(result, 100030, prefixStatusMsg + "传入产品主键为宽");
			}
		} catch (Exception ex) {
			return this.fail(result, 100020, prefixStatusMsg + "传入产品信息有误");
		}

		// TODO
		// 查询当前用户当前购物车产品信息
		NxtShoppingCartProduct shoppingCartProduct = nxtShoppingCartProductService
				.queryByShoppingCartIdProductId(shoppingCart.getId(), productId).get(0);
		if (shoppingCartProduct == null) {
			return this.fail(result, 100040, prefixStatusMsg + "无此产品id:" + productId + "记录");
		}

		// 判读移除产品数量是否合法
		Long quantity = shoppingCartItem.getProduct().getQuantity();
		if (quantity == null) {
			return this.fail(result, 100050, prefixStatusMsg + "产品id:" + productId + "移除数量:传入参数为空");
		}

		// 如果大于数据库的数量则删除此记录
		if (quantity >= shoppingCartProduct.getQuantity()) {
			nxtShoppingCartProductService.deleteById(shoppingCartProduct.getId());
		} else { // 如果小于则更新数据库数量
			Long finalQuantity = shoppingCartProduct.getQuantity() - quantity;
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
