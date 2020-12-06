package com.newxton.nxtframework.schedule;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.schedule.task.NxtTaskCommission;
import com.newxton.nxtframework.schedule.task.NxtTaskOrderForm;
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
    private NxtTaskCommission nxtTaskCommission;

    /**
     * TODO
     * （每隔3600秒执行一次）
     * （确认收货满14天）确认佣金完成，可以结转
     */
    @Scheduled(fixedDelay = 3600000)
    public void autoConfirmOrderFormReceive() {

        /**
         * （确认收货满14天）
         */
        //检查&执行佣金完成，可以结转
        nxtTaskCommission.checkCommissionAndComfirmCompleted();

    }

}
