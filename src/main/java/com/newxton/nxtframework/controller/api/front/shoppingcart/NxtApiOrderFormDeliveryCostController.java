package com.newxton.nxtframework.controller.api.front.shoppingcart;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtShoppingCart;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderFormCreate;
import com.newxton.nxtframework.service.NxtShoppingCartService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiOrderFormDeliveryCostController {

    @Resource
    private NxtProcessOrderFormCreate nxtProcessOrderFormCreate;

    @Resource
    private NxtShoppingCartService nxtShoppingCartService;

    @RequestMapping("/api/order_form/delivery_cost")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam, @RequestHeader(value = "user_id", required = false) Long userId) {

        Long deliveryCountry = jsonParam.getLong("deliveryCountry");
        Long deliveryProvince = jsonParam.getLong("deliveryProvince");
        Long deliveryCity = jsonParam.getLong("deliveryCity");

        if (deliveryCountry == null){
            return new NxtStructApiResult(54,"缺少国家");
        }
        if (deliveryProvince == null){
            return new NxtStructApiResult(54,"缺少省份");
        }
        if (deliveryCity == null){
            return new NxtStructApiResult(54,"缺少城市");
        }

        NxtShoppingCart nxtShoppingCart;

        if (userId != null) {
            //查询已登录用户购物车
            nxtShoppingCart = nxtShoppingCartService.queryByUserId(userId);
        } else {
            //查询匿名用户购物车
            String guestToken = jsonParam.getString("guestToken");
            nxtShoppingCart = nxtShoppingCartService.queryByToken(guestToken);
        }

        if (nxtShoppingCart == null){
            return new NxtStructApiResult(0);
        }

        try {
            //计算运费
            Long deliveryCost = nxtProcessOrderFormCreate.calculateDeliveryCost(
                    nxtShoppingCart,
                    deliveryCountry,
                    deliveryProvince,
                    deliveryCity);
            return new NxtStructApiResult(deliveryCost/100F);
        }
        catch (NxtException e){
            return new NxtStructApiResult(43,e.getNxtExecptionMessage());
        }


    }

}
