package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.model.NxtModelProduct;
import com.newxton.nxtframework.service.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductDetailController {

    @Resource
    private NxtModelProduct nxtModelProduct;

    @Resource
    private NxtProductService nxtProductService;

    @RequestMapping(value = "/api/admin/product/detail", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "缺少参数:id");
            return result;
        }

        NxtProduct nxtProduct = nxtProductService.queryById(id);
        if (nxtProduct == null){
            result.put("status", 49);
            result.put("message", "对应的内容不存在");
            return result;
        }

        result.put("detail",nxtModelProduct.productAllDetail(nxtProduct));

        return result;

    }

}
