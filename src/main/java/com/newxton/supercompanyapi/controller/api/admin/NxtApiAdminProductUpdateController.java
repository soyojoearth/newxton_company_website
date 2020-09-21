package com.newxton.supercompanyapi.controller.api.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.supercompanyapi.entity.*;
import com.newxton.supercompanyapi.service.*;
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
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminProductUpdateController {

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
    private NxtProductService nxtProductService;

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    @Resource
    private NxtProductPictureService nxtProductPictureService;

    @Resource
    private NxtProductSkuService nxtProductSkuService;

    @Resource
    private NxtProductSkuValueService nxtProductSkuValueService;

    @RequestMapping(value = "/api/admin/product/update", method = RequestMethod.POST)
    public Map<String, Object> index(
                                    @RequestParam(value = "id", required=false) Long id,
                                    @RequestParam(value = "category_id", required=false) Long categoryId,
                                     @RequestParam(value = "product_name", required=false) String productName,
                                     @RequestParam(value = "product_description", required=false) String productDescription,
                                     @RequestParam(value = "product_picture_list", required=false) String productPictureList,
                                     @RequestParam(value = "is_recommend", required=false) Integer isRecommend,
                                    @RequestParam(value = "product_sku", required=false) String productSku,
                                    @RequestParam(value = "price", required=false) Object price,
                                    @RequestParam(value = "price_negotiation", required=false) Integer priceNegotiation,
                                    @RequestParam(value = "price_remark", required=false) String priceRemark,
                                    @RequestParam(value = "product_subtitle", required=false) String productSubtitle
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null || productName == null || productDescription == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        if (categoryId == null){
            categoryId = 0L;
        }

        if (isRecommend == null){
            isRecommend = 0;
        }
        if (!isRecommend.equals(0)){
            isRecommend = 1;
        }

        if (price == null){
            price = "";
        }

        if (priceNegotiation == null){
            priceNegotiation = 0;
        }
        if (priceRemark == null){
            priceRemark = "";
        }
        if (productSubtitle == null){
            productSubtitle = "";
        }

        productName = productName.trim();

        /*检查分类*/
        if (categoryId > 0) {
            NxtProductCategory category = nxtProductCategoryService.queryById(categoryId);
            if (category == null) {
                result.put("status", 48);
                result.put("message", "该类别不存在");
                return result;
            }
        }

        /*查询Product*/
        NxtProduct product = nxtProductService.queryById(id);
        if (product == null){
            result.put("status", 49);
            result.put("message", "对应的产品不存在");
            return result;
        }

        //把第三方图片抓取过来，存放到自己这里
        productDescription = checkAndSavePic(productDescription);

        /*更新内容*/
        product.setCategoryId(categoryId);
        product.setProductName(productName);
        product.setProductDescription(productDescription.replace(this.qiniuDomain,"http://newxton-image-domain"));
        product.setDatelineCreate(System.currentTimeMillis());
        product.setDatelineUpdated(product.getDatelineCreate());
        product.setIsRecommend(isRecommend);

        if (isNumeric(price.toString().trim())) {
            Long priceLong = (long) (Float.parseFloat(price.toString().trim()) * 100);
            product.setPrice(priceLong);
        }

        product.setPriceNegotiation(priceNegotiation);
        product.setPriceRemark(priceRemark);
        product.setProductSubtitle(productSubtitle);

        nxtProductService.update(product);

        //更新产品图片id（先删除全部，再重新插入）
        NxtProductPicture nxtProductPictureCondition = new NxtProductPicture();
        nxtProductPictureCondition.setProductId(product.getId());
        List<NxtProductPicture> picList = nxtProductPictureService.queryAll(nxtProductPictureCondition);
        if (picList != null){
            for (int i = 0; i < picList.size(); i++) {
                NxtProductPicture nxtProductPicture = picList.get(i);
                nxtProductPictureService.deleteById(nxtProductPicture.getId());
            }
        }

        //添加产品图片id
        if (productPictureList != null && !productPictureList.trim().isEmpty()){
            Gson gson = new Gson();
            List<Long> productPictureIdList = gson.fromJson(productPictureList,new TypeToken<List<Long>>(){}.getType());
            for (int i = 0; i < productPictureIdList.size(); i++) {
                Long picId = productPictureIdList.get(i);
                NxtUploadfile nxtUploadfile = nxtUploadfileService.queryById(picId);
                if (nxtUploadfile != null){
                    if (nxtUploadfile.getCategoryId().equals(0L)){//检查是不是图片类型
                        NxtProductPicture nxtProductPicture = new NxtProductPicture();
                        nxtProductPicture.setProductId(product.getId());
                        nxtProductPicture.setUploadfileId(nxtUploadfile.getId());
                        //插入关联图片
                        NxtProductPicture nxtProductPictureCreated = nxtProductPictureService.insert(nxtProductPicture);
                        if (nxtProductPictureCreated.getId() != null){
                            //更新排序ID
                            nxtProductPictureCreated.setSortId(nxtProductPictureCreated.getId());
                            nxtProductPictureService.update(nxtProductPictureCreated);
                        }
                    }
                }
            }
        }



        //更新产品属性
        if(productSku != null && !productSku.trim().isEmpty()){

            //先清除原先的属性
            NxtProductSku nxtProductSkuCondition = new NxtProductSku();
            nxtProductSkuCondition.setProductId(product.getId());
            List<NxtProductSku> dbSkuList = nxtProductSkuService.queryAll(nxtProductSkuCondition);
            for (NxtProductSku skuItem :
                    dbSkuList) {
                NxtProductSkuValue nxtProductSkuValueCondition = new NxtProductSkuValue();
                nxtProductSkuValueCondition.setSkuId(skuItem.getId());
                List<NxtProductSkuValue> dbSkuVauleList = nxtProductSkuValueService.queryAll(nxtProductSkuValueCondition);
                for (NxtProductSkuValue skuValueItem :
                        dbSkuVauleList) {
                    nxtProductSkuValueService.deleteById(skuValueItem.getId());
                }
                nxtProductSkuService.deleteById(skuItem.getId());
            }

            //再插入接收到的属性
            Gson gson = new Gson();
            List<Map<String,Object>> productSkuList = gson.fromJson(productSku,new TypeToken<List<Map<String,Object>>>(){}.getType());
            for (Map<String, Object> skuItem :
                    productSkuList) {
                NxtProductSku nxtProductSku = new NxtProductSku();
                nxtProductSku.setProductId(product.getId());
                nxtProductSku.setSkuName(skuItem.get("name").toString());
                NxtProductSku nxtProductSkuCreated = nxtProductSkuService.insert(nxtProductSku);
                List<String> skuValueList = (List<String>)skuItem.get("sku");
                for (String skuValue :
                        skuValueList) {
                    NxtProductSkuValue nxtProductSkuValue = new NxtProductSkuValue();
                    nxtProductSkuValue.setSkuId(nxtProductSkuCreated.getId());
                    nxtProductSkuValue.setSkuValue(skuValue);
                    nxtProductSkuValueService.insert(nxtProductSkuValue);
                }
            }
        }

        return result;

    }

    private boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
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
