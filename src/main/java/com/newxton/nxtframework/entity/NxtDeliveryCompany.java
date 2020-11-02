package com.newxton.nxtframework.entity;

import java.io.Serializable;

/**
 * (NxtDeliveryCompany)实体类
 *
 * @author makejava
 * @since 2020-11-02 19:03:30
 */
public class NxtDeliveryCompany implements Serializable {
    private static final long serialVersionUID = 814538890894141657L;
    /**
    * 快递管理
    */
    private Long id;
    
    private String name;
    
    private String code100;
    /**
    * 0:关闭 1:开启
    */
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode100() {
        return code100;
    }

    public void setCode100(String code100) {
        this.code100 = code100;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}