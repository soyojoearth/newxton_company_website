package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/27
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructUserTransaction {
    private Long id;
    private String datelineReadable;
    private Float amount;
    private String typeText;
    private String eventText;

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

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public String getEventText() {
        return eventText;
    }

    public void setEventText(String eventText) {
        this.eventText = eventText;
    }

}
