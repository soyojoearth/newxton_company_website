package com.newxton.nxtframework.schedule.task;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.entity.NxtCommission;
import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.service.NxtCommissionService;
import com.newxton.nxtframework.service.NxtCronjobService;
import com.newxton.nxtframework.struct.NxtStructSettingCommission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/5
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtTaskCommission {

    private Logger logger = LoggerFactory.getLogger(NxtTaskCommission.class);

    @Resource
    private NxtCronjobService nxtCronjobService;

    @Resource
    private NxtCommissionService nxtCommissionService;

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void checkCommissionAndComfirmCompleted(){

        NxtCronjob nxtCronjob = nxtCronjobService.queryByKey("checkCommissionAndComfirmCompleted");

        if (nxtCronjob == null){
            //任务没有执行过
            NxtCronjob nxtCronjobNew = new NxtCronjob();
            nxtCronjobNew.setJobName("checkCommissionAndComfirmCompleted");
            nxtCronjobNew.setJobKey("checkCommissionAndComfirmCompleted");
            nxtCronjobNew.setJobStatus(1);//0:off(任务未开启) 1:on(任务等待执行)
            try {
                nxtCronjobService.insert(nxtCronjobNew);
            }
            catch (Exception e){
                logger.info("没成功insert，可能其它实际例子已经insert");
            }
        }

        /**
         * 防止集群多个实例并发执行，只需要一个实例在执行即可
         */
        nxtCronjob = nxtCronjobService.queryByKeyForUpdate("checkCommissionAndComfirmCompleted");

        if (nxtCronjob.getJobStatusDateline() != null && nxtCronjob.getJobStatusDateline() + 3600000 > System.currentTimeMillis()){
            logger.info("CronJob[检查佣金确认完成]任务，1小时内已被执行过一次，跳过");
            return;
        }

        logger.info("CronJob[检查佣金确认完成]任务，开始执行");

        //*************************任务代码**开始************************************************

        NxtStructSettingCommission nxtStructSettingCommission = nxtGlobalSettingComponent.getNxtStructSettingCommission();
        /**
         * 查询可以确认佣金完成的单子（确认收货满14天）
         */
        Long datelineReceivedLimit = System.currentTimeMillis() - nxtStructSettingCommission.getSafeDays() * 86400000L;
        List<NxtCommission> nxtCommissionList = nxtCommissionService.queryAllWaittingEndingTooLongTime(datelineReceivedLimit);

        for (NxtCommission nxtCommission : nxtCommissionList) {
            //确认佣金完成，可以结转
            nxtCommission.setDatelineEnd(System.currentTimeMillis());
            nxtCommissionService.update(nxtCommission);
        }


        //*************************任务代码**结束************************************************

        //这一轮任务执行完毕
        nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        nxtCronjobService.update(nxtCronjob);

        logger.info("CronJob[检查佣金确认完成]任务，成功执行完毕");

    }

}
