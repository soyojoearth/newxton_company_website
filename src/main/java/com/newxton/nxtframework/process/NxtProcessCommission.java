package com.newxton.nxtframework.process;

import com.newxton.nxtframework.entity.NxtCommission;
import com.newxton.nxtframework.entity.NxtOrderFormProduct;
import com.newxton.nxtframework.service.NxtCommissionService;
import com.newxton.nxtframework.service.NxtOrderFormProductService;
import com.newxton.nxtframework.struct.NxtStructUserCommission;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/29
 * @address Shenzhen, China
 */
@Component
public class NxtProcessCommission {

    @Resource
    private NxtCommissionService nxtCommissionService;

    @Resource
    private NxtOrderFormProductService nxtOrderFormProductService;

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
