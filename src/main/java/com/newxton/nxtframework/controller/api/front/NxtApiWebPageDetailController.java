package com.newxton.nxtframework.controller.api.front;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtWebPage;
import com.newxton.nxtframework.service.NxtWebPageService;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class NxtApiWebPageDetailController {

    @Resource
    private NxtWebPageService nxtWebPageService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @RequestMapping("/api/web_content/detail")
    public  Map<String, Object> index(@RequestParam(value = "id",required = true) Long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        NxtWebPage nxtWebPage = nxtWebPageService.queryById(id);
        if (nxtWebPage == null){
            result.put("status", 49);
            result.put("message", "对应的内容不存在");
            return result;
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, Object> item = new HashMap<>();
        item.put("id",nxtWebPage.getId());
        item.put("webTitle",nxtWebPage.getWebTitle());
        item.put("contentTitle",nxtWebPage.getContentTitle());
        item.put("contentDetail",nxtUploadImageComponent.checkHtmlAndReplaceImageUrlForDisplay(nxtWebPage.getContentDetail()));
        item.put("datelineUpdate",nxtWebPage.getDatelineUpdate());
        item.put("datelineUpdateReadable",sdf.format(new Date(nxtWebPage.getDatelineUpdate())));

        result.put("data",item);

        return result;

    }

}
