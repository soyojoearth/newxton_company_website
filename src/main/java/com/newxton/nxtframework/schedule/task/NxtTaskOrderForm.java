package com.newxton.nxtframework.schedule.task;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.entity.NxtCommission;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.service.NxtCommissionService;
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
 * @copyright NxtFramework
 */
@Component
public class NxtTaskOrderForm {

    private Logger logger = LoggerFactory.getLogger(NxtTaskOrderForm.class);

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

        logger.info("[检查订单自动确认收货]任务，开始执行");

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

        logger.info("[检查订单自动确认收货]任务，成功执行完毕");

    }

}
