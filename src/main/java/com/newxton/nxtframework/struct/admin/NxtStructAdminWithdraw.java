package com.newxton.nxtframework.struct.admin;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/10
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructAdminWithdraw {
    private Long id;
    private Long userId;
    private String username;
    private Long transactionId;
    /**
     * 状态（0:已申请 1:已批准 2:已拒绝 3:已汇款）
     */
    private Integer status;
    private String statusText;
    /**
     * 正数：提现
     */
    private Float amount;

    private Long datelineCreate;
    private String datelineCreateReadable;

    private Long datelineEnd;
    private String datelineEndReadable;

    /**
     * 平台（0:银行 1:微信 2:支付宝 3:paypal 888:现金）
     */
    private Integer platform;
    private String platformText;
    /**
     * 收款平台收款人
     */
    private String platformPerson;
    /**
     * 收款平台账号
     */
    private String platformAccount;
    /**
     * 收款平台汇款编号
     */
    private String platformSerialNum;
    /**
     * 用户备注
     */
    private String remarkUser;
    /**
     * 管理员备注
     */
    private String remarkAdmin;

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

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Long getDatelineCreate() {
        return datelineCreate;
    }

    public void setDatelineCreate(Long datelineCreate) {
        this.datelineCreate = datelineCreate;
    }

    public String getDatelineCreateReadable() {
        return datelineCreateReadable;
    }

    public void setDatelineCreateReadable(String datelineCreateReadable) {
        this.datelineCreateReadable = datelineCreateReadable;
    }

    public Long getDatelineEnd() {
        return datelineEnd;
    }

    public void setDatelineEnd(Long datelineEnd) {
        this.datelineEnd = datelineEnd;
    }

    public String getDatelineEndReadable() {
        return datelineEndReadable;
    }

    public void setDatelineEndReadable(String datelineEndReadable) {
        this.datelineEndReadable = datelineEndReadable;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getPlatformText() {
        return platformText;
    }

    public void setPlatformText(String platformText) {
        this.platformText = platformText;
    }

    public String getPlatformPerson() {
        return platformPerson;
    }

    public void setPlatformPerson(String platformPerson) {
        this.platformPerson = platformPerson;
    }

    public String getPlatformAccount() {
        return platformAccount;
    }

    public void setPlatformAccount(String platformAccount) {
        this.platformAccount = platformAccount;
    }

    public String getPlatformSerialNum() {
        return platformSerialNum;
    }

    public void setPlatformSerialNum(String platformSerialNum) {
        this.platformSerialNum = platformSerialNum;
    }

    public String getRemarkUser() {
        return remarkUser;
    }

    public void setRemarkUser(String remarkUser) {
        this.remarkUser = remarkUser;
    }

    public String getRemarkAdmin() {
        return remarkAdmin;
    }

    public void setRemarkAdmin(String remarkAdmin) {
        this.remarkAdmin = remarkAdmin;
    }
}
