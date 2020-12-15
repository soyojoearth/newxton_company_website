package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.service.NxtProductService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductUpdateCommissionRateController {
    @Resource
    private NxtProductService nxtProductService;

    @RequestMapping(value = "/api/admin/product/update_commission_rate", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long productId = jsonParam.getLong("productId");
        Long rate = jsonParam.getLong("rate");

        if (productId == null){
            return new NxtStructApiResult(53,"缺少参数：productId").toMap();
        }

        /*先查询*/
        NxtProduct nxtProduct = nxtProductService.queryById(productId);
        if (nxtProduct == null){
            return new NxtStructApiResult(49,"对应的产品不存在").toMap();
        }

        if (rate > 100 || rate < 0){
            return new NxtStructApiResult(49,"rate接受0-100之间的整数").toMap();
        }

        nxtProduct.setCommissionRate(rate);
        nxtProductService.update(nxtProduct);
        return new NxtStructApiResult().toMap();

    }
}
