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

    @Resource
    private HttpServletRequest request;

    public Long findTenantId(){
        Long tenantId = mapDomainToTenantId.getOrDefault(request.getServerName(),null);
        return tenantId;
    }

    public Map<String, Long> getMapDomainToTenantId() {
        return mapDomainToTenantId;
    }

    public void setMapDomainToTenantId(Map<String, Long> mapDomainToTenantId) {
        this.mapDomainToTenantId = mapDomainToTenantId;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

}
