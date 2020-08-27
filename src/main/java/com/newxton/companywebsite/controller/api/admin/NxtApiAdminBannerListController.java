package com.newxton.companywebsite.controller.api.admin;

import com.newxton.companywebsite.entity.NxtBanner;
import com.newxton.companywebsite.service.NxtBannerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/26
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminBannerListController {

    @Value("${newxton.config.qiniuDomain}")
    private String qiniuDomain;

    @Resource
    private NxtBannerService nxtBannerService;

    @RequestMapping(value = "/api/admin/banner/list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtBanner> list = nxtBannerService.queryAll(new NxtBanner());

        Map<String,Object> jsonData = new HashMap<>();
        List<String> locationList = new ArrayList<>();

        for (NxtBanner nxtBanner :
                list) {
            String locationName = nxtBanner.getLocationName();
            if (!jsonData.containsKey(locationName)){
                List itemList = new ArrayList();
                jsonData.put(locationName,itemList);
                locationList.add(locationName);
            }
            List itemList = (List)jsonData.get(locationName);
            Map<String,Object> itemMap = new HashMap<>();
            itemMap.put("banner_picture_id",nxtBanner.getUploadfileId());
            itemMap.put("banner_picture_url", this.qiniuDomain + nxtBanner.getUrlpath());
            itemMap.put("banner_href_url",nxtBanner.getClickUrl());

            itemList.add(itemMap);

        }

        List resultList = new ArrayList();
        for (String locationName :
                locationList) {
            Map<String,Object> resultListItem = new HashMap<>();
            resultListItem.put("location_name",locationName);
            resultListItem.put("banner",jsonData.get(locationName));
            resultList.add(resultListItem);
        }

        result.put("list",resultList);

        return result;

    }

}
