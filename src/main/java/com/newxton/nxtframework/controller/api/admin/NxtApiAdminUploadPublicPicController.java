package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/3
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminUploadPublicPicController {

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @RequestMapping(value = "/api/admin/upload/public_pic", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestHeader("Content-Length") Long contentLength,
            @RequestParam(value = "file",required = false) MultipartFile file
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        result = nxtUploadImageComponent.saveUploadImage(file);

        return result;

    }

}
