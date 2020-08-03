package com.newxton.companywebsite.controller.api.admin;

import com.newxton.companywebsite.entity.NxtProduct;
import com.newxton.companywebsite.entity.NxtProductCategory;
import com.newxton.companywebsite.entity.NxtUploadfile;
import com.newxton.companywebsite.service.NxtProductCategoryService;
import com.newxton.companywebsite.service.NxtProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminProductCategoryDeleteController {

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    @Resource
    private NxtProductService nxtProductService;

    @RequestMapping(value = "/api/admin/product_category/delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null){
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        NxtProductCategory category = nxtProductCategoryService.queryById(id);

        if (category == null){
            result.put("status", 48);
            result.put("message", "该类别不存在");
            return result;
        }

        /*检查有没有子类*/
        NxtProductCategory categoryCondition = new NxtProductCategory();
        categoryCondition.setCategoryPid(category.getId());
        List<NxtProductCategory> list = nxtProductCategoryService.queryAll(categoryCondition);
        if (list.size()>0){
            result.put("status", 56);
            result.put("message", "类别下还有子类，请先删除子类");
            return result;
        }

        /*检查该类别下面有没有内容*/
        NxtProduct nxtProduct = new NxtProduct();
        nxtProduct.setCategoryId(category.getId());
        Long count = nxtProductService.queryCount(nxtProduct);
        if (count > 0){
            result.put("status", 55);
            result.put("message", "类别下还有东西，请先转移掉");
            return result;
        }

        /*删除*/
        nxtProductCategoryService.deleteById(category.getId());

        return result;

    }
}
