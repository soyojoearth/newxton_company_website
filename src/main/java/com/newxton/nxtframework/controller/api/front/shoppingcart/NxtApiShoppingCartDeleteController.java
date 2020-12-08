package com.newxton.nxtframework.controller.api.front.shoppingcart;

import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtShoppingCart;
import com.newxton.nxtframework.entity.NxtShoppingCartProduct;
import com.newxton.nxtframework.service.NxtShoppingCartProductService;
import com.newxton.nxtframework.service.NxtShoppingCartService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructShoppingCartPOST;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * @author youjian163mail@163.com、soyojo.earth@gmail.com
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
	public NxtStructApiResult index(@RequestHeader(value = "user_id", required = false) Long userId, @RequestBody String json) {

		Gson gson = new Gson();
		NxtStructShoppingCartPOST shoppingCartItem;

		try {
			shoppingCartItem = gson.fromJson(json, NxtStructShoppingCartPOST.class);
		}
		catch (Exception e){
			return new NxtStructApiResult(53,"json格式不正确");
		}

		if (userId == null && (shoppingCartItem.getGuestToken() == null || shoppingCartItem.getGuestToken().isEmpty())) {
			return new NxtStructApiResult(54,"缺少user_id或guestToken");
		}

		NxtShoppingCart shoppingCart;

		// 检查条件user_id有值，还是guestToken有值，如果都有值以user_id为主
		if (userId != null) {
			// 已登录用户 购物车
			shoppingCart = nxtShoppingCartService.queryByUserId(userId);
		} else {
			// 匿名用户流程 购物车
			String token = shoppingCartItem.getGuestToken();
			shoppingCart = nxtShoppingCartService.queryByToken(token);
			if (shoppingCart != null && shoppingCart.getUserId() != null){
				//已有归属的购物车，不能仅靠单独guestToken操作
				shoppingCart = null;
			}
		}

		if (shoppingCart == null){
			return new NxtStructApiResult(34,"没找到购物车，请检查上传参数");
		}

		Long shoppingCartItemId = shoppingCartItem.getProduct().getId();

		// 购物车物品id
		if (shoppingCartItemId == null) {
			return new NxtStructApiResult(34,"传入购物车物品id为空");
		}

		// 查询当前用户当前购物车物品信息
		NxtShoppingCartProduct shoppingCartProduct = nxtShoppingCartProductService.queryById(shoppingCartItemId);

		if (shoppingCartProduct == null){
			return new NxtStructApiResult(34,"购物车里没有该物品");
		}

		if (!shoppingCartProduct.getShoppingCartId().equals(shoppingCart.getId())){
			return new NxtStructApiResult(34,"购物车里没有该物品");
		}

		// 判读移除产品数量是否合法
		Long quantity = shoppingCartItem.getProduct().getQuantity();
		if (quantity == null) {
			return new NxtStructApiResult(34,"缺少quantity");
		}

		// 如果大于数据库的数量则删除此记录
		if (quantity >= shoppingCartProduct.getQuantity()) {
			nxtShoppingCartProductService.deleteById(shoppingCartItemId);
		} else { // 如果小于则更新数据库数量
			Long finalQuantity = shoppingCartProduct.getQuantity() - quantity;
			shoppingCartProduct.setQuantity(finalQuantity);
			nxtShoppingCartProductService.update(shoppingCartProduct);
		}

		return new NxtStructApiResult();

	}

}
