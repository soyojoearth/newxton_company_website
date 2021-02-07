package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtTenant)实体类
 *
 * @author makejava
 * @since 2021-02-07 15:39:23
 */
public class NxtTenant implements Serializable {
    private static final long serialVersionUID = 772631586365327909L;
    
    private Long id;
    
    private String domains;
    
    private String tenantName;
    
    private String templetePc;
    
    private String templeteMobile;
    
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomains() {
        return domains;
    }

    public void setDomains(String domains) {
        this.domains = domains;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTempletePc() {
        return templetePc;
    }

    public void setTempletePc(String templetePc) {
        this.templetePc = templetePc;
    }

    public String getTempleteMobile() {
        return templeteMobile;
    }

    public void setTempleteMobile(String templeteMobile) {
        this.templeteMobile = templeteMobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}