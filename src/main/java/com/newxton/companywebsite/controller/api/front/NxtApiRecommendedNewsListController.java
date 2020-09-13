package com.newxton.companywebsite.controller.api.front;

import com.newxton.companywebsite.entity.NxtContent;
import com.newxton.companywebsite.entity.NxtNewsCategory;
import com.newxton.companywebsite.service.NxtContentService;
import com.newxton.companywebsite.service.NxtNewsCategoryService;
import com.newxton.companywebsite.service.NxtSettingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/18
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiRecommendedNewsListController {


    @Value("${newxton.config.qiniuDomain}")
    private String qiniuDomain;

    @Resource
    private NxtSettingService nxtSettingService;

    @Resource
    private NxtContentService nxtContentService;

    @Resource
    private NxtNewsCategoryService nxtNewsCategoryService;

    @RequestMapping("/api/recommended_news_list")
    public Map<String,Object> index(@RequestParam("root_category_id") Long rootCategoryId) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtNewsCategory> contentCategoryList = nxtNewsCategoryService.queryAll(new NxtNewsCategory());

        //新闻资讯
        NxtNewsCategory newsCategory = nxtNewsCategoryService.queryById(rootCategoryId);
        List<Long> categoryIdList = findSubCategory(newsCategory,contentCategoryList);
        categoryIdList.add(rootCategoryId);
        List<Map<String,Object>> newsList = getNewsList(categoryIdList, contentCategoryList, newsCategory.getCategoryName());

        result.put("data", newsList);

        return result;

    }

    /**
     * 遍历搜寻子孙类别的全部Id
     * @param categoryParent
     * @param list
     * @return
     */
    private List<Long> findSubCategory(NxtNewsCategory categoryParent,List<NxtNewsCategory> list){

        List<Long> resultIdList = new ArrayList<>();

        if (categoryParent == null){
            return resultIdList;
        }

        for (int i = 0; i < list.size(); i++) {
            NxtNewsCategory category = list.get(i);
            if (category.getCategoryPid().equals(categoryParent.getId())){
                resultIdList.add(category.getId());
                resultIdList.addAll(findSubCategory(category,list));
            }
        }

        return resultIdList;

    }


    /**
     * 获取某些类别下的资讯推荐内容
     * @return
     */
    private List<Map<String,Object>> getNewsList(List<Long> categoryIdList, List<NxtNewsCategory> contentCategoryList, String defaultCategoryName){

        //类别整理
        Map<Long,String> mapCategoryIdToName = new HashMap<>();
        for (NxtNewsCategory newsCategory :
                contentCategoryList) {
            mapCategoryIdToName.put(newsCategory.getId(),newsCategory.getCategoryName());
        }

        //新闻类别列表
        List<NxtContent> contentList = this.nxtContentService.selectByCategoryIdSet(0,4,categoryIdList);
        List<Map<String,Object>> newsList = new ArrayList<>();
        for (int i = 0; i < contentList.size(); i++) {
            NxtContent content = contentList.get(i);
            String firstPictureUrl = "";
            Document doc = Jsoup.parse(content.getContentDetail());
            Element elementImg = doc.select("img").last();
            Element firstP = doc.selectFirst("p");
            if (elementImg != null && firstP != null) {
                firstPictureUrl = elementImg.attr("src").replace("http://newxton-image-domain", this.qiniuDomain);
            }

            if (!firstPictureUrl.isEmpty()) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", content.getId());
                item.put("text", firstP.text());
                item.put("picUrl", firstPictureUrl);
                if (mapCategoryIdToName.containsKey(content.getCategoryId())){
                    item.put("categoryName", mapCategoryIdToName.get(content.getCategoryId()));
                }
                else {
                    item.put("categoryName", defaultCategoryName);
                }
                newsList.add(item);
            }
            if (newsList.size() == 4){
                break;
            }
        }

        return newsList;

    }


}
