package com.newxton.nxtframework.schedule;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.nxtframework.component.NxtSaaSCoreComponent;
import com.newxton.nxtframework.entity.NxtTenant;
import com.newxton.nxtframework.service.NxtTenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 *
 */
@Component
public class NxtCronJobSaaSCore {

    private Logger logger = LoggerFactory.getLogger(NxtCronJobSaaSCore.class);

    @Resource
    private NxtTenantService nxtTenantService;

    @Resource
    private NxtSaaSCoreComponent nxtSaaSCoreComponent;

    /**
     * 5分钟更新一次全部商户域名数据
     */
    @Scheduled(fixedDelay = 300000)
    public void syncTenantList() {
        List<NxtTenant> nxtTenantList = nxtTenantService.queryAllByLimit(0,Integer.MAX_VALUE);
        Map<String,Long> mapDomainToTenantId = new HashMap<>();
        for (NxtTenant nxtTenant : nxtTenantList) {
            try {
                List<String> domainList = new Gson().fromJson(nxtTenant.getDomains(),new TypeToken<List<String>>(){}.getType());
                for (String domain : domainList) {
                    mapDomainToTenantId.put(domain, nxtTenant.getId());
                }
            }
            catch (Exception e){
                logger.info("有Tenant域名数据解析错误");
            }
        }
        nxtSaaSCoreComponent.setMapDomainToTenantId(mapDomainToTenantId);
    }

}
