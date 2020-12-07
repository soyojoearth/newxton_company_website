package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtReviews)实体类
 *
 * @author makejava
 * @since 2020-12-07 15:05:27
 */
public class NxtReviews implements Serializable {
    private static final long serialVersionUID = 964679694093388825L;
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
    /**
    * 根评论id
    */
    private Long parentId;
    
    private Integer isRecommend;
    
    private Integer isHidden;


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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Integer isHidden) {
        this.isHidden = isHidden;
    }

}