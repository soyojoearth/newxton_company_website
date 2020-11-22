package com.newxton.nxtframework.controller.api.front;

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
 * 购物车-创建订单
 */
@RestController
public class NxtApiOrderFromCreateController {

    @Resource
    private NxtProcessOrderFormCreate nxtProcessOrderFormCreate;

    @RequestMapping(value = "/api/order_form/create", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader("user_id") Long userId, @RequestBody String json) {

        Gson gson = new Gson();
        NxtStructOrderFromCreate nxtStructOrderFromCreate = gson.fromJson(json, NxtStructOrderFromCreate.class);

        try {
            nxtProcessOrderFormCreate.exec(nxtStructOrderFromCreate,userId);
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

        return new NxtStructApiResult();

    }

}
