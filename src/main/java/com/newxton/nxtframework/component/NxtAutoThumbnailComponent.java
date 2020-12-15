package com.newxton.nxtframework.component;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/4
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtAutoThumbnailComponent {

    private Logger logger = LoggerFactory.getLogger(NxtAutoThumbnailComponent.class);

    @Value("${newxton.config.oss.localPath}")
    private String ossLocalPath;

    @Cacheable("NxtAutoThumbnailComponent")
    public byte[] getThumbnail(String imageUrl, String imageStyle)  throws IOException {

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
            return outputStream.toByteArray();
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
            return outputStream.toByteArray();
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
                logger.info("生成文件" + fileName);
            } catch (Exception e) {
                logger.info("Exception: " + e);
                logger.info("生成文件Fail" + fileName);
            }
        }

        return outputStream.toByteArray();
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
