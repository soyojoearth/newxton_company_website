package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtReviews)实体类
 *
 * @author makejava
 * @since 2020-11-14 21:45:45
 */
public class NxtReviews implements Serializable {
    private static final long serialVersionUID = 588829151304728628L;
    /**
     * 购买评论
     */
    private Long id;
    /**
     * 0:用户评 1:管理员回复
     */
    private Integer originType;
    /**
     * 用户或管理员id
     */
    private Long userId;

    private Long productId;

    private Long orderFormId;

    private Long orderFormProductId;

    private Long dateline;

    private String content;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOriginType() {
        return originType;
    }

    public void setOriginType(Integer originType) {
        this.originType = originType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(Long orderFormId) {
        this.orderFormId = orderFormId;
    }

    public Long getOrderFormProductId() {
        return orderFormProductId;
    }

    public void setOrderFormProductId(Long orderFormProductId) {
        this.orderFormProductId = orderFormProductId;
    }

    public Long getDateline() {
        return dateline;
    }

    public void setDateline(Long dateline) {
        this.dateline = dateline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}