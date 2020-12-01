package com.newxton.nxtframework.schedule;

import com.newxton.nxtframework.component.NxtImageTransferComponent;
import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.service.NxtCronjobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
public class ScheduledMoveImage {

    private Logger logger = LoggerFactory.getLogger(ScheduledMoveImage.class);

    private Map<String,Long> lastJobStatusDatelineMap = new HashMap<>();

    @Resource
    private NxtCronjobService nxtCronjobService;

    @Resource
    private NxtImageTransferComponent nxtImageTransferComponent;

    @Scheduled(fixedDelay = 5000)
    public void checkCronJob() {
        //仅查询10分钟之内更新过status的任务
        NxtCronjob nxtCronjobCondition = new NxtCronjob();
        nxtCronjobCondition.setJobStatusDateline(System.currentTimeMillis()-600000);
        List<NxtCronjob> list = nxtCronjobService.queryAllGreaterThanStatusDateline(nxtCronjobCondition);
        for (NxtCronjob nxtCronjob : list) {
            //检查、执行Job(阻塞式)
            this.checkAndRunTask(nxtCronjob);
        }
    }

    /**
     * 检查、执行Job(阻塞式)
     * @param nxtCronjob
     */
    private void checkAndRunTask(NxtCronjob nxtCronjob){
        String jobKey = nxtCronjob.getJobKey();
        Integer jobStatus = nxtCronjob.getJobStatus();
        Long jobStatusDatelne = nxtCronjob.getJobStatusDateline();
        if (jobKey == null){
            return;
        }
        else {
            //分发任务
            if (jobKey.equals("moveLocalImageToQiniu") && jobStatus.equals(1)){
                //把本地图片移动到七牛云
                this.moveLocalImageToQiniu();
            }
            else if (jobKey.equals("moveQiniuImageToLocal") && jobStatus.equals(1)){
                //把七牛云图片搬到本地
                this.moveQiniuImageToLocal();
            }
        }
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void moveQiniuImageToLocal() {

        // 由于实例并不一定只部署成单机一份，也可能由k8s集群部署好几个实例
        // 那么如果希望只让一个实例执行任务，就锁上这一行，执行完才由事务自动解锁

        //检查是否有图片搬运任务
        NxtCronjob nxtCronjob = nxtCronjobService.queryByKeyForUpdate("moveQiniuImageToLocal");
        if (nxtCronjob == null || nxtCronjob.getJobStatus() == null){
            return;
        }
        if (!nxtCronjob.getJobStatus().equals(1)){
            return;//0:off(任务未开启) 1:on(任务等待执行)
        }

        logger.info("moveQiniuImageToLocal Start");

        //一次移动limit张
        int limit = 20;
        int count = nxtImageTransferComponent.moveQiniuImageToLocal(0,limit);
        nxtCronjob.setJobStatusDateline(System.currentTimeMillis());

        if (count == 0){
            //任务结束
            nxtCronjob.setJobStatus(0);
            nxtCronjob.setJobStatusDescription("任务结束");
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        }
        else {
            nxtCronjob.setJobStatus(1);//改回1，下一轮再抢坑
            nxtCronjob.setJobStatusDescription("移动了"+count+"张");
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        }

        nxtCronjobService.update(nxtCronjob);

        logger.info("moveQiniuImageToLocal:"+nxtCronjob.getJobStatusDescription());

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void moveLocalImageToQiniu() {

        // 由于实例并不一定只部署成单机一份，也可能由k8s集群部署好几个实例
        // 那么如果希望只让一个实例执行任务，就锁上这一行，执行完才由事务自动解锁

        //检查是否有图片搬运任务
        NxtCronjob nxtCronjob = nxtCronjobService.queryByKeyForUpdate("moveLocalImageToQiniu");
        if (nxtCronjob == null || nxtCronjob.getJobStatus() == null){
            return;
        }
        if (!nxtCronjob.getJobStatus().equals(1)){
            return;//0:off(任务未开启) 1:on(任务等待执行)
        }

        logger.info("moveLocalImageToQiniu Start");

        //一次移动limit张
        int limit = 20;
        int count = nxtImageTransferComponent.moveLocalImageToQiniu(0,limit);
        nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        if (count == 0){
            //任务结束
            nxtCronjob.setJobStatus(0);
            nxtCronjob.setJobStatusDescription("任务结束");
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        }
        else {
            nxtCronjob.setJobStatus(1);//改回1，下一轮再抢坑
            nxtCronjob.setJobStatusDescription("移动了"+count+"张");
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        }
        nxtCronjobService.update(nxtCronjob);
        logger.info("moveLocalImageToQiniu Result:"+nxtCronjob.getJobStatusDescription());

    }

}
