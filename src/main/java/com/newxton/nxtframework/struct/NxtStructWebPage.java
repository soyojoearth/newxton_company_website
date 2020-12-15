package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/20
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructWebPage {

    private Long id;
    private String webTitle;
    private String contentTitle;
    private String contentDetail;
    private Long datelineUpdate;
    private String datelineUpdateReadable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentDetail() {
        return contentDetail;
    }

    public void setContentDetail(String contentDetail) {
        this.contentDetail = contentDetail;
    }

    public Long getDatelineUpdate() {
        return datelineUpdate;
    }

    public void setDatelineUpdate(Long datelineUpdate) {
        this.datelineUpdate = datelineUpdate;
    }

    public String getDatelineUpdateReadable() {
        return datelineUpdateReadable;
    }

    public void setDatelineUpdateReadable(String datelineUpdateReadable) {
        this.datelineUpdateReadable = datelineUpdateReadable;
    }

}
