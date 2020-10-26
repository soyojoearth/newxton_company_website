package com.newxton.nxtframework.controller.image;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Value("${newxton.config.oss.localPath}")
    private String ossLocalPath;

    /**
     * 输出图片
     */
    @RequestMapping(value = {
            "/public_pic/*.png",
            "/public_pic/*/*.png",
            "/public_pic/*/*/*.png",
            "/public_pic/*/*/*/*.png",
            "/public_pic/*.jpg",
            "/public_pic/*/*.jpg",
            "/public_pic/*/*/*.jpg",
            "/public_pic/*/*/*/*.jpg",
            "/public_pic/*.gif",
            "/public_pic/*/*.gif",
            "/public_pic/*/*/*.gif",
            "/public_pic/*/*/*/*.gif",
            "/public_pic/*.jpeg",
            "/public_pic/*/*.jpeg",
            "/public_pic/*/*/*.jpeg",
            "/public_pic/*/*/*/*.jpeg",
    })
    public ResponseEntity<InputStreamResource> index(HttpServletRequest request) throws IOException {

        String imageUrl= request.getRequestURI().toLowerCase();

        String imageStyle = request.getQueryString();

        if (imageStyle == null || imageStyle.isEmpty()){
            //_imageview2_type1_w50_h150_q75.png（这里兼容Nginx Rewrite过来的地址）
            String patternString = "(.*?)_(imageview2)_type(\\d+?)_w(\\d+?)_h(\\d+?)_q(\\d+)";
            Matcher m = Pattern.compile(patternString).matcher(imageUrl);
            if (m.find()) {
                imageStyle = "imageView2/"+m.group(3)+"/w/"+m.group(4)+"/h/"+m.group(5)+"/q/"+m.group(6);
                imageUrl = m.group(1);
            }
        }

        imageUrl = this.ossLocalPath + imageUrl;

        //初始化对象、检查文件是否存在
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage sourceImg;
        ImageInputStream imageInputStream;
        try {
            File file = new File(imageUrl);
            sourceImg = ImageIO.read(new FileInputStream(file));
            imageInputStream = ImageIO.createImageInputStream(file);
        }
        catch (FileNotFoundException e){
            //文件不存在，输出空
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray())));
        }

        //检查文件类型
        String formatName = getImageFormatName(imageInputStream);
        MediaType mediaType;
        if (formatName != null && formatName.equals("png")){
            mediaType = MediaType.IMAGE_PNG;
        }
        else if (formatName != null && formatName.equals("JPEG")){
            mediaType = MediaType.IMAGE_JPEG;
        }
        else if (formatName != null && formatName.equals("gif")){
            mediaType = MediaType.IMAGE_GIF;
        }
        else {
            //文件类型不支持
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray())));
        }

        //无格式命令
        if (imageStyle == null || imageStyle.isEmpty()){
            //原图
            ImageIO.write(sourceImg,formatName,outputStream);
        }
        else {

            //根据格式要求进行缩放、输出图片（此处兼容七牛云的格式命令串：imageView2/1/w/100/h/135/q/75）
            int width = 0;
            int height = 0;
            float quality = 1;
            String patternString = "(imageView2)/(.+?)/w/(\\d+?)/h/(\\d+?)/q/(\\d+)";
            Matcher m = Pattern.compile(patternString).matcher(imageStyle);
            if (m.find()) {
                int type = Integer.valueOf(m.group(2));
                width = Integer.valueOf(m.group(3));
                height = Integer.valueOf(m.group(4));
                quality = Float.valueOf(m.group(5)) / 100;
                if (type == 2) {
                    //按比例缩放，不裁剪(七牛：缩至指定宽高区域内)
                    Thumbnails.of(sourceImg)
                            .size(width, height)
                            .outputQuality(quality)
                            .outputFormat(formatName)
                            .toOutputStream(outputStream);
                } else if (type == 1) {
                    //按比例缩放，裁剪(七牛：缩至完全覆盖指定宽高区域，居中剪裁)
                    int imgHeight = sourceImg.getHeight();
                    int imgWidth = sourceImg.getWidth();
                    if (width < height) {
                        float rate = (float) width / (float) height;
                        imgWidth = (int) ((float) imgHeight * rate);
                    } else if (height < width) {
                        float rate = (float) height / (float) width;
                        imgHeight = (int) ((float) imgWidth * rate);
                    } else {
                        if (imgHeight > imgWidth) {
                            imgHeight = imgWidth;
                        } else {
                            imgWidth = imgHeight;
                        }
                    }
                    Thumbnails.of(sourceImg)
                            .sourceRegion(Positions.CENTER, imgWidth, imgHeight)
                            .size(width, height)
                            .keepAspectRatio(false)
                            .outputQuality(quality)
                            .outputFormat(formatName)
                            .toOutputStream(outputStream);
                }
            }
        }

        //保存缓存图片
        if (imageStyle != null && !imageStyle.isEmpty() && outputStream.toByteArray().length > 0) {
            String suffix = imageUrl.substring(imageUrl.lastIndexOf(".") + 1).toLowerCase();
            String filePath = imageUrl + "_" + imageStyle.replace("imageView2/", "imageView2_type")
                    .replace("/w/","_w")
                    .replace("/h/","_h")
                    .replace("/q/","_q")
                    + "." + suffix;
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            try {
                File file = new File(filePath);
                byte[] bytes = outputStream.toByteArray();
                OutputStream os = new FileOutputStream(file);
                os.write(bytes);
                os.close();
                System.out.println("生成文件" + fileName);
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                System.out.println("生成文件Fail" + fileName);
            }
        }

        //输出图片。若以上没有匹配格式成功，则输出空图片。
        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .body(new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray())));

    }


    /**
     * 获取图片格式类型
     * @param imageInputStream
     * @return
     */
    private String getImageFormatName(ImageInputStream imageInputStream) {
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
        try {
            while (imageReaders.hasNext()) {
                ImageReader reader = (ImageReader) imageReaders.next();
                return reader.getFormatName();
            }
        }
        catch (Exception e){
            return null;
        }
        return null;
    }

}
