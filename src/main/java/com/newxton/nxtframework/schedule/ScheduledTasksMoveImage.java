package com.newxton.nxtframework.schedule;

import com.newxton.nxtframework.component.NxtImageTransferComponent;
import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.service.NxtCronjobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/8
 * @address Shenzhen, China
 * Cronjob 移动图片
 */
@Component
public class ScheduledTasksMoveImage {

    private Logger logger = LoggerFactory.getLogger(ScheduledTasksMoveImage.class);

    @Resource
    private NxtCronjobService nxtCronjobService;

    @Resource
    private NxtImageTransferComponent nxtImageTransferComponent;

    @Scheduled(fixedDelay = 5000)
    public void moveQiniuImageToLocal() {

        //检查是否有图片搬运任务
        NxtCronjob nxtCronjob = nxtCronjobService.queryByKey("moveQiniuImageToLocal");
        if (nxtCronjob == null){
            return;
        }
        if (nxtCronjob.getJobStatus() != null && nxtCronjob.getJobStatus() < 1){
            return;//任务没打开
        }

        logger.info("moveQiniuImageToLocal Start:");

        //一次移动100张
        int limit = 100;
        int count = nxtImageTransferComponent.moveQiniuImageToLocal(0,limit);
        nxtCronjob.setJobStatusDateline(System.currentTimeMillis());

        if (count == 0){
            //任务结束
            nxtCronjob.setJobStatus(0);
            nxtCronjob.setJobStatusDescription("任务结束");
        }
        else {
            nxtCronjob.setJobStatusDescription("移动了"+count+"张");
        }

        nxtCronjobService.update(nxtCronjob);

        logger.info("moveQiniuImageToLocal:"+nxtCronjob.getJobStatusDescription());

    }

    @Scheduled(fixedDelay = 5000)
    public void moveLocalImageToQiniu() {

        //检查是否有图片搬运任务
        NxtCronjob nxtCronjob = nxtCronjobService.queryByKey("moveLocalImageToQiniu");
        if (nxtCronjob == null){
            return;
        }
        if (nxtCronjob.getJobStatus() != null && nxtCronjob.getJobStatus() < 1){
            return;//任务没打开
        }
        logger.info("moveLocalImageToQiniu Start");

        //一次移动100张
        int limit = 100;
        int count = nxtImageTransferComponent.moveLocalImageToQiniu(0,limit);
        nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        if (count == 0){
            //任务结束
            nxtCronjob.setJobStatus(0);
            nxtCronjob.setJobStatusDescription("任务结束");
        }
        else {
            nxtCronjob.setJobStatusDescription("移动了"+count+"张");
        }
        nxtCronjobService.update(nxtCronjob);
        logger.info("moveLocalImageToQiniu Result:"+nxtCronjob.getJobStatusDescription());

    }

}
