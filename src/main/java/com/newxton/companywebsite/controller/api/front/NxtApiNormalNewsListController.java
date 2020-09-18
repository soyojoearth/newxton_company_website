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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/18
 * @address Shenzhen, China
 */
@RestController
public class NxtApiNormalNewsListController {


    @Value("${newxton.config.qiniuDomain}")
    private String qiniuDomain;

    @Resource
    private NxtSettingService nxtSettingService;

    @Resource
    private NxtContentService nxtContentService;

    @Resource
    private NxtNewsCategoryService nxtNewsCategoryService;

    @RequestMapping("/api/normal_news_list")
    public Map<String,Object> index(
            @RequestParam(value = "root_category_id",required = false) Long rootCategoryId,
            @RequestParam("limit") Integer limit,
            @RequestParam(value = "offset",required = false) Integer offset,
            @RequestParam(value = "show_pages",required = false) Integer show_pages
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (offset == null){
            offset = 0;
        }

        List<NxtNewsCategory> contentCategoryList = nxtNewsCategoryService.queryAll(new NxtNewsCategory());

        List<Long> categoryIdList = new ArrayList<>();

        if (rootCategoryId == null){
            for (NxtNewsCategory newsCategory :
                    contentCategoryList) {
                categoryIdList.add(newsCategory.getId());
            }
        }
        else {
            NxtNewsCategory newsCategory = nxtNewsCategoryService.queryById(rootCategoryId);
            categoryIdList = findSubCategory(newsCategory, contentCategoryList);
            categoryIdList.add(rootCategoryId);
        }

        //获取这些子类的内容
        List<Map<String,Object>> newsList = getNewsList(offset,limit,categoryIdList, contentCategoryList);

        result.put("data", newsList);

        //显示页码
        if (show_pages != null && show_pages.equals(1)){
            Long count = this.getNewsCount(categoryIdList, contentCategoryList);
            int pages = (int) Math.ceil((float)count / limit);
            result.put("pages", pages);
        }

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
    private List<Map<String,Object>> getNewsList(int offset, int limit,List<Long> categoryIdList, List<NxtNewsCategory> contentCategoryList){

        //类别整理
        Map<Long,String> mapCategoryIdToName = new HashMap<>();
        for (NxtNewsCategory newsCategory :
                contentCategoryList) {
            mapCategoryIdToName.put(newsCategory.getId(),newsCategory.getCategoryName());
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        //新闻类别列表
        List<NxtContent> contentList = this.nxtContentService.selectByCategoryIdSet(offset,limit*3,categoryIdList);
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
                item.put("title", content.getContentTitle());
                item.put("text", firstP.text());
                item.put("picUrl", firstPictureUrl);
                item.put("time",sdf.format(new Date(content.getDatelineCreate())));
                if (mapCategoryIdToName.containsKey(content.getCategoryId())){
                    item.put("categoryName", mapCategoryIdToName.get(content.getCategoryId()));
                }
                else {
                    item.put("categoryName", "---");
                }
                newsList.add(item);
            }
            if (newsList.size() == limit){
                break;
            }
        }

        return newsList;

    }

    /**
     * 获取某些类别下的资讯推荐内容(统计数量)
     * @return
     */
    private Long getNewsCount(List<Long> categoryIdList, List<NxtNewsCategory> contentCategoryList){
        //类别整理
        Map<Long,String> mapCategoryIdToName = new HashMap<>();
        for (NxtNewsCategory newsCategory :
                contentCategoryList) {
            mapCategoryIdToName.put(newsCategory.getId(),newsCategory.getCategoryName());
        }
        Long count = this.nxtContentService.countSelectByCategoryIdSet(categoryIdList);
        return count;
    }

}
