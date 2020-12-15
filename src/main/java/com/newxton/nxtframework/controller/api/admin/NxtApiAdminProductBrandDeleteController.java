package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.entity.NxtProductBrand;
import com.newxton.nxtframework.service.NxtProductBrandService;
import com.newxton.nxtframework.service.NxtProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/4
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductBrandDeleteController {

    @Resource
    private NxtProductBrandService nxtProductBrandService;

    @Resource
    private NxtProductService nxtProductService;

    @RequestMapping(value = "/api/admin/product_brand/delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先查询*/
        NxtProductBrand content = nxtProductBrandService.queryById(id);
        if (content == null){
            result.put("status", 49);
            result.put("message", "找不到对应的内容");
            return result;
        }

        //检查冲突
        NxtProduct nxtProductCondition = new NxtProduct();
        nxtProductCondition.setBrandId(content.getId());

        Long count = nxtProductService.queryCount(nxtProductCondition);

        if (count > 0){
            result.put("status", 55);
            result.put("message", "该品牌已被产品引用，请先取消引用");
            return result;
        }

        nxtProductBrandService.deleteById(content.getId());

        return result;

    }

}
