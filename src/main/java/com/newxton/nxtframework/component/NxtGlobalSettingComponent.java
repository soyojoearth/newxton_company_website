package com.newxton.nxtframework.component;

import com.newxton.nxtframework.entity.NxtSetting;
import com.newxton.nxtframework.service.NxtSettingService;
import com.newxton.nxtframework.service.NxtUploadfileService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/18
 * @address Shenzhen, China
 *
 * 全局设置，放置在数据库的nxt_setting表中
 */
@Component
public class NxtGlobalSettingComponent {

    @Resource
    private NxtSettingService nxtSettingService;

    /**
     * 查询多个key的设置
     * @param keys
     * @return
     */
    public Map<String,Object> getSettingsByKeys(List<String> keys){
        List<NxtSetting> nxtSettingList = nxtSettingService.selectByKeySet(keys);
        Map<String,Object> resultMap = new HashMap<>();
        for (NxtSetting nxtSetting : nxtSettingList) {
            resultMap.put(nxtSetting.getSettingKey(),nxtSetting);
        }
        return resultMap;
    }



}
