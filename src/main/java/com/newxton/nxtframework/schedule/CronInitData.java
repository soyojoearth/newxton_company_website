package com.newxton.nxtframework.schedule;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.*;
import com.newxton.nxtframework.struct.NxtStructShoppingCartProductSku;
import com.qiniu.util.IOUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/8
 * @address Shenzhen, China
 * Cronjob 系统初始化，检查默认配置
 */
@Component
public class CronInitData {

    private Logger logger = LoggerFactory.getLogger(CronInitData.class);

    @Resource
    private NxtUtilComponent nxtUtilComponent;

    @Resource
    private NxtAclActionService nxtAclActionService;

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtAclUserActionService nxtAclUserActionService;

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    @Resource
    private NxtNewsCategoryService nxtNewsCategoryService;

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    @Resource
    private NxtWebPageService nxtWebPageService;

    @Resource
    private NxtBannerService nxtBannerService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
    public void exec() throws Exception{

        //检查&初始化默认Banner图
        this.checkAndInitBannerData();

        //检查&初始化资讯类别
        this.checkAndInitNewsCategory();

        //检查&初始化产品类别
        this.checkAndInitProductCategory();

        //检查&初始化地区列表
        this.checkAndInitDeliveryRegion();


        //检查&初始化示例资讯

        //检查&初始化示例产品

    }


    /**
     * 检查&初始化默认Banner图
     */
    private void checkAndInitBannerData(){

       List<NxtBanner> nxtBannerList = nxtBannerService.queryAll(new NxtBanner());

        if (nxtBannerList.size() > 0){
            return;
        }

       InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/init/images/banner1.png");
       try {
           byte[] fileBytesAll = IOUtils.toByteArray(inputStream);
           Map<String,Object> result = nxtUploadImageComponent.saveUploadImage(fileBytesAll,"png");
           if (result.containsKey("id")){
               NxtBanner nxtBanner = new NxtBanner();
               nxtBanner.setLocationName("首页");
               nxtBanner.setClickUrl("#");
               nxtBanner.setUploadfileId((long)result.get("id"));
               nxtBannerService.insert(nxtBanner);
           }
       }
       catch (IOException e){
           logger.info("Resource读取初始化banner图，错误");
       }

    }

    /**
     * 检查&初始化资讯类别
     */
    private void checkAndInitNewsCategory(){
        List<NxtNewsCategory> nxtNewsCategories = nxtNewsCategoryService.queryAllByLimit(0,1);
        if (nxtNewsCategories.size() > 0){
            return;
        }
        List<String> categoryList = new ArrayList<>();
        categoryList.add("行业资讯");
        categoryList.add("政策解读");
        categoryList.add("新闻公告");
        for (String categoryName : categoryList) {
            NxtNewsCategory nxtNewsCategory = new NxtNewsCategory();
            nxtNewsCategory.setCategoryName(categoryName);
            nxtNewsCategory.setCategoryPid(0L);
            nxtNewsCategoryService.insert(nxtNewsCategory);
        }
    }

    /**
     * 检查&初始化产品类别
     */
    private void checkAndInitProductCategory(){

        List<NxtProductCategory> nxtProductCategoryList = nxtProductCategoryService.queryAllByLimit(0,1);
        if (nxtProductCategoryList.size() > 0){
            return;
        }
        List<String> categoryList = new ArrayList<>();
        categoryList.add("产品类别A");
        categoryList.add("产品类别B");
        categoryList.add("产品类别C");
        for (String categoryName : categoryList) {
            NxtProductCategory nxtProductCategory = new NxtProductCategory();
            nxtProductCategory.setCategoryName(categoryName);
            nxtProductCategory.setCategoryPid(0L);
            nxtProductCategoryService.insert(nxtProductCategory);
        }
    }

    /**
     * 检查&初始化地区列表
     * 感谢作者数据收集：https://github.com/airyland/china-area-data
     * https://raw.githubusercontent.com/airyland/china-area-data/master/data.json
     */
    public void checkAndInitDeliveryRegion() throws Exception {

        List<NxtDeliveryRegion> nxtDeliveryRegionList = nxtDeliveryRegionService.queryAllByLimit(0,1);
        if (nxtDeliveryRegionList.size() > 0){
            return;
        }

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/init/china_region.json");
        byte[] fileBytesAll = IOUtils.toByteArray(inputStream);
        String json = new String(fileBytesAll, StandardCharsets.UTF_8);

        Gson gson = new Gson();
        try {
            Map<String,Map<String,String>> mapRegion = gson.fromJson(json,new TypeToken<Map<String,Map<String,String>>>(){}.getType());

            NxtDeliveryRegion nxtDeliveryRegion = new NxtDeliveryRegion();
            nxtDeliveryRegion.setRegionPid(0L);
            nxtDeliveryRegion.setRegionName("中国");
            nxtDeliveryRegionService.insert(nxtDeliveryRegion);
            nxtDeliveryRegion.setSortId(nxtDeliveryRegion.getId());
            nxtDeliveryRegionService.update(nxtDeliveryRegion);

            findRegionListAndInsert(mapRegion,"86",nxtDeliveryRegion.getId());
        }
        catch (Exception e){
            throw new NxtException("china_region.json解析JSON出错");
        }

    }

    /**
     * 遍历导入地区
     * @param mapRegion
     * @param regionCode
     * @param parentId
     */
    private void findRegionListAndInsert(Map<String,Map<String,String>> mapRegion, String regionCode,Long parentId){
        if (!mapRegion.containsKey(regionCode)){
            return;
        }
        Map<String,String> mapChildren = mapRegion.get(regionCode);
        List<String> keyList = new ArrayList<>();
        keyList.addAll(mapChildren.keySet());
        for (String key : keyList) {
            String regionName = mapChildren.get(key);
            NxtDeliveryRegion nxtDeliveryRegion = new NxtDeliveryRegion();
            nxtDeliveryRegion.setRegionPid(parentId);
            nxtDeliveryRegion.setRegionName(regionName);
            nxtDeliveryRegionService.insert(nxtDeliveryRegion);
            nxtDeliveryRegion.setSortId(nxtDeliveryRegion.getId());
            nxtDeliveryRegionService.update(nxtDeliveryRegion);
            findRegionListAndInsert(mapRegion,key,nxtDeliveryRegion.getId());
        }
    }


}
