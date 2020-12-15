package com.newxton.nxtframework.controller.api.front.ucenter;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtUserAddress;
import com.newxton.nxtframework.process.NxtProcessUserAddress;
import com.newxton.nxtframework.service.NxtUserAddressService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructUserAddress;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/1
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserAddressListController {

    @Resource
    private NxtProcessUserAddress nxtProcessUserAddress;

    @RequestMapping(value = "/api/user/address/list", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = true) Long userId, @RequestBody JSONObject jsonParam) {

        List<NxtStructUserAddress> nxtStructUserAddressList = nxtProcessUserAddress.userAddressList(0L,Long.MAX_VALUE,userId);

        return new NxtStructApiResult(nxtStructUserAddressList);

    }

}
