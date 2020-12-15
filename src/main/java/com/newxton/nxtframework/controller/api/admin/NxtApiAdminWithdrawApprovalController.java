package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtWithdraw;
import com.newxton.nxtframework.service.NxtWithdrawService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/10
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminWithdrawApprovalController {

    @Resource
    private NxtWithdrawService nxtWithdrawService;

    @RequestMapping(value = "/api/admin/withdraw/approval", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");
        Boolean approval = jsonParam.getBoolean("approval");

        if (id == null || approval == null){
            return new NxtStructApiResult(53,"缺少参数：id或approval").toMap();
        }

        NxtWithdraw nxtWithdraw = nxtWithdrawService.queryById(id);

        if (nxtWithdraw == null){
            return new NxtStructApiResult(53,"该申请不存在").toMap();
        }

        //状态（0:已申请 1:已批准 2:已拒绝 3:已汇款）
        if (nxtWithdraw.getStatus().equals(3)){
            return new NxtStructApiResult(53,"当前状态不可执行该操作").toMap();
        }

        nxtWithdraw.setStatus(approval ? 1 : 2);
        nxtWithdrawService.update(nxtWithdraw);

        return new NxtStructApiResult().toMap();

    }


}
