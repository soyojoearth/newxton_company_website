package com.newxton.nxtframework;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/9/29
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Configuration
public class NxtScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //设定一个长度20的定时任务线程池
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(20));
    }

}
