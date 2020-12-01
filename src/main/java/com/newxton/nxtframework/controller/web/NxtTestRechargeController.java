package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 */
@Controller
public class NxtTestRechargeController {

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtRechargeService nxtRechargeService;

    @Resource
    private NxtTransactionService nxtTransactionService;

    @Resource
    private NxtOrderFormPayService nxtOrderFormPayService;

    @Resource
    private NxtCommissionService nxtCommissionService;

    @RequestMapping("/test_recharge")
    public ModelAndView index(Device device, ModelAndView model, @Param("id") Long id) {

        /**
         * 开发期间自动充值成功
         */

        model.setViewName("pc/test_recharge");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //8秒后自动付款
                    Thread.sleep(8000);
                    //自动充值
                    autoRecharge(id);
                }
                catch (Exception e){

                }

            }
        }).start();

        return model;

    }

    /**
     * 测试自动充值
     * @param id
     */
    private void autoRecharge(Long id) {

        NxtRecharge nxtRecharge = nxtRechargeService.queryById(id);

        if (nxtRecharge == null || !nxtRecharge.getStatus().equals(0)){
            return;
        }

        //插入Transaction表
        NxtTransaction nxtTransaction = new NxtTransaction();
        nxtTransaction.setUserId(nxtRecharge.getUserId());
        nxtTransaction.setAmount(nxtRecharge.getAmount());
        nxtTransaction.setDateline(nxtRecharge.getDateline());
        nxtTransaction.setType(1);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
        nxtTransaction.setOuterId(nxtRecharge.getId());

        nxtTransactionService.insert(nxtTransaction);

        //更新充值TransactionId
        nxtRecharge.setTransactionId(nxtTransaction.getId());
        nxtRecharge.setStatus(1);
        nxtRechargeService.update(nxtRecharge);

        //充值成功了

    }

}
