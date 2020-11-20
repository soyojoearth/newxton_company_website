package com.newxton.nxtframework.model.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/20
 * @address Shenzhen, China
 */
public class NxtStructSettingCommission {

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
}
