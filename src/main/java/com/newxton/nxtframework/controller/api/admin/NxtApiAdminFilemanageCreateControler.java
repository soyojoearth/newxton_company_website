package com.newxton.nxtframework.controller.api.admin;


import com.newxton.nxtframework.controller.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtUploadfile;
import com.newxton.nxtframework.entity.NxtUploadfileCategory;
import com.newxton.nxtframework.service.NxtUploadfileCategoryService;
import com.newxton.nxtframework.service.NxtUploadfileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminFilemanageCreateControler {

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtUploadfileCategoryService nxtUploadfileCategoryService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @RequestMapping(value = "/api/admin/filemanage/create", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestHeader("Content-Length") Long contentLength,
            @RequestParam(value = "category_id", required=false) Long categoryId,
            @RequestParam(value = "netdisk_url", required=false) String netdiskUrl,
            @RequestParam(value = "netdisk_pwd", required=false) String netdiskPwd,
            @RequestParam(value = "file",required = false) MultipartFile file
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");


        if (categoryId == null){
            categoryId = 0L;
        }

        /*检查分类*/
        if (categoryId > 0) {
            NxtUploadfileCategory category = nxtUploadfileCategoryService.queryById(categoryId);
            if (category == null) {
                result.put("status", 48);
                result.put("message", "该类别不存在");
                return result;
            }
        }

        /*通常方式：上传文件*/
        if (file != null && !file.isEmpty()){
            result = nxtUploadImageComponent.saveUploadImage(file);
        }
        else if (netdiskUrl != null && netdiskPwd != null){

            /*大文件方式：存网盘*/
            NxtUploadfile nxtUploadfile = new NxtUploadfile();
            nxtUploadfile.setFileLocation(0);//网盘
            nxtUploadfile.setCategoryId(categoryId);
            nxtUploadfile.setNetdiskUrl(netdiskUrl);
            nxtUploadfile.setNetdiskPwd(netdiskPwd);

            //增加记录
            NxtUploadfile userCreated = nxtUploadfileService.insert(nxtUploadfile);
            if (userCreated.getId() == null){
                result.put("status", 50);
                result.put("message", "系统错误");
                return result;
            }

            result.put("url",netdiskUrl);
            result.put("id",nxtUploadfile.getId());

        }

        return result;

    }

}
