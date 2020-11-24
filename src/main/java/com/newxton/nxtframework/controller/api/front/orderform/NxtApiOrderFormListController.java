package com.newxton.nxtframework.controller.api.front.orderform;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.struct.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/24
 * @address Shenzhen, China
 */
@RestController
public class NxtApiOrderFormListController {

    @Resource
    private NxtProcessOrderForm nxtProcessOrderForm;

    @RequestMapping(value = "/api/order_form/list", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader(value = "user_id", required = true) Long userId, @RequestBody JSONObject jsonParam) {

        Boolean isPaid = jsonParam.getBoolean("isPaid");
        Boolean isDelivery = jsonParam.getBoolean("isDelivery");
        Boolean isReviews = jsonParam.getBoolean("isReviews");


        long offset = 0;
        long limit = 10;

        List<NxtStructOrderForm> nxtStructOrderFormList = nxtProcessOrderForm.userOrderFormList(userId,offset,limit,isPaid,isDelivery,isReviews);

        return new NxtStructApiResult(nxtStructOrderFormList);

    }

}
