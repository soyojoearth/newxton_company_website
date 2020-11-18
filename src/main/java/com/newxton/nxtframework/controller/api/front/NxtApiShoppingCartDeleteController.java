package com.newxton.nxtframework.controller.api.front;

import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtAclRole;
import com.newxton.nxtframework.entity.NxtShoppingCart;
import com.newxton.nxtframework.model.struct.NxtStructShoppingCart;
import com.newxton.nxtframework.model.struct.NxtStructShoppingCartItem;
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
    		return this.processUserShoppingCartDelProduct(result, userId, json);
    	} else {
    		return this.processGuestShoppingCartDelProduct(result, json);
    	}
    }
    
    // TODO 处理匿名购物车
    private Map<String, Object> processGuestShoppingCartDelProduct(Map<String, Object> result, String json) {
    	return result;
    }
    
    // 处理已登录用户  
    private Map<String, Object> processUserShoppingCartDelProduct(Map<String, Object> result, Long userId, String json) {
    	// 查询购物车
    	NxtShoppingCart shoppingCart = nxtShoppingCartService.queryByUserId(userId);

        if (shoppingCart == null) {
            result.put("status", 100010);
            result.put("message", "当前用户:" + userId + "无购物车记录");
            return result;
        }
        
        
        
        return result;
    }

}
