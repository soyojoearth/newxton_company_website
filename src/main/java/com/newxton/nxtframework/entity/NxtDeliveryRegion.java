package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtDeliveryRegion)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:03:31
 */
public class NxtDeliveryRegion implements Serializable {
    private static final long serialVersionUID = 959929946888187422L;
    /**
    * 【物流配送地区】
    */
    private Long id;
    /**
    * 名称
    */
    private String regionName;
    /**
    * 上级地区id
    */
    private Long regionPid;
    /**
    * 排序，大的在前面
    */
    private Long sortId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Long getRegionPid() {
        return regionPid;
    }

    public void setRegionPid(Long regionPid) {
        this.regionPid = regionPid;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

}