package com.newxton.nxtframework.controller.image;

import com.newxton.nxtframework.component.NxtAclComponent;
import com.newxton.nxtframework.component.NxtAutoThumbnailComponent;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 输出图片（仅支持png、JPEG、gif三种类型）
 * @author soyojo.earth@gmail.com
 * @time 2020/8/11
 * @address Shenzhen, China
 */
@RestController
public class NxtImageController {

    private Logger logger = LoggerFactory.getLogger(NxtImageController.class);

    @Resource
    private HttpServletRequest request;

    @Value("${newxton.config.oss.localPath}")
    private String ossLocalPath;

    @Resource
    private NxtAutoThumbnailComponent nxtAutoThumbnailComponent;

    /**
     * 输出图片
     * NxtAutoThumbnailComponent 这个组件是带缓存的，每个图片只要裁剪计算一次就被缓存了。
     */
    @RequestMapping(value = {
            "/public_pic/{a}.png",
            "/public_pic/{a}/{b}.png",
            "/public_pic/{a}/{b}/{c}.png",
            "/public_pic/{a}/{b}/{c}/{d}.png",
            "/public_pic/{a}.jpg",
            "/public_pic/{a}/{b}.jpg",
            "/public_pic/{a}/{b}/{c}.jpg",
            "/public_pic/{a}/{b}/{c}/{d}.jpg",
            "/public_pic/{a}.gif",
            "/public_pic/{a}/{b}.gif",
            "/public_pic/{a}/{b}/{c}.gif",
            "/public_pic/{a}/{b}/{c}/{d}.gif",
            "/public_pic/{a}.jpeg",
            "/public_pic/{a}/{b}.jpeg",
            "/public_pic/{a}/{b}/{c}.jpeg",
            "/public_pic/{a}/{b}/{c}/{d}.jpeg",
    })
    public ResponseEntity<InputStreamResource> index(
            @PathVariable(value = "a",required = false) String p1,
            @PathVariable(value = "b",required = false) String p2,
            @PathVariable(value = "c",required = false) String p3,
            @PathVariable(value = "d",required = false) String p4
            ) throws IOException {

        String imageUrl= request.getRequestURI().toLowerCase();

        String imageStyle = request.getQueryString();

        byte[] bytesAutoThumbnail = nxtAutoThumbnailComponent.getThumbnail(imageUrl,imageStyle);

        if (bytesAutoThumbnail.length < 4){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray())));
        }

        MediaType mediaType = MediaType.IMAGE_PNG;
        if (bytesAutoThumbnail[0] == (byte) 0xff && bytesAutoThumbnail[1] == (byte) 0xd8 && bytesAutoThumbnail[2] == (byte) 0xff){
            mediaType = MediaType.IMAGE_PNG;
        }
        else if (bytesAutoThumbnail[0] == (byte)0x89 && bytesAutoThumbnail[1] == (byte)0x50 && bytesAutoThumbnail[2] == (byte)0x4e && bytesAutoThumbnail[3] == (byte)0x47){
            mediaType = MediaType.IMAGE_PNG;
        }
        else if (bytesAutoThumbnail[0] == (byte)0x47 && bytesAutoThumbnail[1] == (byte)0x49 && bytesAutoThumbnail[2] == (byte)0x46 && bytesAutoThumbnail[3] == (byte)0x38){
            mediaType = MediaType.IMAGE_GIF;
        }

        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .body(new InputStreamResource(new ByteArrayInputStream(bytesAutoThumbnail)));

    }

}
