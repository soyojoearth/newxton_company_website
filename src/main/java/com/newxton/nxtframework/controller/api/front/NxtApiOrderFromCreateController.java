package com.newxton.nxtframework.controller.api.front;

import com.google.gson.Gson;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.struct.NxtStructOrderFromCreate;
import com.newxton.nxtframework.process.NxtProcessOrderFormCreate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Object> exec(@RequestHeader("user_id") Long userId, @RequestBody String json) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        Gson gson = new Gson();
        NxtStructOrderFromCreate nxtStructOrderFromCreate = gson.fromJson(json, NxtStructOrderFromCreate.class);

        try {
            nxtProcessOrderFormCreate.exec(nxtStructOrderFromCreate,userId);
        }
        catch (NxtException e){
            result.put("status", 54);
            result.put("message", e.getNxtExecptionMessage());
        }

        return result;

    }

}
