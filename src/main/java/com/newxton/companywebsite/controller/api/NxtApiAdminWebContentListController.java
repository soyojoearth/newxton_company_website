package com.newxton.companywebsite.controller.api;

import com.newxton.companywebsite.entity.NxtContent;
import com.newxton.companywebsite.service.NxtContentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class NxtApiAdminWebContentListController {

    @Resource
    private NxtContentService nxtContentService;

    @RequestMapping(value = "/api/admin/web_content/list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtContent contentCondition = new NxtContent();
        contentCondition.setContentType(1);
        List<NxtContent> list = nxtContentService.queryAll(contentCondition);
        List<Map<String,Object>> listResult = new ArrayList<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            NxtContent content = list.get(i);
            Map<String, Object> item = new HashMap<>();
            item.put("id",content.getId());
            item.put("webTitle",content.getWebTitle());
            item.put("contentTitle",content.getContentTitle());
            item.put("datelineUpdate",content.getDatelineUpdate());
            item.put("datelineUpdateReadable",sdf.format(new Date(content.getDatelineUpdate())));
            listResult.add(item);
        }
        result.put("list",listResult);

        return result;

    }

}
