package com.newxton.nxtframework.controller.api.front.cms;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtWebPage;
import com.newxton.nxtframework.service.NxtWebPageService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructWebPage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam) {

        String key = jsonParam.getString("key");

        if (key == null) {
            return new NxtStructApiResult(52,"缺少key");
        }

        NxtWebPage nxtWebPage = nxtWebPageService.queryByKey(key);
        if (nxtWebPage == null){
            return new NxtStructApiResult(49,"对应的内容不存在");
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        NxtStructWebPage nxtStructWebPage = new NxtStructWebPage();
        nxtStructWebPage.setId(nxtWebPage.getId());
        nxtStructWebPage.setWebTitle(nxtWebPage.getWebTitle());
        nxtStructWebPage.setContentTitle(nxtWebPage.getContentTitle());
        nxtStructWebPage.setContentDetail(nxtUploadImageComponent.checkHtmlAndReplaceImageUrlForDisplay(nxtWebPage.getContentDetail()));
        nxtStructWebPage.setDatelineUpdate(nxtWebPage.getDatelineUpdate());
        nxtStructWebPage.setDatelineUpdateReadable(sdf.format(new Date(nxtWebPage.getDatelineUpdate())));

        return new NxtStructApiResult(nxtStructWebPage);

    }

}
