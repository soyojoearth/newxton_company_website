package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtRecharge;
import com.newxton.nxtframework.entity.NxtTransaction;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.entity.NxtWithdraw;
import com.newxton.nxtframework.service.NxtRechargeService;
import com.newxton.nxtframework.service.NxtTransactionService;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.service.NxtWithdrawService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminMemberUpdateBalanceController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtRechargeService nxtRechargeService;

    @Resource
    private NxtWithdrawService nxtWithdrawService;

    @Resource
    private NxtTransactionService nxtTransactionService;



    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/api/admin/member/update_balance", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long userId = jsonParam.getLong("userId");
        Float amount = jsonParam.getFloat("amount");
        String remark = jsonParam.getString("remark");

        NxtUser nxtUser = nxtUserService.queryById(userId);

        if (nxtUser == null) {
            return new NxtStructApiResult(53, "找不到该会员").toMap();
        }



        //增减资
        if (amount != null){

            Long amountLong = (long)(amount * 100L);

            if (amountLong > 0){

                if (remark == null || remark.trim().isEmpty()){
                    remark = "后台手动增资";
                }
                else {
                    remark = "后台手动增资:"+remark;
                }

                //现金充值
                NxtRecharge nxtRecharge = new NxtRecharge();
                nxtRecharge.setUserId(nxtUser.getId());
                nxtRecharge.setStatus(0);//状态（0:正在充值 1:成功 -1:失败）
                nxtRecharge.setPlatform(888);//平台（0:银行 1:微信 2:支付宝 3:paypal 888:现金）
                nxtRecharge.setDateline(System.currentTimeMillis());
                nxtRecharge.setAmount(amountLong);
                nxtRecharge.setRemark(remark);
                nxtRechargeService.insert(nxtRecharge);

                //插入账本
                NxtTransaction nxtTransaction = new NxtTransaction();
                nxtTransaction.setUserId(nxtUser.getId());
                nxtTransaction.setType(1);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
                nxtTransaction.setOuterId(nxtRecharge.getId());
                nxtTransaction.setDateline(nxtRecharge.getDateline());
                nxtTransaction.setAmount(nxtRecharge.getAmount());
                nxtTransactionService.insert(nxtTransaction);

                //充值成功
                nxtRecharge.setTransactionId(nxtTransaction.getId());
                nxtRecharge.setStatus(1);//状态（0:正在充值 1:成功 -1:失败）
                nxtRechargeService.update(nxtRecharge);

            }

            if (amountLong < 0){

                if (remark == null || remark.trim().isEmpty()){
                    remark = "后台手动减资";
                }
                else {
                    remark = "后台手动减资:"+remark;
                }

                //插入账本
                NxtTransaction nxtTransaction = new NxtTransaction();
                nxtTransaction.setUserId(nxtUser.getId());
                nxtTransaction.setType(4);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
                nxtTransaction.setDateline(System.currentTimeMillis());
                nxtTransaction.setAmount(amountLong);
                nxtTransactionService.insert(nxtTransaction);

                //现金渠道提现
                NxtWithdraw nxtWithdraw = new NxtWithdraw();
                nxtWithdraw.setUserId(nxtUser.getId());
                nxtWithdraw.setTransactionId(nxtTransaction.getId());
                nxtWithdraw.setStatus(3);//状态（0:已申请 1:已批准 2:已拒绝 3:已汇款）
                nxtWithdraw.setAmount(amountLong);
                nxtWithdraw.setDatelineCreate(System.currentTimeMillis());
                nxtWithdraw.setDatelineEnd(System.currentTimeMillis());
                nxtWithdraw.setPlatform(888);//平台（0:银行 1:微信 2:支付宝 3:paypal 888:现金）
                nxtWithdraw.setPlatformPerson(nxtUser.getUsername());
                nxtWithdraw.setPlatformAccount(nxtUser.getUsername());
                nxtWithdraw.setRemarkAdmin(remark);
                nxtWithdrawService.insert(nxtWithdraw);

                //(减资)提现成功
                nxtTransaction.setOuterId(nxtWithdraw.getId());
                nxtTransactionService.update(nxtTransaction);

            }

        }


        return new NxtStructApiResult().toMap();

    }

}
