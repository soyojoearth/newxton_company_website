package com.newxton.nxtframework.controller.api.front.product;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.process.NxtProcessProduct;
import com.newxton.nxtframework.service.*;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructProduct;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/18
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiProductDetailController  {

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtProcessProduct nxtProcessProduct;

    @RequestMapping("/api/product_detail")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam) {

        Long productId = jsonParam.getLong("product_id");

        NxtProduct nxtProduct = nxtProductService.queryById(productId);

        if (nxtProduct == null){
            return new NxtStructApiResult(new NxtStructProduct());
        }

        NxtStructProduct nxtStructProduct = nxtProcessProduct.getProductAllDetail(nxtProduct);

        return new NxtStructApiResult(nxtStructProduct);

    }

}
