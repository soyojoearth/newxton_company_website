package com.newxton.nxtframework.controller.api.front;

import com.newxton.nxtframework.entity.NxtSetting;
import com.newxton.nxtframework.service.NxtSettingService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/18
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiHotKeywordsController {

    @Resource
    private NxtSettingService nxtSettingService;

    @RequestMapping("/api/hot_keywords")
    public NxtStructApiResult exec() {

        //热门搜索
        List<String> hotKeywordList = getHotKeyword();

        return new NxtStructApiResult(hotKeywordList);

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
