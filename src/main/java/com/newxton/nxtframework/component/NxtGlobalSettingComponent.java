package com.newxton.nxtframework.component;

import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.entity.NxtSetting;
import com.newxton.nxtframework.service.NxtCronjobService;
import com.newxton.nxtframework.service.NxtSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    private Logger logger = LoggerFactory.getLogger(NxtGlobalSettingComponent.class);

    @Resource
    private NxtSettingService nxtSettingService;

    @Resource
    private NxtCronjobService nxtCronjobService;

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

    @Cacheable("getSettingValueInCache")
    public String getSettingValueInCache(String key){
        logger.info("getSettingValue："+key);
        NxtSetting nxtSetting = nxtSettingService.queryBySettingKey(key);
        if (nxtSetting == null || nxtSetting.getSettingValue() == null){
            return null;
        }
        else {
            return nxtSetting.getSettingValue().trim();
        }
    }

    @CacheEvict(cacheNames = {"getSettingValueInCache"},allEntries = true,beforeInvocation = false)
    public void cleanSettingValueCache(){
        // 注解 @CacheEvict 就执行了清理，代码块里面什么也不用写
    }

    /**
     * 清理缓存
     * 由于实例可能由k8s部署多个，所以需要清除每个实例的缓存，就提交任务，分布式执行
     *
     */
    public void addJobForCleanSettingValueCache(){
        NxtCronjob nxtCronjob = nxtCronjobService.queryByKeyForUpdate("cleanSettingValueCache");
        if (nxtCronjob == null){
            nxtCronjob = new NxtCronjob();
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
            nxtCronjob.setJobStatus(1);
            nxtCronjob.setJobName("清理SettingValue缓存");
            nxtCronjob.setJobKey("cleanSettingValueCache");
            nxtCronjobService.insert(nxtCronjob);
        }
        else {
            nxtCronjob.setJobStatus(1);
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
            nxtCronjobService.update(nxtCronjob);
        }
    }

}
