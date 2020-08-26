package com.newxton.companywebsite.controller.api.admin;

import com.google.gson.Gson;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/3
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminUploadPublicPicController {

    @Value("${newxton.config.qiniuDomain}")
    private String qiniuDomain;

    @Value("${newxton.config.qiniuAccessKey}")
    private String qiniuAccessKey;

    @Value("${newxton.config.qiniuSecretKey}")
    private String qiniuSecretKey;

    @Value("${newxton.config.qiniuBucket}")
    private String qiniuBucket;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @RequestMapping(value = "/api/admin/upload/public_pic", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestHeader("Content-Length") Long contentLength,
            @RequestParam(value = "file",required = false) MultipartFile file
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

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
            String url = "/" + uploadResultFilename;

            NxtUploadfile nxtUploadfile = new NxtUploadfile();

            nxtUploadfile.setFileLocation(1);//七牛云
            nxtUploadfile.setCategoryId(0L);
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

            result.put("url",this.qiniuDomain+url);
            result.put("id",nxtUploadfile.getId());

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
