package com.newxton.companywebsite.controller.api.admin;


import com.google.gson.Gson;
import com.newxton.companywebsite.entity.NxtNewsCategory;
import com.newxton.companywebsite.entity.NxtUploadfile;
import com.newxton.companywebsite.entity.NxtUploadfileCategory;
import com.newxton.companywebsite.service.NxtUploadfileCategoryService;
import com.newxton.companywebsite.service.NxtUploadfileService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminFilemanageCreateControler {

    /*
    * 这里是七牛云的配置，这里仅是测试key，开发完毕就失效（你现在看到的就可能是失效了的）。
    * 请你配置自己的七牛参数。
    * */
    private String qiniuDomain = "http://qdyrmq5wy.bkt.clouddn.com";
    private String qiniuAccessKey = "Kmw6SlJPssvX_aq2udKJpkZYBCnHVym2kNa8OxOF";
    private String qiniuSecretKey = "dIegE9Lfi2bKp6pLMUugar5_E1uSKvh1TZli_wbu";
    private String qiniuBucket = "company-website-test";

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtUploadfileCategoryService nxtUploadfileCategoryService;

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

            String originalFilename = null;
            String uploadResultFilename = null;

            try {
                byte[] bytes = file.getBytes();
                originalFilename = file.getOriginalFilename();
                uploadResultFilename = this.uploadQiniuYun(bytes,originalFilename);
            }
            catch (IOException e) {
                e.printStackTrace();
                /*上传失败*/
            }

            if (uploadResultFilename == null){
                result.put("status", 31);
                result.put("message", "上传失败");
                return result;
            }

            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            String url = this.qiniuDomain + "/" + uploadResultFilename;

            NxtUploadfile nxtUploadfile = new NxtUploadfile();

            nxtUploadfile.setFileLocation(1);//七牛云
            nxtUploadfile.setCategoryId(categoryId);
            nxtUploadfile.setDatelineUpdate(System.currentTimeMillis());
            nxtUploadfile.setFilenameSaved(uploadResultFilename);
            nxtUploadfile.setFilenameSource(originalFilename);
            nxtUploadfile.setFileExt(suffix);
            nxtUploadfile.setUrlpath(url);
            nxtUploadfile.setFilepath(uploadResultFilename);
            nxtUploadfile.setFilesize(file.getSize());
            nxtUploadfile.setFileLocation(1);

            //增加记录
            NxtUploadfile userCreated = nxtUploadfileService.insert(nxtUploadfile);
            if (userCreated.getId() == null){
                result.put("status", 50);
                result.put("message", "系统错误");
                return result;
            }

            result.put("url",url);

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

        }

        return result;

    }

    /**
     * 上传文件到七牛云
     * @param uploadBytes
     * @param fileName
     * @return
     */
    private String uploadQiniuYun(byte[] uploadBytes, String fileName){

        Configuration cfg = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);

        String accessKey = this.qiniuAccessKey;
        String secretKey = this.qiniuSecretKey;
        String bucket = this.qiniuBucket;

        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String yunPath = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        String yunFilename = String.valueOf(new Random().nextInt(1000000000)+1000000000)+"."+suffix;

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = yunPath+"-"+yunFilename;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
            return key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

        return null;
    }

}
