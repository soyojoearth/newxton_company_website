package com.newxton.nxtframework.struct.admin;

import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/6
 * @address Shenzhen, China
 */
public class NxtStructAdminOrderFormRefundApprovalPost {
    private Long id;
    private Integer status;
    private String remark;
    private List<NxtStructAdminOrderFormRefundApprovalPostItemAmount> refundAmountList;
    private Boolean refundDeliveryCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<NxtStructAdminOrderFormRefundApprovalPostItemAmount> getRefundAmountList() {
        return refundAmountList;
    }

    public void setRefundAmountList(List<NxtStructAdminOrderFormRefundApprovalPostItemAmount> refundAmountList) {
        this.refundAmountList = refundAmountList;
    }

    public Boolean getRefundDeliveryCost() {
        return refundDeliveryCost;
    }

    public void setRefundDeliveryCost(Boolean refundDeliveryCost) {
        this.refundDeliveryCost = refundDeliveryCost;
    }
}
