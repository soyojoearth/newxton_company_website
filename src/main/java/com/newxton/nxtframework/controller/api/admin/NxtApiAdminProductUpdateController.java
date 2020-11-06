package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.newxton.nxtframework.model.NxtModelProduct;
import com.newxton.nxtframework.model.struct.NxtStructDeliveryConfig;
import com.newxton.nxtframework.model.struct.NxtStructProduct;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductUpdateController {

    @Resource
    private NxtModelProduct nxtModelProduct;

    @RequestMapping(value = "/api/admin/product/update", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody String json) {

        Gson gson = new Gson();

        NxtStructProduct nxtStructProduct = gson.fromJson(json,NxtStructProduct.class);

        return nxtModelProduct.saveProductAllDetail(nxtStructProduct);

    }

}
