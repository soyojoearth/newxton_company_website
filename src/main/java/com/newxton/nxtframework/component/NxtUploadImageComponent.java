package com.newxton.nxtframework.component;

import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtUploadfile;
import com.newxton.nxtframework.service.NxtUploadfileService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
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
 * @time 2020/8/11
 * @address Shenzhen, China
 */
@Component
public class NxtUploadImageComponent {

    @Value("${newxton.config.oss.localPath}")
    private String ossLocalPath;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @Resource
    private HttpServletRequest request;

    private String getOssLocation(){
        String ossLocation = nxtGlobalSettingComponent.getSettingValueInCache("oss_location");
        if (ossLocation == null || ossLocation.isEmpty()){
            ossLocation = "local";
        }
        return ossLocation;
    }

    private String getOssQiniuDomain(){
        return nxtGlobalSettingComponent.getSettingValueInCache("oss_qiniuDomain");
    }

    /**
     * 把Html里面的第三方图片抓取过来，存放到自己这里
     * @param contentHTML
     * @return
     */
    public String checkHtmlAndSavePic(String contentHTML){

        Matcher m = Pattern.compile("<img.*?src=\"(http.*?)\"").matcher(contentHTML);
        while (m.find()) {
            String imgUrl = m.group(1);
            if (!imgUrl.contains(this.getOssQiniuDomain())){
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
//                    else if (fileBytesAll[0] == (byte)0x42 && fileBytesAll[1] == (byte)0x4d){
//                        fileExt = "bmp";
//                    }
//                    else {
//                        //unknow
//                    }
                    if (fileExt != null) {
                        String urlPath = null;
                        try {
                            if (this.getOssLocation().equals("local")){
                                urlPath = this.saveUploadFileLocal(fileBytesAll, fileExt);
                            }
                            else if (this.getOssLocation().equals("qiniu")){
                                urlPath = this.uploadFileToQiniuYun(fileBytesAll, fileExt);
                            }
                        }
                        catch (Exception e){
                            System.out.println("Exception: " + e);
                        }

                        if (urlPath != null){
                            uploadResultFilename = urlPath.substring(urlPath.lastIndexOf("/") + 1).toLowerCase();
                        }

                        //文件记录保存数据库
                        NxtUploadfile nxtUploadfile = new NxtUploadfile();
                        if (this.getOssLocation().equals("local")){
                            nxtUploadfile.setFileLocation(3);//本地
                        }
                        else if (this.getOssLocation().equals("qiniu")) {
                            nxtUploadfile.setFileLocation(1);//七牛云
                        }
                        nxtUploadfile.setCategoryId(0L);
                        nxtUploadfile.setDatelineUpdate(System.currentTimeMillis());
                        nxtUploadfile.setFilenameSaved(uploadResultFilename);
                        nxtUploadfile.setFilenameSource("ThridPartPic");
                        nxtUploadfile.setFileExt(fileExt);
                        nxtUploadfile.setUrlpath(urlPath);
                        nxtUploadfile.setFilepath(urlPath);
                        nxtUploadfile.setFilesize((long)fileBytesAll.length);
                        //增加记录
                        NxtUploadfile userCreated = nxtUploadfileService.insert(nxtUploadfile);
                        //替换旧图片
                        if (this.getOssLocation().equals("local")) {
                            contentHTML = contentHTML.replace(imgUrl, urlPath);
                        }
                        else if (this.getOssLocation().equals("qiniu")){
                            contentHTML = contentHTML.replace(imgUrl, this.getOssQiniuDomain() + urlPath);
                        }
                    }
                }catch (IOException e){
                    System.out.println(e);
                }
            }
        }
        return contentHTML;
    }

    /**
     * 把虚拟图片域名替换为真实图片域名
     * @param contentHTML
     * @return
     */
    public String checkHtmlAndReplaceImageUrlForDisplay(String contentHTML){
        contentHTML = contentHTML.replace("http://newxton-image-domain",this.getOssQiniuDomain());
        return contentHTML;
    }

    /**
     * 把真实图片域名替换为虚拟图片域名
     * @param contentHTML
     * @return
     */
    public String checkHtmlAndReplaceImageUrlForSave(String contentHTML){
        contentHTML = contentHTML.replace(this.getOssQiniuDomain(),"http://newxton-image-domain");
        return contentHTML;
    }

    /**
     * 给imagePath加上图片域名
     * @param imagePath
     * @return
     */
    public String convertImagePathToDomainImagePath(String imagePath){
        if (imagePath.contains("/public_pic")){
            return request.getScheme() + "://" + request.getHeader("host") + imagePath;
        }
        else {
            return this.getOssQiniuDomain() + imagePath;
        }
    }


    /**
     * 保存Form上传图片
     * @param multipartFile
     * @return
     */
    public Map<String, Object> saveUploadImage(MultipartFile multipartFile){

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        String url_path = null;

        try {
            if (this.getOssLocation().equals("local")){
                url_path = this.saveUploadFileLocal(multipartFile);
            }
            else if (this.getOssLocation().equals("qiniu")){
                url_path = this.uploadFileToQiniuYun(multipartFile);
            }
        }
        catch (Exception e){
            System.out.println("Exception: " + e);
        }

        /*通常方式：上传文件*/
        if (url_path == null) {
            result.put("status", 31);
            result.put("message", "上传失败");
            return result;
        }
        else {

            String originalFilename = multipartFile.getOriginalFilename();
            String uploadResultFilename = url_path.substring(url_path.lastIndexOf("/") + 1).toLowerCase();

            String suffix = url_path.substring(url_path.lastIndexOf(".") + 1).toLowerCase();

            NxtUploadfile nxtUploadfile = new NxtUploadfile();

            if (this.getOssLocation().equals("local")){
                nxtUploadfile.setFileLocation(3);//本地
            }
            else if (this.getOssLocation().equals("qiniu")) {
                nxtUploadfile.setFileLocation(1);//七牛云
            }

            nxtUploadfile.setCategoryId(0L);
            nxtUploadfile.setDatelineUpdate(System.currentTimeMillis());
            nxtUploadfile.setFilenameSaved(uploadResultFilename);
            nxtUploadfile.setFilenameSource(originalFilename);
            nxtUploadfile.setFileExt(suffix);
            nxtUploadfile.setUrlpath(url_path);
            nxtUploadfile.setFilepath(url_path);
            nxtUploadfile.setFilesize(multipartFile.getSize());

            //增加记录
            NxtUploadfile userCreated = nxtUploadfileService.insert(nxtUploadfile);
            if (userCreated.getId() == null) {
                result.put("status", 50);
                result.put("message", "系统错误");
            }
            else {
                if (this.getOssLocation().equals("qiniu")) {
                    result.put("url", this.getOssQiniuDomain() + url_path);
                }
                else {
                    result.put("url", url_path);
                }
                result.put("id",nxtUploadfile.getId());
            }
        }
        return result;
    }

    /**
     * 保存图片到本地
     * @param multipartFile
     * @return
     */
    public String saveUploadFileLocal(MultipartFile multipartFile) throws IOException{
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            byte[] bytes = multipartFile.getBytes();
            return saveUploadFileLocal(bytes,suffix);
        }
        return null;
    }

    /**
     * 保存图片到本地
     * @param uploadBytes
     * @param fileExt
     * @return
     */
    public String saveUploadFileLocal(byte[] uploadBytes, String fileExt){
        // 保存图片
        String suffix = fileExt.toLowerCase();
        String savePath = new SimpleDateFormat("/yyyy/MM/dd").format(new Date(System.currentTimeMillis()));
        savePath = "/public_pic" + savePath;
        String saveFilename = String.valueOf(new Random().nextInt(1000000000)+1000000000)+"."+suffix;
        this.createDir(this.ossLocalPath + savePath);
        try {
            String url = savePath + "/" + saveFilename;
            String filePath = this.ossLocalPath + savePath + "/" + saveFilename;
            File file = new File(filePath);
            OutputStream os = new FileOutputStream(file);
            os.write(uploadBytes);
            os.close();
            return url;
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
            /*上传失败*/
            return null;
        }
    }

    /**
     * Create Dir
     * @param destDirName
     * @return
     */
    public boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
//            System.out.println(destDirName + "目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
//            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }


    /**
     * 上传文件到七牛云
     * @param multipartFile
     * @return
     */
    public String uploadFileToQiniuYun(MultipartFile multipartFile) throws IOException{
        /*通常方式：上传文件*/
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String originalFilename = null;
            byte[] uploadBytes = multipartFile.getBytes();
            originalFilename = multipartFile.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            String qiniuYunPath = this.uploadFileToQiniuYun(uploadBytes,suffix);
            return qiniuYunPath;
        }
        return null;
    }

    /**
     * 上传文件到七牛云
     * @param uploadBytes
     * @param fileExt
     * @return
     */
    public String uploadFileToQiniuYun(byte[] uploadBytes, String fileExt){

        Configuration cfg = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);

        String accessKey = nxtGlobalSettingComponent.getSettingValueInCache("oss_qiniuAccessKey");;
        String secretKey = nxtGlobalSettingComponent.getSettingValueInCache("oss_qiniuSecretKey");;
        String bucket = nxtGlobalSettingComponent.getSettingValueInCache("oss_qiniuBucket");;

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
            if (putRet.key != null){
                key = "/"+putRet.key;
            }
            else {
                key = null;
            }
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
