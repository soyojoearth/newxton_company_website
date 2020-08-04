package com.newxton.companywebsite.controller.api.admin;

import com.newxton.companywebsite.entity.NxtContent;
import com.newxton.companywebsite.entity.NxtNewsCategory;
import com.newxton.companywebsite.service.NxtContentService;
import com.newxton.companywebsite.service.NxtNewsCategoryService;
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
public class NxtApiAdminNewsCategoryDeleteController {

    @Resource
    private NxtNewsCategoryService nxtNewsCategoryService;

    @Resource
    private NxtContentService nxtContentService;

    @RequestMapping(value = "/api/admin/news_category/delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null){
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        NxtNewsCategory category = nxtNewsCategoryService.queryById(id);

        if (category == null){
            result.put("status", 48);
            result.put("message", "该类别不存在");
            return result;
        }

        /*检查有没有子类*/
        NxtNewsCategory categoryCondition = new NxtNewsCategory();
        categoryCondition.setCategoryPid(category.getId());
        List<NxtNewsCategory> list = nxtNewsCategoryService.queryAll(categoryCondition);
        if (list.size()>0){
            result.put("status", 56);
            result.put("message", "类别下还有子类，请先删除子类");
            return result;
        }

        /*检查该类别下面有没有内容*/
        NxtContent nxtContent = new NxtContent();
        nxtContent.setCategoryId(category.getId());
        Long count = nxtContentService.queryCount(nxtContent);
        if (count > 0){
            result.put("status", 55);
            result.put("message", "类别下还有东西，请先转移掉");
            return result;
        }

        /*删除*/
        nxtNewsCategoryService.deleteById(category.getId());

        return result;

    }
}
