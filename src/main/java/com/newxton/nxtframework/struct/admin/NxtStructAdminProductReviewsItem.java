package com.newxton.nxtframework.struct.admin;

import org.springframework.data.relational.core.sql.In;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/7
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructAdminProductReviewsItem {

    /**
     * 评论id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 评论时间
     */
    private String date;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品图片
     */
    private String productPicUrl;
    /**
     * 订单id
     */
    private Long orderFormId;

    private Boolean isHidden;
    private Boolean isRecommend;

    /**
     * 来源类型
     */
    private Integer originType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPicUrl() {
        return productPicUrl;
    }

    public void setProductPicUrl(String productPicUrl) {
        this.productPicUrl = productPicUrl;
    }

    public Long getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(Long orderFormId) {
        this.orderFormId = orderFormId;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public Boolean getRecommend() {
        return isRecommend;
    }

    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    public Integer getOriginType() {
        return originType;
    }

    public void setOriginType(Integer originType) {
        this.originType = originType;
    }
}
