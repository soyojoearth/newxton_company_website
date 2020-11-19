package com.newxton.nxtframework.controller.api.front;

import com.google.gson.Gson;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.model.struct.NxtStructOrderFromCreate;
import com.newxton.nxtframework.process.NxtProcessOrderFormCreate;
import org.springframework.transaction.annotation.Transactional;
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

        if (nxtStructOrderFromCreate.deliveryCountry == null){
            result.put("status", 54);
            result.put("message", "请选择国家");
            return result;
        }
        if (nxtStructOrderFromCreate.deliveryProvince == null){
            result.put("status", 54);
            result.put("message", "请选择省份");
            return result;
        }
        if (nxtStructOrderFromCreate.deliveryCity == null){
            result.put("status", 54);
            result.put("message", "请选择城市");
            return result;
        }
        if (nxtStructOrderFromCreate.deliveryAddress == null || nxtStructOrderFromCreate.deliveryAddress.isEmpty()){
            result.put("status", 54);
            result.put("message", "缺少地址");
            return result;
        }
        if (nxtStructOrderFromCreate.deliveryPerson == null || nxtStructOrderFromCreate.deliveryPerson.isEmpty()){
            result.put("status", 54);
            result.put("message", "缺少收件人");
            return result;
        }
        if (nxtStructOrderFromCreate.deliveryPhone == null || nxtStructOrderFromCreate.deliveryPhone.isEmpty()){
            result.put("status", 54);
            result.put("message", "缺少联系电话");
            return result;
        }
        if (nxtStructOrderFromCreate.deliveryPostcode == null || nxtStructOrderFromCreate.deliveryPostcode.isEmpty()){
            result.put("status", 54);
            result.put("message", "缺少邮编");
            return result;
        }
        if (nxtStructOrderFromCreate.deliveryConfigId == null){
            result.put("status", 54);
            result.put("message", "缺少配送方式");
            return result;
        }
        if (nxtStructOrderFromCreate.dealPlatform == null){
            result.put("status", 54);
            result.put("message", "缺少参数：成交平台（0:web 1:ios 2:android 3:wx ）");
            return result;
        }

        try {
            nxtProcessOrderFormCreate.exec(nxtStructOrderFromCreate,userId);
        }
        catch (NxtException e){
            result.put("status", 54);
            result.put("message", e.getMessage());
        }

        return result;

    }

}
