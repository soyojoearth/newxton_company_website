package com.newxton.nxtframework.controller.api.admin;

import com.google.gson.Gson;
import com.newxton.nxtframework.model.NxModelShoppingCart;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youjian163mail@163.com
 * @time 2020/11/17
 * @address Suzhou, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminShoppingCartAddController {
	
	/* TODO 待用
	 * @Resource private NxtProductService nxtProductService;
	 * 
	 * @Resource private NxtProductCategoryService nxtProductCategoryService;
	 * 
	 * @Resource private NxtProductPictureService nxtProductPictureService;
	 */

    @RequestMapping(value = "/api/admin/shopping_cart/add_product", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody String json) {
    	// 检查条件guestToken,还是user_id
    	Gson gson = new Gson();
    	NxModelShoppingCart shoppingCart = gson.fromJson(json,NxModelShoppingCart.class);
    	if (shoppingCart.getGuestToken() == null) {
    		this.processGuestShoppingCart();
    	} else {
    		this.processUserShoppingCart();
    	}
    	
    	// 返回状态码
    	Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");
        
        return result;
    }
    
    // TODO 处理未登录用户 匿名购物车流程
    private void processGuestShoppingCart() {
    	
    }
    
    // TODO 处理已登录用户 直接购物车流程 
    private void processUserShoppingCart() {
    	
    }

}
