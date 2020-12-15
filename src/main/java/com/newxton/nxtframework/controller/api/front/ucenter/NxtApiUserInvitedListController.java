package com.newxton.nxtframework.controller.api.front.ucenter;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessInvite;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructInvitees;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/1
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 我的下家
 */
@RestController
public class NxtApiUserInvitedListController {

    @Resource
    private NxtProcessInvite nxtProcessInvite;

    @RequestMapping(value = "/api/user/invited/list", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = true) Long userId, @RequestBody JSONObject jsonParam) {

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");

        Long inviterLevel = jsonParam.getLong("inviterLevel");

        if (offset == null || limit == null){
            return new NxtStructApiResult(53,"缺少offset或limit");
        }

        if (inviterLevel == null){
            return new NxtStructApiResult(53,"缺少参数：inviterLevel");
        }

        try {
            List<NxtStructInvitees> nxtStructInviteesList = nxtProcessInvite.userInvitees(offset,limit,userId,inviterLevel);
            return new NxtStructApiResult(nxtStructInviteesList);
        }
        catch (NxtException e){
            return new NxtStructApiResult(53,e.getNxtExecptionMessage());
        }

    }

}
