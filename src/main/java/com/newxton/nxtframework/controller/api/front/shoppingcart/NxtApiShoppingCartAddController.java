package com.newxton.nxtframework.controller.api.front.shoppingcart;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.NxtShoppingCart;
import com.newxton.nxtframework.entity.NxtShoppingCartProduct;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessShoppingCart;
import com.newxton.nxtframework.struct.*;
import com.newxton.nxtframework.service.NxtShoppingCartProductService;
import com.newxton.nxtframework.service.NxtShoppingCartService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @author youjian163mail@163.com、soyojo.earth@gmail.com
 * @time 2020/11/17
 * @address Suzhou, China
 * @copyright NxtFramework
 * 
 *            Api接口：购物车-商品加入
 * 
 */
@RestController
public class NxtApiShoppingCartAddController {

	@Resource
	private NxtShoppingCartService nxtShoppingCartService;
	@Resource
	private NxtShoppingCartProductService nxtShoppingCartProductService;
	@Resource
	private NxtUtilComponent nxtUtilComponent;

	@Resource
	private NxtProcessShoppingCart nxtProcessShoppingCart;

	@RequestMapping(value = "/api/shopping_cart/add_product", method = RequestMethod.POST)
	public NxtStructApiResult index(@RequestHeader(value = "user_id", required = false) Long userId,
									@RequestHeader(value = "token", required = false) String token, @RequestBody String json) {

		// 检查条件user_id有值，还是guestToken有值，如果都有值以user_id为主
		Gson gson = new Gson();
		NxtStructShoppingCartPOST shoppingCartPOST;
		try {
			shoppingCartPOST = gson.fromJson(json, NxtStructShoppingCartPOST.class);
		}
		catch (Exception e){
			return new NxtStructApiResult(53,"json格式不正确");
		}

		NxtShoppingCart shoppingCart;
		if (userId != null) {
			// 已登录用户 购物车
			shoppingCart = nxtShoppingCartService.queryByUserId(userId);
			if (shoppingCart == null) {
				NxtShoppingCart newNxtShoppingCart = new NxtShoppingCart();
				newNxtShoppingCart.setUserId(userId);
				newNxtShoppingCart.setToken(token);
				nxtShoppingCartService.insert(newNxtShoppingCart);
				shoppingCart = newNxtShoppingCart;
			}

		} else {
			// 匿名用户 购物车
			String guestToken = shoppingCartPOST.getGuestToken();
			shoppingCart = nxtShoppingCartService.queryByToken(guestToken);
			if (shoppingCart == null ||
					shoppingCart.getUserId() != null//已有归属的购物车，不能仅靠单独guestToken操作
			) {
				NxtShoppingCart newNxtShoppingCart = new NxtShoppingCart();
				newNxtShoppingCart.setToken(nxtUtilComponent.getRandomString(32));
				nxtShoppingCartService.insert(newNxtShoppingCart);
				shoppingCart = newNxtShoppingCart;
			}
		}

		//执行添加
		try {
			this.processShoppingCartAddProduct(shoppingCartPOST,shoppingCart);
			Map<String,Object> data = new HashMap<>();
			data.put("guestToken",shoppingCart.getToken());
			return new NxtStructApiResult(data);
		}
		catch (NxtException e){
			return new NxtStructApiResult(53,e.getNxtExecptionMessage());
		}
	}

	/**
	 * 执行添加
	 * @param shoppingCartItem
	 * @param shoppingCart
	 * @throws NxtException
	 */
	private void processShoppingCartAddProduct(NxtStructShoppingCartPOST shoppingCartItem, NxtShoppingCart shoppingCart) throws NxtException {

		NxtStructShoppingCartProduct nxtStructShoppingCartProduct = shoppingCartItem.getProduct();

		NxtStructShoppingCart nxtStructShoppingCart = nxtProcessShoppingCart.allDetail(shoppingCart);

		List<NxtStructShoppingCartProduct> itemList = nxtStructShoppingCart.getItemList();

		//寻找NxtStructShoppingCartProduct是否存在
		for (NxtStructShoppingCartProduct item : itemList){
			if(item.isSameProductWithSku(nxtStructShoppingCartProduct)){
				//已存在，加数量
				NxtShoppingCartProduct nxtShoppingCartProduct = nxtShoppingCartProductService.queryById(item.getId());
				nxtShoppingCartProduct.setQuantity(nxtShoppingCartProduct.getQuantity()+nxtStructShoppingCartProduct.getQuantity());
				nxtShoppingCartProductService.update(nxtShoppingCartProduct);
				return;
			}
		}

		//不存在，新加
		NxtShoppingCartProduct newNxtShoppingCartProduct = new NxtShoppingCartProduct();
		newNxtShoppingCartProduct.setDateline(System.currentTimeMillis());
		newNxtShoppingCartProduct.setShoppingCartId(shoppingCart.getId());
		newNxtShoppingCartProduct.setProductId(shoppingCartItem.getProduct().getProductId());
		newNxtShoppingCartProduct.setQuantity(shoppingCartItem.getProduct().getQuantity());
		String skuJsonStr = JSONObject.toJSONString(shoppingCartItem.getProduct().getSku());
		newNxtShoppingCartProduct.setSku(skuJsonStr);
		newNxtShoppingCartProduct.setSelected(1);

		nxtShoppingCartProductService.insert(newNxtShoppingCartProduct);

	}

}