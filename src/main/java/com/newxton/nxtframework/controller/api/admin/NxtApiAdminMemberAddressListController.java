package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.process.NxtProcessUserAddress;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructUserAddress;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminMemberAddressListController {

    @Resource
    private NxtProcessUserAddress nxtProcessUserAddress;

    @RequestMapping(value = "/api/admin/member_address/list", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long userId = jsonParam.getLong("userId");

        if (userId == null){
            return new NxtStructApiResult(53,"缺少参数：userId").toMap();
        }

        List<NxtStructUserAddress> nxtStructUserAddressList = nxtProcessUserAddress.userAddressList(0L,Long.MAX_VALUE,userId);

        return new NxtStructApiResult(nxtStructUserAddressList).toMap();

    }

}
