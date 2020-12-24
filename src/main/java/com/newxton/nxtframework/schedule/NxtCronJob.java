package com.newxton.nxtframework.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/8
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtCronJob {

    private Logger logger = LoggerFactory.getLogger(NxtCronJob.class);

    @Scheduled(fixedDelay = 15000)
    public void readme() {
        //不要在无状态web实例中设置有状态的Schedule cronjob，集群使用也不用多担心什么问题。
        //如有需要，请另外做个SpringBoot实例专门用来做cronjob
    }

}
