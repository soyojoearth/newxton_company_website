package com.newxton.supercompanyapi.controller.api.admin;

import com.newxton.supercompanyapi.entity.NxtNewsCategory;
import com.newxton.supercompanyapi.service.NxtNewsCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/23
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminNewsCategoryCreateController {

    @Resource
    private NxtNewsCategoryService nxtNewsCategoryService;

    @RequestMapping(value = "/api/admin/news_category/create", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "category_name", required=false) String categoryName,
                                     @RequestParam(value = "category_pid", required=false) Long categoryPid
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (categoryName == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        if (categoryPid == null){
            categoryPid = 0L;
        }

        if (categoryPid > 0){
            NxtNewsCategory parentCategory = nxtNewsCategoryService.queryById(categoryPid);
            if (parentCategory == null){
                result.put("status", 47);
                result.put("message", "没有对应的上级分类");
                return result;
            }
        }

        NxtNewsCategory newCategory = new NxtNewsCategory();
        newCategory.setCategoryName(categoryName.trim());
        newCategory.setCategoryPid(categoryPid);

        //增加
        NxtNewsCategory categoryCreated = nxtNewsCategoryService.insert(newCategory);

        if (categoryCreated.getId() == null){
            result.put("status", 50);
            result.put("message", "系统错误");
            return result;
        }

        result.put("category",categoryCreated);

        return result;

    }

}
