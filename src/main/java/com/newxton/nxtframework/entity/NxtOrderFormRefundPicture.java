package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtOrderFormRefundPicture)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:41:56
 */
public class NxtOrderFormRefundPicture implements Serializable {
    private static final long serialVersionUID = 400604962006206442L;
    /**
     * 退货申请附加图片
     */
    private Long id;

    private Long orderFormRefundId;

    private Long uploadfileId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderFormRefundId() {
        return orderFormRefundId;
    }

    public void setOrderFormRefundId(Long orderFormRefundId) {
        this.orderFormRefundId = orderFormRefundId;
    }

    public Long getUploadfileId() {
        return uploadfileId;
    }

    public void setUploadfileId(Long uploadfileId) {
        this.uploadfileId = uploadfileId;
    }

}