package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/29
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructUserCommission {

    private Long id;
    private String datelineReadable;
    private Float amount;
    private String statusText;
    private String productName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatelineReadable() {
        return datelineReadable;
    }

    public void setDatelineReadable(String datelineReadable) {
        this.datelineReadable = datelineReadable;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
