package com.newxton.nxtframework.process;

import com.newxton.nxtframework.entity.NxtCommission;
import com.newxton.nxtframework.entity.NxtCommissionTransferIn;
import com.newxton.nxtframework.entity.NxtOrderFormProduct;
import com.newxton.nxtframework.entity.NxtTransaction;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtCommissionService;
import com.newxton.nxtframework.service.NxtCommissionTransferInService;
import com.newxton.nxtframework.service.NxtOrderFormProductService;
import com.newxton.nxtframework.service.NxtTransactionService;
import com.newxton.nxtframework.struct.NxtStructUserCommission;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/29
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtProcessCommission {

    @Resource
    private NxtCommissionService nxtCommissionService;

    @Resource
    private NxtOrderFormProductService nxtOrderFormProductService;

    @Resource
    private NxtCommissionTransferInService nxtCommissionTransferInService;

    @Resource
    private NxtTransactionService nxtTransactionService;

    /**
     * 个人中心--申请结转全部佣金到余额
     * @param userId
     */
    @Transactional(rollbackFor=Exception.class)
    public void userCashOut(Long userId) throws NxtException {

        //查询所有已完成交易、且未结转的佣金记录
        List<NxtCommission> nxtCommissionList = nxtCommissionService.queryAllAllowTransferByUserId(userId);

        //可结转收益总额
        Long balanceAllowTransfer = 0L;
        for (NxtCommission nxtCommission : nxtCommissionList) {
            //未退货退款数量
            Long quantityNotRefund = nxtCommission.getQuantityDeal() - nxtCommission.getQuantityRefund();
            //可结转收益
            balanceAllowTransfer += nxtCommission.getCommissionAmount() * quantityNotRefund;
            if (quantityNotRefund > 0 && nxtCommission.getCommissionAmount() > 0) {
                //结转标记
                nxtCommission.setIsTransfer(1);
                nxtCommissionService.update(nxtCommission);
            }
        }

        if (balanceAllowTransfer <= 0L){
            throw new NxtException("可结转金额不足");
        }

        //创建结转记录（然后等待后台管理员审核）【现在变了，实时到账吧】
        NxtCommissionTransferIn nxtCommissionTransferIn = new NxtCommissionTransferIn();
        nxtCommissionTransferIn.setUserId(userId);
        nxtCommissionTransferIn.setAmount(balanceAllowTransfer);
        nxtCommissionTransferIn.setDatelineCreate(System.currentTimeMillis());
        nxtCommissionTransferIn.setStatus(0);//状态（0等待审核 1通过 -1驳回）

        nxtCommissionTransferInService.insert(nxtCommissionTransferIn);

        //结转id记录
        for (NxtCommission nxtCommission : nxtCommissionList) {
            //未退货退款数量
            Long quantityNotRefund = nxtCommission.getQuantityDeal() - nxtCommission.getQuantityRefund();
            if (quantityNotRefund > 0 && nxtCommission.getCommissionAmount() > 0) {
                //结转id
                nxtCommission.setCommissionTransferInId(nxtCommissionTransferIn.getId());
                nxtCommissionService.update(nxtCommission);
            }
        }

        //实时到账
        NxtTransaction nxtTransaction = new NxtTransaction();
        nxtTransaction.setType(6);//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
        nxtTransaction.setUserId(userId);
        nxtTransaction.setAmount(nxtCommissionTransferIn.getAmount());
        nxtTransaction.setDateline(System.currentTimeMillis());
        nxtTransaction.setOuterId(nxtCommissionTransferIn.getId());
        nxtTransactionService.insert(nxtTransaction);

        nxtCommissionTransferIn.setDatelineEnd(System.currentTimeMillis());
        nxtCommissionTransferIn.setTransactionId(nxtTransaction.getId());
        nxtCommissionTransferIn.setStatus(1);//状态（0等待审核 1通过 -1驳回）
        nxtCommissionTransferInService.update(nxtCommissionTransferIn);


    }

    /**
     * 获取用户的收益列表
     * @param offset
     * @param limit
     * @param userId
     * @return
     */
    public List<NxtStructUserCommission> userCommissionList(Long offset,Long limit,Long userId){

        List<NxtCommission> nxtCommissionList = nxtCommissionService.queryAllByUserIdLimit(offset,limit,userId);

        List<NxtStructUserCommission> nxtStructUserCommissionList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Long> orderFormProductIdList = new ArrayList<>();

        for (NxtCommission nxtCommission:nxtCommissionList) {
            orderFormProductIdList.add(nxtCommission.getOrderFormProductId());
        }

        Map<Long,String> mapOrderFormProductIdToProductName = new HashMap<>();
        List<NxtOrderFormProduct> nxtOrderFormProductList = nxtOrderFormProductService.selectAllByIdSet(orderFormProductIdList);
        for (NxtOrderFormProduct item :
                nxtOrderFormProductList) {
            mapOrderFormProductIdToProductName.put(item.getId(),item.getProductName());
        }

        for (NxtCommission nxtCommission:nxtCommissionList) {
            NxtStructUserCommission nxtStructUserCommission = new NxtStructUserCommission();
            nxtStructUserCommissionList.add(nxtStructUserCommission);

            nxtStructUserCommission.setId(nxtCommission.getId());
            nxtStructUserCommission.setDatelineReadable(sdf.format(new Date(nxtCommission.getDatelineCreate())));
            nxtStructUserCommission.setAmount(nxtCommission.getCommissionAmount()/100F);
            nxtStructUserCommission.setStatusText(this.statusText(nxtCommission));
            nxtStructUserCommission.setProductName(mapOrderFormProductIdToProductName.getOrDefault(nxtCommission.getOrderFormProductId(),"---"));
            orderFormProductIdList.add(nxtCommission.getOrderFormProductId());

        }

        return nxtStructUserCommissionList;

    }

    private String statusText(NxtCommission nxtCommission){
        String s1;
        if (nxtCommission.getIsPaid().equals(1)){
            s1 = "已支付";
        }
        else {
            s1 = "等待支付";
        }
        String s2;
        if (nxtCommission.getQuantityRefund()>0){
            s2 = "退货["+nxtCommission.getQuantityRefund()+"/"+nxtCommission.getQuantityDeal()+"]";
        }
        else {
            s2 = "正常";
        }
        String s3;
        if (nxtCommission.getIsTransfer().equals(1)){
            s3 = "已结转";
        }
        else {
            if (nxtCommission.getDatelineEnd() == null){
                s3 = "进行中";
            }
            else {
                s3 = "完成";
            }
        }
        return s1+";"+s2+";"+s3;
    }

}
