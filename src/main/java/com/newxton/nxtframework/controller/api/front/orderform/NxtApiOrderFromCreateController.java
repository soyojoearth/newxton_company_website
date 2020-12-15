package com.newxton.nxtframework.controller.api.front.orderform;

import com.google.gson.Gson;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderFromCreate;
import com.newxton.nxtframework.process.NxtProcessOrderFormCreate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/19
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 购物车-创建订单
 */
@RestController
public class NxtApiOrderFromCreateController {

    @Resource
    private NxtProcessOrderFormCreate nxtProcessOrderFormCreate;

    @RequestMapping(value = "/api/order_form/create", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id",required = false) Long userId, @RequestBody String json) {

        Gson gson = new Gson();
        NxtStructOrderFromCreate nxtStructOrderFromCreate;
        try {
             nxtStructOrderFromCreate = gson.fromJson(json, NxtStructOrderFromCreate.class);
        }
        catch (Exception e){
            return new NxtStructApiResult(54,"json数据不对");
        }

        try {
            Long orderFormId = nxtProcessOrderFormCreate.exec(nxtStructOrderFromCreate,userId);
            return new NxtStructApiResult(orderFormId);
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

    }

}
