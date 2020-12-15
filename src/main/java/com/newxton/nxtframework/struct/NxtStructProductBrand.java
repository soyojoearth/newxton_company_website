package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructProductBrand {

    public Long id;
    public String brandName;
    public Long uploadFileId;
    public String picUrlPath;
    public String picUrlPathWithDomain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPicUrlPath() {
        return picUrlPath;
    }

    public void setPicUrlPath(String picUrlPath) {
        this.picUrlPath = picUrlPath;
    }

    public Long getUploadFileId() {
        return uploadFileId;
    }

    public void setUploadFileId(Long uploadFileId) {
        this.uploadFileId = uploadFileId;
    }

    public String getPicUrlPathWithDomain() {
        return picUrlPathWithDomain;
    }

    public void setPicUrlPathWithDomain(String picUrlPathWithDomain) {
        this.picUrlPathWithDomain = picUrlPathWithDomain;
    }

}
