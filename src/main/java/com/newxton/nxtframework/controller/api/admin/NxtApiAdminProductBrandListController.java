package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtProductBrand;
import com.newxton.nxtframework.service.NxtProductBrandService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/4
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminProductBrandListController {

    @Resource
    private NxtProductBrandService nxtProductBrandService;

    @RequestMapping(value = "/api/admin/product_brand/list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtProductBrand> nxtProductBrands = nxtProductBrandService.queryAllOrderByNameASC();

        result.put("list",nxtProductBrands);

        return result;

    }

}
