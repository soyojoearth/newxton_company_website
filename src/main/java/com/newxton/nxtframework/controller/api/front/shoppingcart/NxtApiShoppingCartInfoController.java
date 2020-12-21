package com.newxton.nxtframework.controller.api.front.shoppingcart;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtShoppingCart;
import com.newxton.nxtframework.entity.NxtShoppingCartProduct;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessShoppingCart;
import com.newxton.nxtframework.service.NxtShoppingCartProductService;
import com.newxton.nxtframework.service.NxtShoppingCartService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/22
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiShoppingCartInfoController {

    private Logger logger = LoggerFactory.getLogger(NxtApiShoppingCartInfoController.class);

    @Resource
    private NxtShoppingCartService nxtShoppingCartService;

    @Resource
    private NxtShoppingCartProductService nxtShoppingCartProductService;

    @Resource
    private NxtProcessShoppingCart nxtProcessShoppingCart;

    @RequestMapping(value = "/api/shopping_cart/info", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = false) Long userId,
                                   @RequestHeader(value = "shopping_cart_token", required = false) String shoppingCartToken
    ) {

        NxtShoppingCart nxtShoppingCart;

        if (userId != null) {
            //查询已登录用户购物车
            nxtShoppingCart = nxtShoppingCartService.queryByUserId(userId);
            //检查合并购物车
            if (shoppingCartToken != null){
                try {
                    nxtProcessShoppingCart.mergeGuestShoppingCartToUser(shoppingCartToken, userId);
                    nxtShoppingCart = nxtShoppingCartService.queryByUserId(userId);
                }
                catch (NxtException e) {
                    //合并失败就不合并了
                    logger.error("用户："+userId+" 购物车合并失败："+e.getNxtExecptionMessage());
                }
            }
        } else {
            //查询匿名用户购物车
            nxtShoppingCart = nxtShoppingCartService.queryByToken(shoppingCartToken);
            if (nxtShoppingCart != null && nxtShoppingCart.getUserId() != null){
                //已有归属的购物车，不能仅靠单独shoppingCartToken操作
                nxtShoppingCart = null;
            }
        }

        Long countSelected = 0L;
        Long countAll = 0L;

        //查询购物车
        if (nxtShoppingCart != null){
            List<NxtShoppingCartProduct> nxtShoppingCartProductList = nxtShoppingCartProductService.queryAllProductByShoppingCartId(nxtShoppingCart.getId());
            for (NxtShoppingCartProduct item :
                    nxtShoppingCartProductList) {
                if (item.getSelected() > 0){
                    countSelected += item.getQuantity();
                }
                countAll += item.getQuantity();
            }
        }

        Map<String,Object> data = new HashMap<>();
        data.put("countChecked",countSelected);
        data.put("countAll",countAll);

        return new NxtStructApiResult(data);

    }

}
