package com.newxton.nxtframework.controller.api.front.cms;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtContent;
import com.newxton.nxtframework.entity.NxtNewsCategory;
import com.newxton.nxtframework.service.NxtContentService;
import com.newxton.nxtframework.service.NxtNewsCategoryService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/3
 * @address Shenzhen, China
 */
@RestController
public class NxtApiRecommandNewsListController {

    @Resource
    private NxtContentService nxtContentService;

    @Resource
    private NxtNewsCategoryService nxtNewsCategoryService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @RequestMapping("/api/recommand_news_list")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam) {

        Integer limit = jsonParam.getInteger("limit");
        Integer offset = jsonParam.getInteger("offset");

        if (limit == null || offset == null){
            return new NxtStructApiResult(53,"缺少offset、limit");
        }

        List<NxtNewsCategory> contentCategoryList = nxtNewsCategoryService.queryAll(new NxtNewsCategory());

        Map<String, Object> data = new HashMap<>();

        //获取推荐资讯内容
        List<Map<String,Object>> newsList = this.recommandNewsList(offset,limit, contentCategoryList);

        data.put("newsList", newsList);

        return new NxtStructApiResult(data);

    }

    /**
     * 获取推荐资讯内容
     * @return
     */
    private List<Map<String,Object>> recommandNewsList(int offset, int limit, List<NxtNewsCategory> contentCategoryList){

        //类别整理
        Map<Long,String> mapCategoryIdToName = new HashMap<>();
        for (NxtNewsCategory newsCategory : contentCategoryList) {
            mapCategoryIdToName.put(newsCategory.getId(),newsCategory.getCategoryName());
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        //新闻类别列表
        List<NxtContent> contentList = this.nxtContentService.queryRecommandListByLimit(offset,limit);
        List<Map<String,Object>> newsList = new ArrayList<>();
        for (int i = 0; i < contentList.size(); i++) {
            NxtContent content = contentList.get(i);
            String firstPictureUrl = "";
            if (content.getContentDetail() == null){
                content.setContentDetail("");
            }
            Document doc = Jsoup.parse(content.getContentDetail());
            Element elementImg = doc.select("img").last();
            Element firstP = doc.selectFirst("p");
            if (elementImg != null && firstP != null) {
                firstPictureUrl = elementImg.attr("src");
                firstPictureUrl = nxtUploadImageComponent.checkHtmlAndReplaceImageUrlForDisplay(firstPictureUrl);
            }
            else {
                firstPictureUrl = nxtUploadImageComponent.checkHtmlAndReplaceImageUrlForDisplay("/common/images/empty.png");
            }

            Map<String, Object> item = new HashMap<>();
            item.put("id", content.getId());
            item.put("title", content.getContentTitle());
            if (firstP != null) {
                item.put("text", firstP.text());
            }
            else {
                item.put("text", "");
            }
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

        return newsList;

    }

}
