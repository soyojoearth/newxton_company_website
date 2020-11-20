package com.newxton.nxtframework.controller.api.front;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtShoppingCart;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessShoppingCart;
import com.newxton.nxtframework.service.NxtShoppingCartService;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructShoppingCart;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
    private NxtUserService nxtUserService;

    @Resource
    private NxtProcessShoppingCart nxtProcessShoppingCart;

    @RequestMapping("/api/shopping_cart/detail")
    public Map<String, Object> exec(@RequestHeader(value = "user_id", required = false) Long userId,
                                    @RequestHeader(value = "token", required = false) String token,
                                    @RequestBody JSONObject jsonParam) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        //检查是否登录
        Boolean isLogin = false;
        if (!(userId == null || token == null || token.isEmpty())){
            NxtUser user = nxtUserService.queryById(Long.valueOf(userId));
            if (user == null && user.getToken().equals(token)){
                isLogin = true;
            }
        }

        NxtShoppingCart nxtShoppingCart;

        if (isLogin) {
            //查询已登录用户购物车
            nxtShoppingCart = nxtShoppingCartService.queryByUserId(userId);
        } else {
            //查询匿名用户购物车
            String guestToken = jsonParam.getString("guestToken");
            //根据guestToken获取购物车，guestToken错误就新建一个购物车
            nxtShoppingCart = nxtShoppingCartService.queryByToken(guestToken);
        }

        //填坑（没有购物车的用户就直接返回空购物车）
        result.put("detail",new NxtStructShoppingCart());

        if (nxtShoppingCart != null){
            try {
                NxtStructShoppingCart nxtStructShoppingCart = nxtProcessShoppingCart.allDetail(nxtShoppingCart);
                result.put("detail",nxtStructShoppingCart);
            }
            catch (NxtException e){
                result.put("status",54);
                result.put("message",e.getNxtExecptionMessage());
            }
        }

        return result;

    }

}
