package com.newxton.companywebsite.controller.api.admin;

import com.newxton.companywebsite.entity.NxtWebPage;
import com.newxton.companywebsite.service.NxtWebPageService;
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
 * @time 2020/7/24
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminWebContentDetailController {

    @Resource
    private NxtWebPageService nxtWebPageService;

    @RequestMapping(value = "/api/admin/web_content/detail", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id) {

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
        item.put("contentDetail",nxtWebPage.getContentDetail());
        item.put("datelineUpdate",nxtWebPage.getDatelineUpdate());
        item.put("datelineUpdateReadable",sdf.format(new Date(nxtWebPage.getDatelineUpdate())));

        result.put("detail",item);

//        /*获取七牛图片上传token*/
//        Auth auth = Auth.create(qiniuAccessKey, qiniuSecretKey);
//        String qiniuToken = auth.uploadToken(qiniuBucket);
//
//        result.put("qiniuToken",qiniuToken);

        return result;

    }

}
