package com.newxton.nxtframework.controller.api.front;

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
 * Api接口：购物车-移除商品
 * 
 */
@RestController
public class NxtApiShoppingCartDeleteController {

	@Resource private NxtShoppingCartService nxtShoppingCartService;
	@Resource private NxtShoppingCartProductService nxtShoppingCartProductService;

    @RequestMapping(value = "/api/shopping_cart/del_product", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestHeader(value="user_id", required=false) Long userId, @RequestBody String json) {
    	Map<String, Object> result = new HashMap<>();
    	result.put("status", 0);
        result.put("message", "");
        
    	// 检查条件user_id有值，还是guestToken有值，如果都有值以user_id为主
    	Gson gson = new Gson();
    	NxtStructShoppingCartItem shoppingCartItem = gson.fromJson(json, NxtStructShoppingCartItem.class);
    	
    	if (userId == null && (shoppingCartItem.getGuestToken() == null || shoppingCartItem.getGuestToken().equals(""))) {
    		result.put("status", 100001);
            result.put("message", "user_id、guestToken没传入参数值");
            
            return result;
    	}
    	
    	if (userId != null) {
    		return this.processUserShoppingCartDelProduct(result, userId, shoppingCartItem);
    	} else {
    		return this.processGuestShoppingCartDelProduct(result, shoppingCartItem);
    	}
    }
    
    // TODO 处理匿名购物车　移除产品
    private Map<String, Object> processGuestShoppingCartDelProduct(Map<String, Object> result, NxtStructShoppingCartItem shoppingCartItem) {
    	return result;
    }
    
    // 处理已登录用户 移除产品
    private Map<String, Object> processUserShoppingCartDelProduct(Map<String, Object> result, Long userId, NxtStructShoppingCartItem shoppingCartItem) {
    	// 查询购物车
    	NxtShoppingCart shoppingCart = nxtShoppingCartService.queryByUserId(userId);

        if (shoppingCart == null) {
            result.put("status", 100010);
            result.put("message", "当前用户:" + userId + "无购物车记录");
            return result;
        }
        
        // 产品主键
        Long productId = null;
        try {
        	productId = shoppingCartItem.getProduct().getId();
        	
        	if (productId == null) {
        		result.put("status", 100030);
                result.put("message", "当前用户:" + userId + "付入产品主键为家");
                return result;
        	}
        } catch(Exception ex) {
        	result.put("status", 100020);
            result.put("message", "当前用户:" + userId + "付入产品信息有误");
            return result;
        }
        
        // 查询当前用户当前购物车产品信息
        NxtShoppingCartProduct ShoppingCartProduct = nxtShoppingCartProductService.queryByShoppingCartIdProductId(shoppingCart.getId(), productId);
        
        if (ShoppingCartProduct == null) {
        	result.put("status", 100040);
            result.put("message", "当前用户:" + userId + "无此产品:" + productId + "记录");
            return result;
        }
        
        // 判读移除产品数量是否合法
        Long quantity = shoppingCartItem.getProduct().getQuantity();
        if (quantity == null) {
        	result.put("status", 100050);
            result.put("message", "当前用户:" + userId + "产品:" + productId + "移除数量:传入参数为空");
            return result;
        }
        
        // 如果大于数据库的数量则删除此记录
        if (quantity >= ShoppingCartProduct.getQuantity()) {
        	nxtShoppingCartProductService.deleteById(ShoppingCartProduct.getId());
        } else {
        	// 如果小于则更新数据库数量
        	Long finalQuantity = ShoppingCartProduct.getQuantity() - quantity;
        	ShoppingCartProduct.setQuantity(finalQuantity);
        	nxtShoppingCartProductService.update(ShoppingCartProduct);
        }
        
        return result;
    }

}
