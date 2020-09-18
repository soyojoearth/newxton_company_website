package com.newxton.companywebsite.controller.api.front;

import com.newxton.companywebsite.entity.NxtSetting;
import com.newxton.companywebsite.service.NxtSettingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/18
 * @address Shenzhen, China
 */
@RestController
public class NxtApiHotKeywordsController {

    @Resource
    private NxtSettingService nxtSettingService;

    @RequestMapping("/api/hot_keywords")
    public Map<String,Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        //热门搜索
        List<String> hotKeywordList = getHotKeyword();

        result.put("data", hotKeywordList);

        return result;

    }

    /**
     * 获取热门搜索关键词列表
     * @return
     */
    protected List<String> getHotKeyword(){

        List<String> resultList = new ArrayList<>();
        NxtSetting nxtSetting = this.nxtSettingService.queryBySettingKey("search_keys");
        if (nxtSetting != null && nxtSetting.getSettingValue() != null){
            String[] values = nxtSetting.getSettingValue().split(" ");
            resultList = Arrays.asList(values);
        }
        return resultList;

    }

}
