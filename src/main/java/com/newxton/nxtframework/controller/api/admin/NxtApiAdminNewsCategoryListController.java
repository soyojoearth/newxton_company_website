package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtNewsCategory;
import com.newxton.nxtframework.service.NxtNewsCategoryService;
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
 * @copyright NxtFramework
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

        List<Map<String,Object>> listSimpleResult = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {

            NxtNewsCategory category = list.get(i);

            if (category.getCategoryPid().equals(0L)){
                Map<String, Object> item = new HashMap<>();
                item.put("category",category);
                //搜寻下级
                List<Map<String,Object>> subCategoryList = findSubCategory(category,list);
                item.put("sub_category_list",subCategoryList);
                listResult.add(item);

                //前端简化显示listSimpleResult
                Map<String, Object> simpleItem = new HashMap<>();
                simpleItem.put("category_name",category.getCategoryName());
                simpleItem.put("category_name_display",category.getCategoryName());
                simpleItem.put("category_id",category.getId());
                simpleItem.put("category_pid",category.getCategoryPid());

                listSimpleResult.add(simpleItem);

                if (subCategoryList.size() > 0){
                    this.addSubCategoryListToSimpleList(subCategoryList,listSimpleResult,"--");
                }

            }

        }

        result.put("list",listResult);

        result.put("list_simple",listSimpleResult);

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


    /**
     * 照顾前端开发工程师，增加这样的显示
     * 分类
     * --分类
     * ----分类
     * 分类
     * --分类
     * @param subCategoryList
     * @param listSimpleResult
     * @param preStr
     */
    private void addSubCategoryListToSimpleList(List<Map<String,Object>> subCategoryList, List<Map<String,Object>> listSimpleResult,String preStr){
        for (int i = 0; i < subCategoryList.size(); i++) {
            Map<String, Object> item = subCategoryList.get(i);
            NxtNewsCategory category = (NxtNewsCategory)item.get("category");
            List<Map<String,Object>> sub_category_list = (List<Map<String,Object>>)item.get("sub_category_list");

            Map<String, Object> simpleItem = new HashMap<>();
            simpleItem.put("category_name",category.getCategoryName());
            simpleItem.put("category_name_display",preStr+category.getCategoryName());
            simpleItem.put("category_id",category.getId());
            simpleItem.put("category_pid",category.getCategoryPid());

            listSimpleResult.add(simpleItem);

            if (sub_category_list.size()>0){
                this.addSubCategoryListToSimpleList(sub_category_list,listSimpleResult,preStr + "--");
            }
        }
    }


}
