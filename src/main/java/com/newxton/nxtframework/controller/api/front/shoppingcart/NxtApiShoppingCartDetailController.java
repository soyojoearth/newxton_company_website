package com.newxton.nxtframework.controller.api.front.shoppingcart;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtShoppingCart;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessShoppingCart;
import com.newxton.nxtframework.service.NxtShoppingCartService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructShoppingCart;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/20
 * @address Shenzhen, China
 */
@RestController
public class NxtApiShoppingCartDetailController {

    @Resource
    private NxtShoppingCartService nxtShoppingCartService;

    @Resource
    private NxtProcessShoppingCart nxtProcessShoppingCart;

    @RequestMapping("/api/shopping_cart/detail")
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = false) Long userId,
                                   @RequestBody JSONObject jsonParam) {

        NxtShoppingCart nxtShoppingCart;

        if (userId != null) {
            //查询已登录用户购物车
            nxtShoppingCart = nxtShoppingCartService.queryByUserId(userId);
        } else {
            //查询匿名用户购物车
            String guestToken = jsonParam.getString("guestToken");
            //根据guestToken获取购物车，guestToken错误就新建一个购物车
            nxtShoppingCart = nxtShoppingCartService.queryByToken(guestToken);
        }

        if (nxtShoppingCart != null){
            try {
                NxtStructShoppingCart nxtStructShoppingCart = nxtProcessShoppingCart.allDetail(nxtShoppingCart);
                return new NxtStructApiResult(nxtStructShoppingCart);
            }
            catch (NxtException e){
                return new NxtStructApiResult(54,e.getNxtExecptionMessage());
            }
        }

        //没有购物车的用户就直接返回空购物车
        return new NxtStructApiResult(new NxtStructShoppingCart());

    }

}
