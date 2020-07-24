package com.newxton.companywebsite.controller.api.admin;

import com.newxton.companywebsite.entity.NxtContent;
import com.newxton.companywebsite.entity.NxtNewsCategory;
import com.newxton.companywebsite.service.NxtContentService;
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
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminWebContentUpdateController {

    @Resource
    private NxtContentService nxtContentService;

    @RequestMapping(value = "/api/admin/web_content/update", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "id", required=false) Long id,
            @RequestParam(value = "content_title", required=false) String contentTitle,
            @RequestParam(value = "content_detail", required=false) String contentDetail,
            @RequestParam(value = "web_title", required=false) String webTitle
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null || contentTitle == null || contentDetail == null || webTitle == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        contentTitle = contentTitle.trim();

        webTitle = webTitle.trim();

        /*更新内容*/
        NxtContent content = nxtContentService.queryById(id);
        if (content == null || !content.getContentType().equals(1)){
            result.put("status", 49);
            result.put("message", "对应的内容不存在");
            return result;
        }

        content.setContentTitle(contentTitle);
        content.setContentDetail(contentDetail);
        content.setDatelineUpdate(System.currentTimeMillis());
        content.setWebTitle(webTitle);

        nxtContentService.update(content);

        return result;

    }

}
