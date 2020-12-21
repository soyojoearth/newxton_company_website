package com.newxton.nxtframework.controller.image;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/12
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtImageQRCodeController {

    @RequestMapping("/qr_code/{content}.png")
    public ResponseEntity<InputStreamResource> action(@PathVariable(value = "content",required = false) String content) {

        if (StringUtils.isEmpty(content)){
            content = "";
        }

        try {
            ByteArrayOutputStream image = this.crateQRCode(content,128,128);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(new ByteArrayInputStream(image.toByteArray())));
        }
        catch (Exception e){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray())));
        }
    }
    public ByteArrayOutputStream crateQRCode(String content, int width, int height) throws IOException {

        if (!StringUtils.isEmpty(content)) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            @SuppressWarnings("rawtypes")
            HashMap<EncodeHintType, Comparable> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 指定字符编码为“utf-8”
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // 指定二维码的纠错等级为中级
            hints.put(EncodeHintType.MARGIN, 2); // 设置图片的边距

            try {
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ImageIO.write(bufferedImage, "png", os);
                return os;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
