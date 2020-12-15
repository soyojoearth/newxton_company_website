package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructOrderFormRefundCreate {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 勾选的退货物品id数组
     */
    private List<NxtStructOrderFormRefundProduct> orderFormProductList = new ArrayList<>();

    /**
     * 0:无理由 1:质量问题
     */
    private Integer reasonType;

    /**
     * 退货原因描述
     */
    private String reasionDescription;

    /**
     * 附带上传的图片id数组
     */
    private List<Long> imageIdList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<NxtStructOrderFormRefundProduct> getOrderFormProductList() {
        return orderFormProductList;
    }

    public void setOrderFormProductList(List<NxtStructOrderFormRefundProduct> orderFormProductList) {
        this.orderFormProductList = orderFormProductList;
    }

    public Integer getReasonType() {
        return reasonType;
    }

    public void setReasonType(Integer reasonType) {
        this.reasonType = reasonType;
    }

    public String getReasionDescription() {
        return reasionDescription;
    }

    public void setReasionDescription(String reasionDescription) {
        this.reasionDescription = reasionDescription;
    }

    public List<Long> getImageIdList() {
        return imageIdList;
    }

    public void setImageIdList(List<Long> imageIdList) {
        this.imageIdList = imageIdList;
    }

}
