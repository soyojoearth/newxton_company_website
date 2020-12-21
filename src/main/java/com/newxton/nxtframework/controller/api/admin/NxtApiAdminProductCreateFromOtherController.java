package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessProduct;
import com.newxton.nxtframework.process.NxtProcessProductSpider;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructProduct;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/19
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 抓取第三方平台商品并创建产品
 */
@RestController
public class NxtApiAdminProductCreateFromOtherController {

    @Resource
    private NxtProcessProduct nxtProcessProduct;

    @Resource
    private NxtProcessProductSpider nxtProcessProductSpider;

    @RequestMapping(value = "/api/admin/product/create_from_other", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        String externalUrl = jsonParam.getString("externalUrl");
        Long categoryId = jsonParam.getLong("categoryId");
        Long deliveryConfigId = jsonParam.getLong("deliveryConfigId");

        if (categoryId == null){
            return new NxtStructApiResult(52,"请选择类别").toMap();
        }
        if (deliveryConfigId == null){
            return new NxtStructApiResult(52,"请选择运费模版").toMap();
        }

        try {
            //抓取产品
            NxtStructProduct nxtStructProduct =  nxtProcessProductSpider.catchProductFromOther(externalUrl);
            if (nxtStructProduct == null){
                return new NxtStructApiResult(52,"抓取失败").toMap();
            }
            nxtStructProduct.setCategoryId(categoryId);
            nxtStructProduct.setDeliveryConfigId(deliveryConfigId);
            //创建
            NxtStructProduct detail =  nxtProcessProduct.saveProductAllDetail(nxtStructProduct);
            return new NxtStructApiResult(detail).toMap();
        }
        catch (NxtException e){
            return new NxtStructApiResult(53,e.getNxtExecptionMessage()).toMap();
        }

    }

}
