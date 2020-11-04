package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.model.NxtModelProduct;
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
public class NxtApiAdminProductCreateController {

    @Resource
    private NxtModelProduct nxtModelProduct;

    @RequestMapping(value = "/api/admin/product/create", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        jsonParam.put("id",null);

        return nxtModelProduct.saveProductAllDetail(jsonParam);

    }

}
