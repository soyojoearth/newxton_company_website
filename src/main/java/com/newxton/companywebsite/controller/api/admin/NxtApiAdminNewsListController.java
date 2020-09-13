package com.newxton.companywebsite.controller.api.admin;

import com.newxton.companywebsite.entity.NxtContent;
import com.newxton.companywebsite.entity.NxtNewsCategory;
import com.newxton.companywebsite.entity.NxtUser;
import com.newxton.companywebsite.service.NxtContentService;
import com.newxton.companywebsite.service.NxtNewsCategoryService;
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
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminNewsListController {

    @Resource
    private NxtContentService nxtContentService;

    @Resource
    private NxtNewsCategoryService nxtNewsCategoryService;

    @RequestMapping(value = "/api/admin/news/list", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "category_id", required = false) Long categoryId,
            @RequestParam(value = "page_number", required = false) Integer pageNumber,
            @RequestParam(value = "is_recommend", required = false) Integer isRecommend,
            @RequestParam(value = "search_keyword", required = false) String searchKeyword
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (pageNumber == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先取出所有分类做备用*/
        List<NxtNewsCategory> categoryList = nxtNewsCategoryService.queryAll(new NxtNewsCategory());
        Map<Long,String> categoryNameMap = new HashMap<>();
        for (NxtNewsCategory category: categoryList) {
            categoryNameMap.put(category.getId(),category.getCategoryName());
        }

        int limit = 20;
        int offset = 0;

        if (pageNumber > 1){
            offset = limit * (pageNumber-1);
        }

        Map<String,Object> mapCondition = new HashMap<>();

        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            mapCondition.put("contentTitle","%"+searchKeyword.trim()+"%");
        }
        if (isRecommend != null){
            mapCondition.put("isRecommend",isRecommend);
        }
        if (categoryId != null){
            mapCondition.put("categoryId",categoryId);
        }

        mapCondition.put("offset",offset);
        mapCondition.put("limit",limit);

        List<NxtContent> list = nxtContentService.searchQueryAllByMap(mapCondition);

//        List<NxtContent> list = nxtContentService.selectAllByLimit(offset,limit,categoryId);
        List<Map<String,Object>> listResult = new ArrayList<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            NxtContent content = list.get(i);
            Map<String, Object> item = new HashMap<>();
            item.put("id",content.getId());
            item.put("categoryId",content.getCategoryId());
            if (categoryNameMap.containsKey(content.getCategoryId())){
                item.put("categoryName",categoryNameMap.get(content.getCategoryId()));
            }
            else {
                item.put("categoryName","---");
            }
            item.put("contentTitle",content.getContentTitle());
            item.put("datelineUpdate",content.getDatelineUpdate());
            item.put("datelineUpdateReadable",sdf.format(new Date(content.getDatelineUpdate())));
            item.put("datelineCreate",content.getDatelineCreate());
            item.put("datelineCreateReadable",sdf.format(new Date(content.getDatelineCreate())));
            item.put("isRecommend",content.getIsRecommend());
            listResult.add(item);
        }
        result.put("list",listResult);

        Long totalCount = nxtContentService.countSearchQueryAllByMap(mapCondition);
        Long pageCount =  (long)Math.ceil((float)totalCount / (float)limit);
        result.put("page_count",pageCount);

        return result;

    }

}
