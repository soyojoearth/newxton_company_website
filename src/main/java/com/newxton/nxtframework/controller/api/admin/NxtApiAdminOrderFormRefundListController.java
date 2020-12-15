package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderFormRefund;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderFormRefund;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/6
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminOrderFormRefundListController {

    @Resource
    private NxtProcessOrderFormRefund nxtProcessOrderFormRefund;

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtUserService nxtUserService;

    @RequestMapping(value = "/api/admin/order_form_refund/list", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");
        Integer status = jsonParam.getInteger("status");
        String username = jsonParam.getString("username");
        String orderFormSerialNum = jsonParam.getString("orderFormSerialNum");

        if (offset == null || limit == null){
            return new NxtStructApiResult(54,"缺少offset或limit").toMap();
        }

        Long userId = null;
        if (username != null && !username.trim().isEmpty()) {
            NxtUser user = nxtUserService.queryByUsername(username);
            if (user == null) {
                return new NxtStructApiResult(53,"找不到该用户：" + username).toMap();
            }
            userId = user.getId();
        }

        Long orderFormId = null;
        if (orderFormSerialNum != null && !orderFormSerialNum.trim().isEmpty()){
            NxtOrderForm nxtOrderForm = nxtOrderFormService.queryBySerialNum(orderFormSerialNum);
            if (nxtOrderForm == null){
                return new NxtStructApiResult(53,"找不到该订单："+orderFormSerialNum).toMap();
            }
        }

        try {
            List<NxtStructOrderFormRefund> list = nxtProcessOrderFormRefund.adminQueryList(offset,limit,status,userId,orderFormId);
            Long count = nxtProcessOrderFormRefund.adminQueryCount(offset,limit,status,userId,orderFormId);
            Map<String,Object> result = new HashMap<>();
            result.put("list",list);
            result.put("count",count);
            return new NxtStructApiResult(result).toMap();
        }
        catch (NxtException e){
            return new NxtStructApiResult(34,e.getNxtExecptionMessage()).toMap();
        }

    }

}
