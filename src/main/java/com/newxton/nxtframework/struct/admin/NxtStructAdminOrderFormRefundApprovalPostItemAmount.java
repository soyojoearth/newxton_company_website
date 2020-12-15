package com.newxton.nxtframework.struct.admin;

import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/6
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructAdminOrderFormRefundApprovalPostItemAmount {
    private Long orderFromRefundProductId;
    private Float orderFromRefundProductAmount;

    public Long getOrderFromRefundProductId() {
        return orderFromRefundProductId;
    }

    public void setOrderFromRefundProductId(Long orderFromRefundProductId) {
        this.orderFromRefundProductId = orderFromRefundProductId;
    }

    public Float getOrderFromRefundProductAmount() {
        return orderFromRefundProductAmount;
    }

    public void setOrderFromRefundProductAmount(Float orderFromRefundProductAmount) {
        this.orderFromRefundProductAmount = orderFromRefundProductAmount;
    }

}
