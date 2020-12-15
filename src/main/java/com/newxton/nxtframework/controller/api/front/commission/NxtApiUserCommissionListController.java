package com.newxton.nxtframework.controller.api.front.commission;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.process.NxtProcessCommission;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructUserCommission;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/29
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserCommissionListController {

    @Resource
    private NxtProcessCommission nxtProcessCommission;

    @RequestMapping(value = "/api/user/commission/list", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = true) Long userId, @RequestBody JSONObject jsonParam) {

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");

        if (offset == null || limit == null){
            return new NxtStructApiResult(53,"缺少offset或limit");
        }

        List<NxtStructUserCommission> list = nxtProcessCommission.userCommissionList(offset,limit,userId);

        return new NxtStructApiResult(list);

    }

}
