package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderForm;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/3
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminOrderFormDetailController {

    @Resource
    private NxtProcessOrderForm nxtProcessOrderForm;

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @RequestMapping(value = "/api/admin/order_form/detail", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");

        if (id == null){
            return new NxtStructApiResult(54,"请提交订单id").toMap();
        }

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(id);
        if (nxtOrderForm == null){
            return new NxtStructApiResult(34,"没有该订单").toMap();
        }

        try {
            NxtStructOrderForm nxtStructOrderForm = nxtProcessOrderForm.orderFormDetail(id);
            nxtStructOrderForm.setSellerRemark(nxtOrderForm.getSellerRemark());//仅商家可见字段
            return new NxtStructApiResult(nxtStructOrderForm).toMap();
        }
        catch (NxtException e){
            return new NxtStructApiResult(34,e.getNxtExecptionMessage()).toMap();
        }

    }

}
