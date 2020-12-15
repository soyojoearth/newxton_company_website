package com.newxton.nxtframework.controller.api.front.commission;

import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessCommission;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/29
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserCommissionCashOutController {

    @Resource
    private NxtProcessCommission nxtProcessCommission;

    @RequestMapping(value = "/api/user/commission/cash_out", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader("user_id") Long userId) {

        try {
            nxtProcessCommission.userCashOut(userId);
            return new NxtStructApiResult();
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

    }

}
