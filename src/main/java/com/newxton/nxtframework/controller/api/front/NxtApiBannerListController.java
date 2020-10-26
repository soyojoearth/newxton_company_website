package com.newxton.nxtframework.controller.api.front;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtBanner;
import com.newxton.nxtframework.entity.NxtUploadfile;
import com.newxton.nxtframework.service.NxtBannerService;
import com.newxton.nxtframework.service.NxtUploadfileService;
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
 * @copyright NxtFramework
 */
@RestController
public class NxtApiBannerListController {

    @Resource
    private NxtBannerService nxtBannerService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @RequestMapping("/api/banner_list")
    public Map<String,Object> exec(@RequestParam("location_name") String locationName) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        //热门搜索
        NxtBanner nxtBannerCondition = new NxtBanner();
        nxtBannerCondition.setLocationName(locationName);
        List<NxtBanner> bannerList = this.nxtBannerService.queryAll(nxtBannerCondition);

        //查询图片url
        List<Long> picFileIdList = new ArrayList<>();
        for (NxtBanner nxtBanner : bannerList) {
            picFileIdList.add(nxtBanner.getUploadfileId());
        }
        List<NxtUploadfile> nxtUploadfileList = nxtUploadfileService.selectByIdSet(0,picFileIdList.size(),picFileIdList);
        Map<Long,Object> fileIdMapUrlPath = new HashMap<>();
        for (NxtUploadfile nxtUploadfile : nxtUploadfileList) {
            fileIdMapUrlPath.put(nxtUploadfile.getId(),nxtUploadfile.getUrlpath());
        }

        List<Map<String, Object>> resultList = new ArrayList<>();

        for (NxtBanner banner :
                bannerList) {
            if (fileIdMapUrlPath.containsKey(banner.getUploadfileId())) {
                String urlPath = fileIdMapUrlPath.get(banner.getUploadfileId()).toString();
                Map<String, Object> item = new HashMap<>();
                item.put("urlpath", nxtUploadImageComponent.convertImagePathToDomainImagePath(urlPath));
                item.put("clickUrl", banner.getClickUrl());
                resultList.add(item);
            }
        }

        result.put("data",resultList);

        return result;

    }

}
