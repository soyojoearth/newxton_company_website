package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/27
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructOrderFormReivewsCreate {

    /**
     * 订单id
     */
    private Long orderFormId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 订单物品id
     */
    private Long orderFormProductId;
    /**
     * 附加图片id列表
     */
    private List<Long> imageIdList = new ArrayList<>();

    public Long getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(Long orderFormId) {
        this.orderFormId = orderFormId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getOrderFormProductId() {
        return orderFormProductId;
    }

    public void setOrderFormProductId(Long orderFormProductId) {
        this.orderFormProductId = orderFormProductId;
    }

    public List<Long> getImageIdList() {
        return imageIdList;
    }

    public void setImageIdList(List<Long> imageIdList) {
        this.imageIdList = imageIdList;
    }

}
