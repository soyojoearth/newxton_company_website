package com.newxton.nxtframework.controller.api.front.ucenter;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessUserAddress;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructUserAddress;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/1
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtAapiUserAddressSetDefaultController {

    @Resource
    private NxtProcessUserAddress nxtProcessUserAddress;

    @RequestMapping(value = "/api/user/address/set_default", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = true) Long userId, @RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");

        if (id == null){
            return new NxtStructApiResult(54,"请提供id");
        }

        try {
            nxtProcessUserAddress.setDefault(userId,id);
            return new NxtStructApiResult();
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

    }

}
