package com.newxton.nxtframework.component;

import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtSetting;
import com.newxton.nxtframework.struct.*;
import com.newxton.nxtframework.service.NxtCronjobService;
import com.newxton.nxtframework.service.NxtSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/18
 * @address Shenzhen, China
 * @copyright NxtFramework
 *
 * 全局设置，放置在数据库的nxt_setting表中
 */
@Component
public class NxtGlobalSettingComponent {

    private Logger logger = LoggerFactory.getLogger(NxtGlobalSettingComponent.class);

    @Resource
    private NxtSettingService nxtSettingService;

    @Resource
    private NxtCronjobService nxtCronjobService;

    /**
     * 保存 系统基本设置
     * @param nxtStructSettingNormal
     */
    public void saveNxtStructSettingNormal(NxtStructSettingNormal nxtStructSettingNormal){
        Gson gson = new Gson();
        String json = gson.toJson(nxtStructSettingNormal);
        String key = "NxtStructSettingNormal";
        this.saveSettingsValueByKey(key,json);
    }

    /**
     * 获取 系统基本设置
     * @return
     */
    @Cacheable("getNxtStructSettingNormal")
    public NxtStructSettingNormal getNxtStructSettingNormal(){
        Gson gson = new Gson();
        String key = "NxtStructSettingNormal";
        String json = this.getSettingsValueByKey(key);
        if (json == null){
            return new NxtStructSettingNormal();
        }
        try {
            NxtStructSettingNormal nxtStructSettingNormal = gson.fromJson(json,NxtStructSettingNormal.class);
            return nxtStructSettingNormal;
        }
        catch (Exception e){
            return new NxtStructSettingNormal();
        }
    }

    /**
     * 保存 图片存储配置
     * @param nxtStructSettingOssConfig
     */
    public void saveNxtStructSettingOssConfig(NxtStructSettingOssConfig nxtStructSettingOssConfig){
        Gson gson = new Gson();
        String json = gson.toJson(nxtStructSettingOssConfig);
        String key = "NxtStructSettingOssConfig";
        this.saveSettingsValueByKey(key,json);
    }

    /**
     * 获取 图片存储配置
     * @return
     */
    @Cacheable("getNxtStructSettingOssConfig")
    public NxtStructSettingOssConfig getNxtStructSettingOssConfig(){
        Gson gson = new Gson();
        String key = "NxtStructSettingOssConfig";
        String json = this.getSettingsValueByKey(key);
        if (json == null){
            return new NxtStructSettingOssConfig();
        }
        try {
            NxtStructSettingOssConfig nxtStructSettingOssConfig = gson.fromJson(json,NxtStructSettingOssConfig.class);
            return nxtStructSettingOssConfig;
        }
        catch (Exception e){
            return new NxtStructSettingOssConfig();
        }
    }

    /**
     * 保存 支付配置
     * @param nxtStructSettingPayConfig
     */
    public void saveNxtStructSettingPayConfig(NxtStructSettingPayConfig nxtStructSettingPayConfig){
        Gson gson = new Gson();
        String json = gson.toJson(nxtStructSettingPayConfig);
        String key = "NxtStructSettingPayConfig";
        this.saveSettingsValueByKey(key,json);
    }

    /**
     * 获取 支付配置
     * @return
     */
    @Cacheable("getNxtStructSettingPayConfig")
    public NxtStructSettingPayConfig getNxtStructSettingPayConfig(){
        Gson gson = new Gson();
        String key = "NxtStructSettingPayConfig";
        String json = this.getSettingsValueByKey(key);
        if (json == null){
            return new NxtStructSettingPayConfig();
        }
        try {
            NxtStructSettingPayConfig nxtStructSettingPayConfig = gson.fromJson(json,NxtStructSettingPayConfig.class);
            return nxtStructSettingPayConfig;
        }
        catch (Exception e){
            return new NxtStructSettingPayConfig();
        }
    }

    /**
     * 保存 商城配置
     * @param nxtStructSettingEcConfig
     */
    public void saveNxtStructSettingEcConfig(NxtStructSettingEcConfig nxtStructSettingEcConfig){
        Gson gson = new Gson();
        String json = gson.toJson(nxtStructSettingEcConfig);
        String key = "NxtStructSettingEcConfig";
        this.saveSettingsValueByKey(key,json);
    }

    /**
     * 获取 商城配置
     * @return
     */
    @Cacheable("getNxtStructSettingEcConfig")
    public NxtStructSettingEcConfig getNxtStructSettingEcConfig(){
        Gson gson = new Gson();
        String key = "NxtStructSettingEcConfig";
        String json = this.getSettingsValueByKey(key);
        if (json == null){
            return new NxtStructSettingEcConfig();
        }
        try {
            NxtStructSettingEcConfig nxtStructSettingEcConfig = gson.fromJson(json,NxtStructSettingEcConfig.class);
            return nxtStructSettingEcConfig;
        }
        catch (Exception e){
            return new NxtStructSettingEcConfig();
        }
    }

    /**
     * 保存 分销配置
     * @param nxtStructSettingCommission
     */
    public void saveNxtStructSettingCommission(NxtStructSettingCommission nxtStructSettingCommission){
        Gson gson = new Gson();
        String json = gson.toJson(nxtStructSettingCommission);
        String key = "NxtStructSettingCommission";
        this.saveSettingsValueByKey(key,json);
    }

    /**
     * 获取 分销配置
     * @return
     */
    @Cacheable("getNxtStructSettingCommission")
    public NxtStructSettingCommission getNxtStructSettingCommission(){
        Gson gson = new Gson();
        String key = "NxtStructSettingCommission";
        String json = this.getSettingsValueByKey(key);
        if (json == null){
            return new NxtStructSettingCommission();
        }
        try {
            NxtStructSettingCommission nxtStructSettingCommission = gson.fromJson(json,NxtStructSettingCommission.class);
            return nxtStructSettingCommission;
        }
        catch (Exception e){
            return new NxtStructSettingCommission();
        }
    }

    /**
     * 取值
     * @param key
     * @return
     */
    private String getSettingsValueByKey(String key){
        NxtSetting nxtSetting = nxtSettingService.queryBySettingKey(key);
        if (nxtSetting == null){
            return null;
        }
        else {
            return nxtSetting.getSettingValue();
        }
    }


    /**
     * 保存值
     * @param key
     * @param value
     * @return
     */
    private NxtSetting saveSettingsValueByKey(String key, String value){
        NxtSetting nxtSetting = nxtSettingService.queryBySettingKey(key);
        if (nxtSetting == null){
            nxtSetting = new NxtSetting();
            nxtSetting.setSettingKey(key);
            nxtSetting.setSettingValue(value);
            nxtSettingService.insert(nxtSetting);
        }
        else {
            nxtSetting.setSettingValue(value);
            nxtSettingService.update(nxtSetting);
        }
        return nxtSetting;
    }


    @CacheEvict(cacheNames = {
            "getNxtStructSettingCommission",
            "getNxtStructSettingNormal",
            "getNxtStructSettingOssConfig",
            "getNxtStructSettingPayConfig",
            "getNxtStructSettingEcConfig"
    },allEntries = true,beforeInvocation = false)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void cleanCache() {
        // 注解 @CacheEvict 就执行了清理，代码块里面什么也不用写
    }

}
