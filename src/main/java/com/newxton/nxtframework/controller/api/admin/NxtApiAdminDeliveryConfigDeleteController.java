package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtDeliveryConfig;
import com.newxton.nxtframework.entity.NxtDeliveryConfigItem;
import com.newxton.nxtframework.service.NxtDeliveryConfigItemRegionService;
import com.newxton.nxtframework.service.NxtDeliveryConfigItemService;
import com.newxton.nxtframework.service.NxtDeliveryConfigService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/4
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminDeliveryConfigDeleteController {

    @Resource
    private NxtDeliveryConfigService nxtDeliveryConfigService;

    @Resource
    private NxtDeliveryConfigItemService nxtDeliveryConfigItemService;

    @Resource
    private NxtDeliveryConfigItemRegionService nxtDeliveryConfigItemRegionService;

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/api/admin/delivery_config/delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先查询*/
        NxtDeliveryConfig nxtDeliveryConfig = nxtDeliveryConfigService.queryById(id);
        if (nxtDeliveryConfig == null){
            result.put("status", 49);
            result.put("message", "找不到对应的内容");
            return result;
        }

        //检查运费模版是否已被引用，被引用的不可删除
        // TODO

        //item列表
        NxtDeliveryConfigItem nxtDeliveryConfigItemCondition = new NxtDeliveryConfigItem();
        nxtDeliveryConfigItemCondition.setDeliveryConfigId(nxtDeliveryConfig.getId());
        List<Long> configItemIdList = new ArrayList<>();
        List<NxtDeliveryConfigItem> nxtDeliveryConfigItems =  nxtDeliveryConfigItemService.queryAll(nxtDeliveryConfigItemCondition);
        for (NxtDeliveryConfigItem item :
                nxtDeliveryConfigItems) {
            configItemIdList.add(item.getId());

        }
        if (configItemIdList.size() > 0){
            nxtDeliveryConfigItemRegionService.deleteByConfigItemIdSet(configItemIdList);
            nxtDeliveryConfigItemService.deleteByIdSet(configItemIdList);
        }

        nxtDeliveryConfigService.deleteById(id);

        return result;

    }

}
