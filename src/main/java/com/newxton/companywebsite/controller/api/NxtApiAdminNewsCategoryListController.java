package com.newxton.companywebsite.controller.api;

import com.newxton.companywebsite.entity.NxtNewsCategory;
import com.newxton.companywebsite.entity.NxtUploadfileCategory;
import com.newxton.companywebsite.service.NxtNewsCategoryService;
import com.newxton.companywebsite.service.NxtUploadfileCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/23
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminNewsCategoryListController {

    @Resource
    private NxtNewsCategoryService nxtNewsCategoryService;

    @RequestMapping(value = "/api/admin/news_category/list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtNewsCategory> list = nxtNewsCategoryService.queryAll(new NxtNewsCategory());

        List<Map<String,Object>> listResult = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            NxtNewsCategory category = list.get(i);

            if (category.getCategoryPid().equals(0L)){
                Map<String, Object> item = new HashMap<>();
                item.put("category",category);
                //搜寻下级
                item.put("sub_category_list",findSubCategory(category,list));
                listResult.add(item);
            }

        }

        result.put("list",listResult);

        return result;

    }

    /**
     * 遍历搜寻下级类别，最终成树状
     * @param categoryParent
     * @param list
     * @return
     */
    private List<Map<String,Object>> findSubCategory(NxtNewsCategory categoryParent,List<NxtNewsCategory> list){

        List<Map<String,Object>> listResult = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            NxtNewsCategory category = list.get(i);
            if (category.getCategoryPid().equals(categoryParent.getId())){

                Map<String, Object> item = new HashMap<>();
                item.put("category",category);
                //搜寻下级
                item.put("sub_category_list",findSubCategory(category,list));
                listResult.add(item);

            }
        }

        return listResult;

    }

}
