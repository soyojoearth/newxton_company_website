package com.newxton.nxtframework.schedule;

import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.schedule.task.NxtTaskMoveImage;
import com.newxton.nxtframework.service.NxtCronjobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/8
 * @address Shenzhen, China
 * Cronjob 自动图片搬运
 */
@Component
public class NxtCronMoveImage {

    private Logger logger = LoggerFactory.getLogger(NxtCronMoveImage.class);

    private Map<String,Long> lastJobStatusDatelineMap = new HashMap<>();

    @Resource
    private NxtCronjobService nxtCronjobService;

    @Resource
    private NxtTaskMoveImage nxtTaskMoveImage;

    @Scheduled(fixedDelay = 5000)
    public void checkCronJob() {

        Boolean working = false;

        NxtCronjob nxtCronjobToQiniu = nxtCronjobService.queryByKey("moveLocalImageToQiniu");

        if (!working && nxtCronjobToQiniu != null && nxtCronjobToQiniu.getJobStatus().equals(1)){//0:off(任务未开启) 1:on(任务等待执行)
            //把本地图片移动到七牛云
            nxtTaskMoveImage.moveLocalImageToQiniu();
            working = true;
        }

        NxtCronjob nxtCronjobToLocal = nxtCronjobService.queryByKey("moveQiniuImageToLocal");

        if (!working && nxtCronjobToLocal != null && nxtCronjobToLocal.getJobStatus().equals(1)){//0:off(任务未开启) 1:on(任务等待执行)
            //把七牛云图片搬到本地
            nxtTaskMoveImage.moveQiniuImageToLocal();
            working = true;
        }

    }

}
