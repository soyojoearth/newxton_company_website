package com.newxton.companywebsite.controller.api.admin;

import com.newxton.companywebsite.service.NxtUploadfileCategoryService;
import com.newxton.companywebsite.service.NxtUploadfileService;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/3
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiQiniuUpToken {

    @Value("${newxton.config.qiniuDomain}")
    private String qiniuDomain;

    @Value("${newxton.config.qiniuAccessKey}")
    private String qiniuAccessKey;

    @Value("${newxton.config.qiniuSecretKey}")
    private String qiniuSecretKey;

    @Value("${newxton.config.qiniuBucket}")
    private String qiniuBucket;

    @RequestMapping(value = "/api/qiniu/wang_editor_uptoken", method = RequestMethod.GET)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();

        /*获取七牛图片上传token*/
        Auth auth = Auth.create(qiniuAccessKey, qiniuSecretKey);
        String qiniuToken = auth.uploadToken(qiniuBucket);

        result.put("uptoken",qiniuToken);
        result.put("domain",qiniuDomain);

        return result;

    }

}
