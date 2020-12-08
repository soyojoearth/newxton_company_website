package com.newxton.nxtframework.schedule;

import com.newxton.nxtframework.schedule.task.NxtTaskOrderForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


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
    private NxtTaskOrderForm nxtTaskOrderForm;

    /**
     * TODO
     * 订单扫描，自动确认收货（每隔3600秒执行一次）
     */
    @Scheduled(fixedDelay = 3600000)
    public void autoConfirmOrderFormReceive() {

        //检查&自动确认收货
        nxtTaskOrderForm.checkOrderFormAndComfirmReceived();

    }

}
