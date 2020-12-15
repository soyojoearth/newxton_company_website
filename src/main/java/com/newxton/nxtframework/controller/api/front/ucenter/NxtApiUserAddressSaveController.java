package com.newxton.nxtframework.controller.api.front.ucenter;

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
public class NxtApiUserAddressSaveController {

    @Resource
    private NxtProcessUserAddress nxtProcessUserAddress;

    @RequestMapping(value = "/api/user/address/save", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = true) Long userId, @RequestBody String json) {

        Gson gson = new Gson();
        NxtStructUserAddress nxtStructUserAddress;
        try {
            nxtStructUserAddress = gson.fromJson(json, NxtStructUserAddress.class);
        }
        catch (Exception e){
            return new NxtStructApiResult(54,"json数据不对");
        }

        try {
            nxtProcessUserAddress.saveUserAddress(userId,nxtStructUserAddress);
            return new NxtStructApiResult();
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

    }

}
