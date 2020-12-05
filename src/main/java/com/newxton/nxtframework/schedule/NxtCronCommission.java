package com.newxton.nxtframework.schedule;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.service.NxtCronjobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/8
 * @address Shenzhen, China
 * Cronjob 分销佣金
 */
@Component
public class NxtCronCommission {

    private Logger logger = LoggerFactory.getLogger(NxtCronCommission.class);
    
    @Resource
    private NxtCronjobService nxtCronjobService;

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    /**
     * TODO
     * 佣金检查，自动确认佣金完成
     */
    @Scheduled(fixedDelay = 3600000)
    public void autoConfirmCommissionCompleted() {

        /**
         * TODO
         */
        /**
         * 确认收货15天后，没有交易纠纷，佣金设置"可结算"
         */

    }

}
