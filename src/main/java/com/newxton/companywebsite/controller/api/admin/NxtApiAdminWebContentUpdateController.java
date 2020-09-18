package com.newxton.companywebsite.controller.api.admin;

import com.google.gson.Gson;
import com.newxton.companywebsite.entity.NxtUploadfile;
import com.newxton.companywebsite.entity.NxtWebPage;
import com.newxton.companywebsite.service.*;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminWebContentUpdateController {

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


    @Resource
    private NxtWebPageService nxtWebPageService;

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
        NxtWebPage nxtWebPage = nxtWebPageService.queryById(id);
        if (nxtWebPage == null){
            result.put("status", 49);
            result.put("message", "对应的内容不存在");
            return result;
        }

        //把第三方图片抓取过来，存放到自己这里
        contentDetail = checkAndSavePic(contentDetail);

        nxtWebPage.setContentTitle(contentTitle);
        nxtWebPage.setContentDetail(contentDetail.replace(this.qiniuDomain,"http://newxton-image-domain"));
        nxtWebPage.setDatelineUpdate(System.currentTimeMillis());
        nxtWebPage.setWebTitle(webTitle);

        nxtWebPageService.update(nxtWebPage);

        return result;

    }


    /**
     * 把第三方图片抓取过来，存放到自己这里
     * @param contentHTML
     * @return
     */
    private String checkAndSavePic(String contentHTML){

        Matcher m = Pattern.compile("<img.*?src=\"(http.*?)\"").matcher(contentHTML);
        while (m.find()) {
            String imgUrl = m.group(1);
            if (!imgUrl.contains(this.qiniuDomain)){
                //抓取图片，上传
                String uploadResultFilename = null;
                try {
                    URL uri = new URL(imgUrl);
                    InputStream in = uri.openStream();
                    byte[] fileBytesAll = IOUtils.toByteArray(in);
                    in.close();
                    //判断图片类型
                    String fileExt = null;
                    if (fileBytesAll[0] == (byte) 0xff && fileBytesAll[1] == (byte) 0xd8 && fileBytesAll[2] == (byte) 0xff){
                        fileExt = "jpg";
                    }
                    else if (fileBytesAll[0] == (byte)0x89 && fileBytesAll[1] == (byte)0x50 && fileBytesAll[2] == (byte)0x4e && fileBytesAll[3] == (byte)0x47){
                        fileExt = "png";
                    }
                    else if (fileBytesAll[0] == (byte)0x47 && fileBytesAll[1] == (byte)0x49 && fileBytesAll[2] == (byte)0x46 && fileBytesAll[3] == (byte)0x38){
                        fileExt = "gif";
                    }
                    else if (fileBytesAll[0] == (byte)0x42 && fileBytesAll[1] == (byte)0x4d){
                        fileExt = "bmp";
                    }
                    else {
                        //unknow
                    }
                    if (fileExt != null) {
                        //上传七牛云
                        uploadResultFilename = uploadQiniuYun( fileBytesAll, fileExt);
                        String url = "/" + uploadResultFilename;
                        //文件记录保存数据库
                        NxtUploadfile nxtUploadfile = new NxtUploadfile();
                        nxtUploadfile.setFileLocation(1);//七牛云
                        nxtUploadfile.setCategoryId(0L);
                        nxtUploadfile.setDatelineUpdate(System.currentTimeMillis());
                        nxtUploadfile.setFilenameSaved(uploadResultFilename);
                        nxtUploadfile.setFilenameSource("ThridPartPic");
                        nxtUploadfile.setFileExt(fileExt);
                        nxtUploadfile.setUrlpath(url);
                        nxtUploadfile.setFilepath(uploadResultFilename);
                        nxtUploadfile.setFilesize((long)fileBytesAll.length);
                        nxtUploadfile.setFileLocation(1);
                        //增加记录
                        NxtUploadfile userCreated = nxtUploadfileService.insert(nxtUploadfile);
                        //替换旧图片
                        contentHTML = contentHTML.replace(imgUrl,this.qiniuDomain+url);
                    }
                }catch (IOException e){
                    System.out.println(e);
                }
            }
        }
        return contentHTML;
    }

    /**
     * 上传文件到七牛云
     * @param uploadBytes
     * @param fileExt
     * @return
     */
    private String uploadQiniuYun(byte[] uploadBytes, String fileExt){

        Configuration cfg = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);

        String accessKey = this.qiniuAccessKey;
        String secretKey = this.qiniuSecretKey;
        String bucket = this.qiniuBucket;

        String suffix = fileExt;
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
