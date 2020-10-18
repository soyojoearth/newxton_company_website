package com.newxton.nxtframework.component;

import com.newxton.nxtframework.entity.NxtUploadfile;
import com.newxton.nxtframework.service.NxtUploadfileService;
import com.qiniu.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/18
 * @address Shenzhen, China
 * 图片搬家。例如将图片从本地搬到七牛云、或者七牛云搬到本地、或者阿里云搬到七牛云等
 */
@Component
public class NxtImageTransferComponent {

    private Logger logger = LoggerFactory.getLogger(NxtImageTransferComponent.class);

    @Value("${newxton.config.oss.qiniuDomain}")
    private String ossQniuDomain;

    @Value("${newxton.config.oss.localPath}")
    private String ossLocalPath;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    /**
     * 移动一部分本地图片到七牛云
     * @param offset
     * @param limit
     * @return 成功移动几张
     */
    public int moveLocalImageToQiniu(int offset,int limit){

        int countSuccess = 0;

        //0:网盘文件 1:七牛云oss 2:阿里云oss 3:本地
        List<NxtUploadfile> list = nxtUploadfileService.queryAllByLimitAndLocation(offset,limit,3);

        for (NxtUploadfile nxtUploadfile :
                list) {
            String fileExt = nxtUploadfile.getFileExt();
            String filePath = nxtUploadfile.getFilepath();

            try {
                try {
                    Path path = Paths.get(this.ossLocalPath + filePath);
                    byte[] allBytes = Files.readAllBytes(path);
                    String urlPath = nxtUploadImageComponent.uploadFileToQiniuYun(allBytes,fileExt);
                    if (urlPath != null){
                        //上传成功，修改数据库，删除本地图片
                        nxtUploadfile.setFileLocation(1);//七牛云
                        String uploadResultFilename = urlPath.substring(urlPath.lastIndexOf("/") + 1).toLowerCase();
                        nxtUploadfile.setFilenameSaved(uploadResultFilename);
                        nxtUploadfile.setFilepath(urlPath);
                        nxtUploadfile.setUrlpath(urlPath);
                        nxtUploadfile.setFilesize(Long.valueOf(allBytes.length));
                        nxtUploadfile.setDatelineUpdate(System.currentTimeMillis());
                        nxtUploadfileService.update(nxtUploadfile);
                        //删除本地图片(先不删，自己手动删吧)
                        countSuccess++;
                    }
                }
                catch (FileNotFoundException e){

                }
            }
            catch (Exception e){

            }

        }

        return countSuccess;

    }

    /**
     * 把七牛云上的图片移动到本地保存
     * @param offset
     * @param limit
     * @return
     */
    public int moveQiniuImageToLocal(int offset,int limit){

        int countSuccess = 0;

        //0:网盘文件 1:七牛云oss 2:阿里云oss 3:本地
        List<NxtUploadfile> list = nxtUploadfileService.queryAllByLimitAndLocation(offset,limit,1);

        for (NxtUploadfile nxtUploadfile :
                list) {
            String urlPath = nxtUploadfile.getUrlpath();
            String newUrlPath = getRemoteImageAndSaveToLocal(this.ossQniuDomain + urlPath);
            if (newUrlPath != null){
                //抓取保存成功，修改数据库
                nxtUploadfile.setFileLocation(3);//七牛云
                String uploadResultFilename = newUrlPath.substring(newUrlPath.lastIndexOf("/") + 1).toLowerCase();
                nxtUploadfile.setFilenameSaved(uploadResultFilename);
                nxtUploadfile.setFilepath(newUrlPath);
                nxtUploadfile.setUrlpath(newUrlPath);
                nxtUploadfile.setDatelineUpdate(System.currentTimeMillis());
                nxtUploadfileService.update(nxtUploadfile);
                //删除七牛图片(先不删，自己手动删吧)
                countSuccess++;
            }
        }

        return countSuccess;

    }


    /**
     * 抓取远程图片，保存到本地图片目录
     * @param remotePicUrl
     * @return
     */
    public String getRemoteImageAndSaveToLocal(String remotePicUrl){
        try {
            URL uri = new URL(remotePicUrl);
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
                    urlPath = nxtUploadImageComponent.saveUploadFileLocal(fileBytesAll, fileExt);
                }
                catch (Exception e){
                    logger.error("faild",e);
                }
                return urlPath;
            }
        }catch (IOException e){
            logger.error("faild",e);
        }
        return null;
    }


}
