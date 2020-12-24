package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtTenant)实体类
 *
 * @author makejava
 * @since 2020-12-24 13:13:19
 */
public class NxtTenant implements Serializable {
    private static final long serialVersionUID = -87341733081593067L;
    
    private Long id;
    
    private String domains;
    
    private String tenantName;
    
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}