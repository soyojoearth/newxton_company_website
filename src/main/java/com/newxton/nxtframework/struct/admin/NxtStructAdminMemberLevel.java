package com.newxton.nxtframework.struct.admin;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructAdminMemberLevel {

    private Integer levelNum;

    private String levelName;
    /**
     * 所需消费额度
     */
    private Float levelCost;
    /**
     * 折扣，百分比（放大100倍）
     */
    private Long levelDiscount;

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Float getLevelCost() {
        return levelCost;
    }

    public void setLevelCost(Float levelCost) {
        this.levelCost = levelCost;
    }

    public Long getLevelDiscount() {
        return levelDiscount;
    }

    public void setLevelDiscount(Long levelDiscount) {
        this.levelDiscount = levelDiscount;
    }

}
