package com.newxton.nxtframework.schedule;

import com.newxton.nxtframework.component.NxtAclComponent;
import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/8
 * @address Shenzhen, China
 * Cronjob 缓存清理
 */
@Component
public class NxtCronCacheClean {

    private Logger logger = LoggerFactory.getLogger(NxtCronCacheClean.class);

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @Resource
    private NxtAclComponent nxtAclComponent;

    @Scheduled(fixedDelay = 15000)
    public void cleanSettingCache() {
        //15秒清理一次Setting缓存
        nxtGlobalSettingComponent.cleanCache();
    }

    @Scheduled(fixedDelay = 15000)
    public void cleanAclCache() {
        //15秒清理一次ACL缓存
        nxtAclComponent.cleanCache();
    }

}
