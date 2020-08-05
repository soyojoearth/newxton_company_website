package com.newxton.companywebsite.controller.api.admin;

import com.newxton.companywebsite.entity.NxtContent;
import com.newxton.companywebsite.entity.NxtProduct;
import com.newxton.companywebsite.service.NxtContentService;
import com.newxton.companywebsite.service.NxtProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminProductDeleteController {

    @Resource
    private NxtProductService nxtProductService;

    @RequestMapping(value = "/api/admin/product/delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先查询，再删除*/
        NxtProduct product = nxtProductService.queryById(id);
        if (product == null){
            result.put("status", 49);
            result.put("message", "对应的产品不存在");
            return result;
        }

        nxtProductService.deleteById(product.getId());

        return result;

    }

}
