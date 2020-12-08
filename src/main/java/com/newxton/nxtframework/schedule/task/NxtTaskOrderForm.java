package com.newxton.nxtframework.schedule.task;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.entity.NxtCommission;
import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.service.NxtCommissionService;
import com.newxton.nxtframework.service.NxtCronjobService;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.struct.NxtStructSettingEcConfig;
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
 */
@Component
public class NxtTaskOrderForm {

    private Logger logger = LoggerFactory.getLogger(NxtTaskOrderForm.class);

    @Resource
    private NxtCronjobService nxtCronjobService;

    @Resource
    private NxtGlobalSettingComponent nxtGlobalSettingComponent;

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtCommissionService nxtCommissionService;

    /**
     * 检查自动确认收货
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void checkOrderFormAndComfirmReceived(){

        NxtCronjob nxtCronjob = nxtCronjobService.queryByKey("checkOrderFormAndComfirmReceived");

        if (nxtCronjob == null){
            //任务没有执行过
            NxtCronjob nxtCronjobNew = new NxtCronjob();
            nxtCronjobNew.setJobName("checkOrderFormAndComfirmReceived");
            nxtCronjobNew.setJobKey("checkOrderFormAndComfirmReceived");
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
        nxtCronjob = nxtCronjobService.queryByKeyForUpdate("checkOrderFormAndComfirmReceived");

        if (nxtCronjob.getJobStatusDateline() != null && nxtCronjob.getJobStatusDateline() + 3600000 > System.currentTimeMillis()){
            logger.info("CronJob[检查订单自动确认收货]任务，1小时内已被执行过一次，跳过");
            return;
        }

        logger.info("CronJob[检查订单自动确认收货]任务，开始执行");

        //*************************任务代码**开始************************************************

        //获取商城配置
        NxtStructSettingEcConfig nxtStructSettingEcConfig = nxtGlobalSettingComponent.getNxtStructSettingEcConfig();

        /**
         * 发货超过这个天数后，自动确认收货
         */
        Integer days = nxtStructSettingEcConfig.getAutomaticConfirmationOfReceiptTime();
        Long datelineDeliveryLimit = System.currentTimeMillis() - days * 86400000L;

        //查询所有超期等待确认收货的订单
        List<NxtOrderForm> nxtOrderFormList = nxtOrderFormService.queryAllWaittingReceivedTooLongTime(datelineDeliveryLimit);

        for (NxtOrderForm nxtOrderForm : nxtOrderFormList) {
            //自动确认收货
            nxtOrderForm.setDatelineReceived(System.currentTimeMillis());
            nxtOrderFormService.update(nxtOrderForm);
            //查询分销
            NxtCommission nxtCommissionCondition = new NxtCommission();
            nxtCommissionCondition.setOrderFormId(nxtOrderForm.getId());
            List<NxtCommission> nxtCommissionList = nxtCommissionService.queryAll(nxtCommissionCondition);
            for (NxtCommission nxtCommission : nxtCommissionList) {
                //分销单记录确认收货时间
                nxtCommission.setDatelineReceived(nxtOrderForm.getDatelineReceived());
                nxtCommissionService.update(nxtCommission);
            }
        }

        //*************************任务代码**结束************************************************

        //这一轮任务执行完毕
        nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        nxtCronjobService.update(nxtCronjob);

        logger.info("CronJob[检查订单自动确认收货]任务，成功执行完毕");

    }

}
