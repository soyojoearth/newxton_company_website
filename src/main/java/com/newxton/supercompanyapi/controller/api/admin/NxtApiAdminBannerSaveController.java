package com.newxton.supercompanyapi.controller.api.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.supercompanyapi.entity.NxtBanner;
import com.newxton.supercompanyapi.entity.NxtUploadfile;
import com.newxton.supercompanyapi.service.NxtBannerService;
import com.newxton.supercompanyapi.service.NxtUploadfileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
public class NxtApiAdminBannerSaveController {

    @Resource
    private NxtBannerService nxtBannerService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @RequestMapping(value = "/api/admin/banner/save", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "location_name", required=false) String locationName,
                                     @RequestParam(value = "banner_picture_list", required=false) String bannerPictureList,
                                     @RequestParam(value = "banner_href_list", required=false) String bannerHrefList
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (locationName == null || bannerPictureList == null || bannerHrefList == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        if (!bannerPictureList.trim().isEmpty() && !bannerHrefList.trim().isEmpty()){

            Gson gson = new Gson();
            List<Long> idList = gson.fromJson(bannerPictureList,new TypeToken<List<Long>>(){}.getType());
            List<String> hrefList = gson.fromJson(bannerHrefList,new TypeToken<List<String>>(){}.getType());

            if (idList.size() != hrefList.size()) {
                result.put("status", 52);
                result.put("message", "参数错误:id列表和href列表size必须相同");
                return result;
            }

            //先删除旧的Banner数据
            NxtBanner nxtBannerCondition = new NxtBanner();
            nxtBannerCondition.setLocationName(locationName);
            List<NxtBanner> bannerListOld = nxtBannerService.queryAll(nxtBannerCondition);
            for (NxtBanner itemBanner :
                    bannerListOld) {
                nxtBannerService.deleteById(itemBanner.getId());
            }

            //再插入新数据
            for (int i = 0; i < idList.size(); i++) {
                Long uploadFileId = idList.get(i);
                String clickUrl = hrefList.get(i);
                //查询图片
                NxtUploadfile nxtUploadfileQuery = nxtUploadfileService.queryById(uploadFileId);
                if (nxtUploadfileQuery != null) {
                    //保存Banner数据
                    NxtBanner nxtBanner = new NxtBanner();
                    nxtBanner.setLocationName(locationName);
                    nxtBanner.setUploadfileId(uploadFileId);
                    nxtBanner.setClickUrl(clickUrl);
                    nxtBanner.setUrlpath(nxtUploadfileQuery.getUrlpath());
                    nxtBannerService.insert(nxtBanner);
                }
            }

        }

        return result;

    }

}
