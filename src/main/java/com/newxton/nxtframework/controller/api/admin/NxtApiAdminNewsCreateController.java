package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtContent;
import com.newxton.nxtframework.entity.NxtNewsCategory;
import com.newxton.nxtframework.service.NxtContentService;
import com.newxton.nxtframework.service.NxtNewsCategoryService;
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
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminNewsCreateController {

    @Resource
    private NxtContentService nxtContentService;

    @Resource
    private NxtNewsCategoryService nxtNewsCategoryService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @RequestMapping(value = "/api/admin/news/create", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "category_id", required=false) Long categoryId,
                                     @RequestParam(value = "content_title", required=false) String contentTitle,
                                     @RequestParam(value = "content_detail", required=false) String contentDetail,
                                     @RequestParam(value = "is_recommend", required=false) Integer isRecommend
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (contentTitle == null || contentDetail == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        if (categoryId == null){
            categoryId = 0L;
        }

        if (isRecommend == null){
            isRecommend = 0;
        }
        if (!isRecommend.equals(0)){
            isRecommend = 1;
        }

        contentTitle = contentTitle.trim();

        /*检查分类*/
        if (categoryId > 0) {
            NxtNewsCategory category = nxtNewsCategoryService.queryById(categoryId);
            if (category == null) {
                result.put("status", 48);
                result.put("message", "该类别不存在");
                return result;
            }
        }

        //把第三方图片抓取过来，存放到自己这里
        contentDetail = nxtUploadImageComponent.checkHtmlAndSavePic(contentDetail);

        /*添加内容*/
        NxtContent content = new NxtContent();
        content.setCategoryId(categoryId);
        content.setContentTitle(contentTitle);
        content.setContentDetail(nxtUploadImageComponent.checkHtmlAndReplaceImageUrlForSave(contentDetail));
        content.setDatelineCreate(System.currentTimeMillis());
        content.setDatelineUpdate(content.getDatelineCreate());
        content.setIsRecommend(isRecommend);

        NxtContent contentCreated = nxtContentService.insert(content);

        if (contentCreated.getId() == null){
            result.put("status", 50);
            result.put("message", "系统错误");
            return result;
        }

        /*给sort设置一个默认值*/
        contentCreated.setSortId(contentCreated.getId());
        nxtContentService.update(contentCreated);

        return result;

    }


}
