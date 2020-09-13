package com.newxton.companywebsite.controller.api.front;

import com.newxton.companywebsite.entity.NxtBanner;
import com.newxton.companywebsite.service.NxtBannerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/18
 * @address Shenzhen, China
 */
@RestController
public class NxtApiBannerListController {

    @Value("${newxton.config.qiniuDomain}")
    private String qiniuDomain;

    @Resource
    private NxtBannerService nxtBannerService;

    @RequestMapping("/api/banner_list")
    public Map<String,Object> index(@RequestParam("location_name") String locationName) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        //热门搜索
        NxtBanner nxtBannerCondition = new NxtBanner();
        nxtBannerCondition.setLocationName(locationName);
        List<NxtBanner> bannerList = this.nxtBannerService.queryAll(nxtBannerCondition);

        List<Map<String, Object>> resultList = new ArrayList<>();

        for (NxtBanner banner :
                bannerList) {
            Map<String, Object> item = new HashMap<>();
            item.put("urlpath",this.qiniuDomain + banner.getUrlpath());
            item.put("clickUrl",banner.getClickUrl());
            resultList.add(item);
        }

        result.put("data",resultList);

        return result;

    }

}
