package com.newxton.nxtframework.schedule.task;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.entity.NxtCommission;
import com.newxton.nxtframework.service.NxtCommissionService;
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
    private NxtCommissionService nxtCommissionService;

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void checkCommissionAndComfirmCompleted(){

        logger.info("[检查佣金确认完成]任务，开始执行");

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


        logger.info("[检查佣金确认完成]任务，成功执行完毕");

    }

}
