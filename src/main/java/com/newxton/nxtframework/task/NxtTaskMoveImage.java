package com.newxton.nxtframework.task;

import com.newxton.nxtframework.component.NxtImageTransferComponent;
import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.service.NxtCronjobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/23
 * @address Shenzhen, China
 */
@Component
public class NxtTaskMoveImage {

    private Logger logger = LoggerFactory.getLogger(NxtTaskMoveImage.class);

    @Resource
    private NxtCronjobService nxtCronjobService;

    @Resource
    private NxtImageTransferComponent nxtImageTransferComponent;

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
            nxtCronjob.setJobStatusDescription("移动了"+count+"张");
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        }
        nxtCronjobService.update(nxtCronjob);
        logger.info("moveLocalImageToQiniu Result:"+nxtCronjob.getJobStatusDescription());

    }


}
