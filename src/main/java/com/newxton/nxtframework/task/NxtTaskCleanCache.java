package com.newxton.nxtframework.task;

import com.newxton.nxtframework.component.NxtAclComponent;
import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/25
 * @address Shenzhen, China
 */
@Component
public class NxtTaskCleanCache {

    private Logger logger = LoggerFactory.getLogger(NxtTaskCleanCache.class);

    @Resource
    private NxtAclComponent nxtAclComponent;

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    /**
     * 清理Acl缓存
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void cleanAclCache() {
        nxtAclComponent.cleanCache();
        logger.info("cleanAclCache");
    }

    /**
     * 清理全局设置缓存
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void cleanSettingValueCache() {
        nxtGlobalSettingComponent.cleanSettingValueCache();
        logger.info("cleanSettingValueCache");
    }

}
