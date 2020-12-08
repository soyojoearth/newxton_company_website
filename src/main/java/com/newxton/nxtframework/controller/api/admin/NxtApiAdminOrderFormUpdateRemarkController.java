package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/3
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminOrderFormUpdateRemarkController {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @RequestMapping(value = "/api/admin/order_form/update_remark", method = RequestMethod.POST)
    public Map<String, Object> exec(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");
        String remark = jsonParam.getString("remark");

        if (id == null){
            return new NxtStructApiResult(53,"缺少参数：id").toMap();
        }
        if (remark == null || remark.isEmpty()){
            return new NxtStructApiResult(53,"请填写备注").toMap();
        }

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(id);
        if (nxtOrderForm == null){
            return new NxtStructApiResult(53,"找不到对应订单").toMap();
        }

        nxtOrderForm.setSellerRemark(remark);
        nxtOrderFormService.update(nxtOrderForm);

        return new NxtStructApiResult().toMap();

    }

}
