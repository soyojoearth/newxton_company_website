package com.newxton.nxtframework.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtDeliveryConfig;
import com.newxton.nxtframework.entity.NxtDeliveryConfigItem;
import com.newxton.nxtframework.entity.NxtDeliveryConfigItemRegion;
import com.newxton.nxtframework.entity.NxtDeliveryRegion;
import com.newxton.nxtframework.service.NxtDeliveryConfigItemRegionService;
import com.newxton.nxtframework.service.NxtDeliveryConfigItemService;
import com.newxton.nxtframework.service.NxtDeliveryRegionService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

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
@Component
public class NxtModelDeliveryConfig {

    @Resource
    private NxtDeliveryConfigItemService nxtDeliveryConfigItemService;

    @Resource
    private NxtDeliveryConfigItemRegionService nxtDeliveryConfigItemRegionService;

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    /**
     * 保存运费模版
     * @param jsonParam
     * @return
     */
    public Map<String, Object> save(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");
        String name = jsonParam.getString("name");
        Integer type = jsonParam.getInteger("type");

        JSONArray itemList = jsonParam.getJSONArray("itemList");

        JSONArray regionList = jsonParam.getJSONArray("regionList");


        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        return result;

    }

    /**
     * 获取运费模版详情
     * @param nxtDeliveryConfig
     * @return
     */
    public Map<String, Object> getDeliveryConfigAllDetail(NxtDeliveryConfig nxtDeliveryConfig){

        Map<String,Object> detail = new HashMap<>();
        detail.put("id",nxtDeliveryConfig.getId());
        detail.put("name",nxtDeliveryConfig.getName());
        detail.put("type",nxtDeliveryConfig.getType() == null ? 0 : nxtDeliveryConfig.getType());
        detail.put("itemList",getDeliveryConfigItemListAllDetail(nxtDeliveryConfig));

        return detail;

    }

    /**
     * 获取运费模版中的各条目详情
     * @param nxtDeliveryConfig
     * @return
     */
    private List<Map<String,Object>> getDeliveryConfigItemListAllDetail(NxtDeliveryConfig nxtDeliveryConfig){

        List<Map<String,Object>> resultList = new ArrayList<>();

        NxtDeliveryConfigItem nxtDeliveryConfigItemCondition = new NxtDeliveryConfigItem();
        nxtDeliveryConfigItemCondition.setDeliveryConfigId(nxtDeliveryConfig.getId());
        List<NxtDeliveryConfigItem> nxtDeliveryConfigItemList = nxtDeliveryConfigItemService.queryAll(nxtDeliveryConfigItemCondition);

        Map<Long,List<Map<String,Object>>> itemRegionListMap = new HashMap<>();

        List<Long> configItemIdList = new ArrayList<>();

        for (NxtDeliveryConfigItem configItem :
                nxtDeliveryConfigItemList) {

            configItemIdList.add(configItem.getId());

            ArrayList itemRegionList = new ArrayList();
            itemRegionListMap.put(configItem.getId(),itemRegionList);

            Map<String,Object> configItemMap = new HashMap<>();
            configItemMap.put("id",configItem.getId());
            configItemMap.put("billableQuantity",configItem.getBillableQuantity());
            configItemMap.put("billablePrice",configItem.getBillablePrice());
            configItemMap.put("additionQuantity",configItem.getAdditionQuantity());
            configItemMap.put("additionPrice",configItem.getAdditionPrice());

            configItemMap.put("regionList",itemRegionList);

            resultList.add(configItemMap);
        }

        if (configItemIdList.size() > 0) {

            List<NxtDeliveryConfigItemRegion> nxtDeliveryConfigItemRegionList = nxtDeliveryConfigItemRegionService.selectByConfigItemIdSet(configItemIdList);

            List<Long> regionIdList = new ArrayList<>();
            for (NxtDeliveryConfigItemRegion configItemRegion :
                    nxtDeliveryConfigItemRegionList) {
                regionIdList.add(configItemRegion.getDeliveryRegionId());
            }
            Map<Long,String> regionIdToRegionNameMap = new HashMap<>();
            if (regionIdList.size() > 0){
                List<NxtDeliveryRegion> nxtDeliveryRegionList = nxtDeliveryRegionService.selectByIdSet(0,Integer.MAX_VALUE,regionIdList);
                for (NxtDeliveryRegion region :
                        nxtDeliveryRegionList) {
                    regionIdToRegionNameMap.put(region.getId(), region.getRegionName());
                }
            }

            for (NxtDeliveryConfigItemRegion configItemRegion :
                    nxtDeliveryConfigItemRegionList) {
                Map<String,Object> mapItemRegion = new HashMap<>();

                mapItemRegion.put("regionId",configItemRegion.getDeliveryRegionId());
                mapItemRegion.put("regionName",regionIdToRegionNameMap.get(configItemRegion.getDeliveryRegionId()));

                List<Map<String,Object>> itemRegionList = itemRegionListMap.get(configItemRegion.getDeliveryConfigItemId());
                itemRegionList.add(mapItemRegion);

            }

        }

        return resultList;

    }

}
