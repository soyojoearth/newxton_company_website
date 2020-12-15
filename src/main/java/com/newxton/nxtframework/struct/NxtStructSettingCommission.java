package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/20
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructSettingCommission {

    /**
     * 分销开关
     */
    private Boolean onoff = true;

    /**
     * 一级分销佣金分成比例
     */
    private Long commissionRateLevel1 = 70L;//默认值
    /**
     * 二级分销佣金分成比例
     */
    private Long commissionRateLevel2 = 20L;//默认值
    /**
     * 三级分销佣金分成比例
     */
    private Long commissionRateLevel3 = 10L;//默认值

    /**
     * 全站默认产品佣金比率
     */
    private Long defaultProductCommissionRate = 10L;//默认值

    /**
     * 下家注册超过这个时间后，上家不再享有佣金提成
     */
    private Long inviterExpirationDays = 36500L;

    /**
     * 安全期：佣金售后期限（确认收货后几天，佣金才可转入余额）
     */
    private Long safeDays = 14L;//默认值

    public Boolean getOnoff() {
        return onoff;
    }

    public void setOnoff(Boolean onoff) {
        this.onoff = onoff;
    }

    public Long getCommissionRateLevel1() {
        return commissionRateLevel1;
    }

    public void setCommissionRateLevel1(Long commissionRateLevel1) {
        this.commissionRateLevel1 = commissionRateLevel1;
    }

    public Long getCommissionRateLevel2() {
        return commissionRateLevel2;
    }

    public void setCommissionRateLevel2(Long commissionRateLevel2) {
        this.commissionRateLevel2 = commissionRateLevel2;
    }

    public Long getCommissionRateLevel3() {
        return commissionRateLevel3;
    }

    public void setCommissionRateLevel3(Long commissionRateLevel3) {
        this.commissionRateLevel3 = commissionRateLevel3;
    }

    public Long getDefaultProductCommissionRate() {
        return defaultProductCommissionRate;
    }

    public void setDefaultProductCommissionRate(Long defaultProductCommissionRate) {
        this.defaultProductCommissionRate = defaultProductCommissionRate;
    }

    public Long getInviterExpirationDays() {
        return inviterExpirationDays;
    }

    public void setInviterExpirationDays(Long inviterExpirationDays) {
        this.inviterExpirationDays = inviterExpirationDays;
    }

    public Long getSafeDays() {
        return safeDays;
    }

    public void setSafeDays(Long safeDays) {
        this.safeDays = safeDays;
    }
}
