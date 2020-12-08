package com.newxton.nxtframework.controller.api.front.cms;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtContent;
import com.newxton.nxtframework.service.NxtContentService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.*;

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
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");

        if (id == null) {
            return new NxtStructApiResult(52,"参数错误");
        }

        NxtContent content = nxtContentService.queryById(id);
        if (content == null){
            return new NxtStructApiResult(49,"对应的内容不存在");
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

        return new NxtStructApiResult(item);

    }

}
