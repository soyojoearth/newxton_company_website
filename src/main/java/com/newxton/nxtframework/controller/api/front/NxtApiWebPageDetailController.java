package com.newxton.nxtframework.controller.api.front;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtWebPage;
import com.newxton.nxtframework.service.NxtWebPageService;
import com.newxton.nxtframework.struct.NxtStructWebPage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Map<String, Object> exec(@RequestBody JSONObject jsonParam) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        Long id = jsonParam.getLong("id");

        if (id == null) {
            result.put("status",52);
            result.put("message","缺少id");
            return result;
        }

        NxtWebPage nxtWebPage = nxtWebPageService.queryById(id);
        if (nxtWebPage == null){
            result.put("status",49);
            result.put("message","对应的内容不存在");
            return result;
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        NxtStructWebPage nxtStructWebPage = new NxtStructWebPage();
        nxtStructWebPage.setId(nxtWebPage.getId());
        nxtStructWebPage.setWebTitle(nxtWebPage.getWebTitle());
        nxtStructWebPage.setContentTitle(nxtWebPage.getContentTitle());
        nxtStructWebPage.setContentDetail(nxtUploadImageComponent.checkHtmlAndReplaceImageUrlForDisplay(nxtWebPage.getContentDetail()));
        nxtStructWebPage.setDatelineUpdate(nxtWebPage.getDatelineUpdate());
        nxtStructWebPage.setDatelineUpdateReadable(sdf.format(new Date(nxtWebPage.getDatelineUpdate())));

        result.put("detail",nxtStructWebPage);

        return result;

    }

}
