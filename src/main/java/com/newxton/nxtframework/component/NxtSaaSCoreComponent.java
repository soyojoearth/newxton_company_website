package com.newxton.nxtframework.component;

import com.newxton.nxtframework.schedule.NxtCronJobSaaSCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtSaaSCoreComponent {

    private Logger logger = LoggerFactory.getLogger(NxtCronJobSaaSCore.class);

    private Map<String,Long> mapDomainToTenantId = new HashMap<>();

    private Map<String,String> mapDomainToTempletePc = new HashMap<>();

    private Map<String,String> mapDomainToTempleteMobile = new HashMap<>();

    @Resource
    private HttpServletRequest request;

    /**
     * 获取当前租户id
     * @return
     */
    public Long findTenantId(){
        Long tenantId = mapDomainToTenantId.getOrDefault(request.getServerName(),null);
        return tenantId;
    }

    /**
     * 获取当前租户templete pc端模版名称
     * @return
     */
    public String findTenantTempletePc(){
        String templetePc = mapDomainToTempletePc.getOrDefault(request.getServerName(),"default");
        return templetePc;
    }

    /**
     * 获取当前租户templete 移动端模版名称
     * @return
     */
    public String findTenantTempleteMobile(){
        String templeteMobile = mapDomainToTempleteMobile.getOrDefault(request.getServerName(),"default");
        return templeteMobile;
    }

    public Map<String, Long> getMapDomainToTenantId() {
        return mapDomainToTenantId;
    }

    public void setMapDomainToTenantId(Map<String, Long> mapDomainToTenantId) {
        this.mapDomainToTenantId = mapDomainToTenantId;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Map<String, String> getMapDomainToTempletePc() {
        return mapDomainToTempletePc;
    }

    public void setMapDomainToTempletePc(Map<String, String> mapDomainToTempletePc) {
        this.mapDomainToTempletePc = mapDomainToTempletePc;
    }

    public Map<String, String> getMapDomainToTempleteMobile() {
        return mapDomainToTempleteMobile;
    }

    public void setMapDomainToTempleteMobile(Map<String, String> mapDomainToTempleteMobile) {
        this.mapDomainToTempleteMobile = mapDomainToTempleteMobile;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

}
