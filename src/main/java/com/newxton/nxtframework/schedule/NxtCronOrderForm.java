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
 * Cronjob 订单
 */
@Component
public class NxtCronOrderForm {

    private Logger logger = LoggerFactory.getLogger(NxtCronOrderForm.class);

    @Resource
    private NxtCronjobService nxtCronjobService;

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;


    /**
     * TODO
     * 订单扫描，自动确认收货（每隔3600秒执行一次）
     */
    @Scheduled(fixedDelay = 3600000)
    public void autoConfirmOrderFormReceive() {

        /**
         * TODO
         */

        /**
         * 发货超过这个天数后，自动确认收货
         */
        Integer days = 15;

        //1、确认订单
        //已发货，

    }

}
