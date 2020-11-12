package com.newxton.nxtframework.controller.api.front;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtContent;
import com.newxton.nxtframework.service.NxtContentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/9/14
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiNewsDetailController {

    @Resource
    private NxtContentService nxtContentService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @RequestMapping(value = "/api/news/detail", method = RequestMethod.POST)
    public Map<String, Object> exec(@RequestParam(value = "id", required=false) Long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        NxtContent content = nxtContentService.queryById(id);
        if (content == null){
            result.put("status", 49);
            result.put("message", "对应的内容不存在");
            return result;
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, Object> item = new HashMap<>();
        item.put("id",content.getId());
        item.put("categoryId",content.getCategoryId());
        item.put("contentTitle",content.getContentTitle());
        item.put("contentDetail",nxtUploadImageComponent.checkHtmlAndReplaceImageUrlForDisplay(content.getContentDetail()));
        item.put("datelineUpdate",content.getDatelineUpdate());
        item.put("datelineUpdateReadable",sdf.format(new Date(content.getDatelineUpdate())));
        item.put("datelineCreate",content.getDatelineCreate());
        item.put("datelineCreateReadable",sdf.format(new Date(content.getDatelineCreate())));
        item.put("isRecommend",content.getDatelineCreate());

        result.put("data",item);

        return result;

    }

}
