package com.newxton.nxtframework.process;

import com.newxton.nxtframework.entity.NxtDeliveryConfig;
import com.newxton.nxtframework.entity.NxtDeliveryConfigItem;
import com.newxton.nxtframework.entity.NxtDeliveryConfigItemRegion;
import com.newxton.nxtframework.entity.NxtDeliveryRegion;
import com.newxton.nxtframework.struct.NxtStructDeliveryCofnigItemRegion;
import com.newxton.nxtframework.struct.NxtStructDeliveryConfig;
import com.newxton.nxtframework.struct.NxtStructDeliveryConfigItem;
import com.newxton.nxtframework.service.NxtDeliveryConfigItemRegionService;
import com.newxton.nxtframework.service.NxtDeliveryConfigItemService;
import com.newxton.nxtframework.service.NxtDeliveryConfigService;
import com.newxton.nxtframework.service.NxtDeliveryRegionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtProcessDeliveryConfig {

    @Resource
    private NxtDeliveryConfigService nxtDeliveryConfigService;

    @Resource
    private NxtDeliveryConfigItemService nxtDeliveryConfigItemService;

    @Resource
    private NxtDeliveryConfigItemRegionService nxtDeliveryConfigItemRegionService;

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    /**
     * 保存运费模版
     * @param nxtStructDeliveryConfig
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public Map<String, Object> save(NxtStructDeliveryConfig nxtStructDeliveryConfig) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtDeliveryConfig nxtDeliveryConfig;

        if (nxtStructDeliveryConfig.getId() == null){
            nxtDeliveryConfig = new NxtDeliveryConfig();
        }
        else {
            nxtDeliveryConfig = nxtDeliveryConfigService.queryById(nxtStructDeliveryConfig.getId());
        }

        if (nxtDeliveryConfig == null){
            result.put("status", 49);
            result.put("message", "找不到对应的内容");
            return result;
        }

        //需要新增的NxtStructDeliveryConfigItem条目列表
        List<NxtStructDeliveryConfigItem> nxtStructDeliveryConfigItemListForInsert = new ArrayList<>();

        //需要更新的NxtStructDeliveryConfigItem条目列表
        List<NxtStructDeliveryConfigItem> nxtStructDeliveryConfigItemListForUpdate = new ArrayList<>();

        //需要删除的NxtStructDeliveryConfigItem条目列表id
        List<Long> configItemIdListForDelete = new ArrayList<>();

        if (nxtStructDeliveryConfig.getId() == null){

            nxtDeliveryConfig.setName(nxtStructDeliveryConfig.getName());

            nxtDeliveryConfigService.insert(nxtDeliveryConfig);

            //插入全部config item
            nxtStructDeliveryConfigItemListForInsert = nxtStructDeliveryConfig.getItemList();

            for (NxtStructDeliveryConfigItem nxtStructDeliveryConfigItem:
            nxtStructDeliveryConfigItemListForInsert) {
                nxtStructDeliveryConfigItem.setDeliveryConfigId(nxtDeliveryConfig.getId());
            }

        }
        else {
            nxtDeliveryConfig.setId(nxtStructDeliveryConfig.getId());
            nxtDeliveryConfig.setName(nxtStructDeliveryConfig.getName());

            nxtDeliveryConfigService.update(nxtDeliveryConfig);

            NxtDeliveryConfigItem nxtDeliveryConfigItemCondition = new NxtDeliveryConfigItem();
            nxtDeliveryConfigItemCondition.setDeliveryConfigId(nxtDeliveryConfig.getId());

            List<NxtDeliveryConfigItem> nxtDeliveryConfigItemList = nxtDeliveryConfigItemService.queryAll(nxtDeliveryConfigItemCondition);

            //旧的config item id集合
            Set<Long> configItemIdSet = new HashSet<>();
            for (NxtDeliveryConfigItem nxtDeliveryConfigItem:
                    nxtDeliveryConfigItemList) {
                configItemIdSet.add(nxtDeliveryConfigItem.getId());
            }

            //比对config item
            List<NxtStructDeliveryConfigItem> nxtStructDeliveryConfigItemList = nxtStructDeliveryConfig.getItemList();
            for (NxtStructDeliveryConfigItem nxtStructDeliveryConfigItem:
                    nxtStructDeliveryConfigItemList) {
                nxtStructDeliveryConfigItem.setDeliveryConfigId(nxtDeliveryConfig.getId());
                if (nxtStructDeliveryConfigItem.getId() == null){
                    //需要新增
                    nxtStructDeliveryConfigItemListForInsert.add(nxtStructDeliveryConfigItem);
                }
                else {
                    configItemIdSet.remove(nxtStructDeliveryConfigItem.getId());
                    //需要更新
                    nxtStructDeliveryConfigItemListForUpdate.add(nxtStructDeliveryConfigItem);
                }
            }

            //需要删除
            configItemIdListForDelete = new ArrayList<>(configItemIdSet);

        }

        //执行删除
        deleteStructDeliveryConfigItemList(configItemIdListForDelete);

        //执行新增
        insertStructDeliveryConfigItemList(nxtStructDeliveryConfigItemListForInsert);

        //执行更新
        updateStructDeliveryConfigItemList(nxtStructDeliveryConfigItemListForUpdate);

        result.put("detail",getDeliveryConfigAllDetail(nxtDeliveryConfig));

        return result;

    }

    /**
     * 执行 需要新增的ConfigItemList
     * @param nxtStructDeliveryConfigItemInsertList
     */
    private void insertStructDeliveryConfigItemList(List<NxtStructDeliveryConfigItem> nxtStructDeliveryConfigItemInsertList){

        //需要新增的NxtStructDeliveryCofnigItemRegion条目列表
        List<NxtStructDeliveryCofnigItemRegion> nxtStructDeliveryConfigItemRegionListForInsert = new ArrayList<>();

        for (NxtStructDeliveryConfigItem nxtStructDeliveryConfigItem:
             nxtStructDeliveryConfigItemInsertList) {

            NxtDeliveryConfigItem nxtDeliveryConfigItem = new NxtDeliveryConfigItem();
            nxtDeliveryConfigItem.setId(nxtStructDeliveryConfigItem.getId());
            nxtDeliveryConfigItem.setDeliveryConfigId(nxtStructDeliveryConfigItem.getDeliveryConfigId());
            nxtDeliveryConfigItem.setBillableQuantity(nxtStructDeliveryConfigItem.getBillableQuantity());
            nxtDeliveryConfigItem.setBillablePrice((long)(nxtStructDeliveryConfigItem.getBillablePrice()*100));
            nxtDeliveryConfigItem.setAdditionQuantity(nxtStructDeliveryConfigItem.getAdditionQuantity());
            nxtDeliveryConfigItem.setAdditionPrice((long)(nxtStructDeliveryConfigItem.getAdditionPrice()*100));
            nxtDeliveryConfigItemService.insert(nxtDeliveryConfigItem);

            List<NxtStructDeliveryCofnigItemRegion> nxtStructDeliveryCofnigItemRegionList = nxtStructDeliveryConfigItem.getRegionList();

            for (NxtStructDeliveryCofnigItemRegion nxtStructDeliveryCofnigItemRegion:
                    nxtStructDeliveryCofnigItemRegionList) {
                nxtStructDeliveryCofnigItemRegion.setDeliveryConfigItemId(nxtDeliveryConfigItem.getId());
                nxtStructDeliveryConfigItemRegionListForInsert.add(nxtStructDeliveryCofnigItemRegion);
            }

        }

        //执行新增
        insertStructDeliveryConfigItemRegionList(nxtStructDeliveryConfigItemRegionListForInsert);

    }

    /**
     * 执行 需要更新的ConfigItemList
     * @param nxtStructDeliveryConfigItemList
     */
    private void updateStructDeliveryConfigItemList(List<NxtStructDeliveryConfigItem> nxtStructDeliveryConfigItemList){

        //需要新增的NxtStructDeliveryCofnigItemRegion条目列表
        List<NxtStructDeliveryCofnigItemRegion> nxtStructDeliveryConfigItemRegionListForInsert = new ArrayList<>();
        //需要更新的NxtStructDeliveryCofnigItemRegion条目列表
        List<NxtStructDeliveryCofnigItemRegion> nxtStructDeliveryConfigItemRegionListForUpdate = new ArrayList<>();
        //需要删除的NxtStructDeliveryCofnigItemRegion条目列表id
        List<Long> configRegionIdListForDelete = new ArrayList<>();

        List<Long> configItemIdList = new ArrayList<>();

        for (NxtStructDeliveryConfigItem nxtStructDeliveryConfigItem:
                nxtStructDeliveryConfigItemList) {
            NxtDeliveryConfigItem nxtDeliveryConfigItem = new NxtDeliveryConfigItem();
            nxtDeliveryConfigItem.setId(nxtStructDeliveryConfigItem.getId());
            nxtDeliveryConfigItem.setBillableQuantity(nxtStructDeliveryConfigItem.getBillableQuantity());
            nxtDeliveryConfigItem.setBillablePrice((long)(nxtStructDeliveryConfigItem.getBillablePrice()*100));
            nxtDeliveryConfigItem.setAdditionQuantity(nxtStructDeliveryConfigItem.getAdditionQuantity());
            nxtDeliveryConfigItem.setAdditionPrice((long)(nxtStructDeliveryConfigItem.getAdditionPrice()*100));
            nxtDeliveryConfigItemService.update(nxtDeliveryConfigItem);

            nxtStructDeliveryConfigItem.setId(nxtDeliveryConfigItem.getId());

            configItemIdList.add(nxtDeliveryConfigItem.getId());
        }

        //数据库中原先的ItemRegionList
        List<NxtDeliveryConfigItemRegion> nxtStructDeliveryConfigItemRegionList = nxtDeliveryConfigItemRegionService.selectByConfigItemIdSet(configItemIdList);

        Set<Long> configItemRegionIdSet = new HashSet<>();
        for (NxtDeliveryConfigItemRegion nxtDeliveryConfigItemRegion:
                nxtStructDeliveryConfigItemRegionList) {
            configItemRegionIdSet.add(nxtDeliveryConfigItemRegion.getId());
        }

        for (NxtStructDeliveryConfigItem nxtStructDeliveryConfigItem:
                nxtStructDeliveryConfigItemList) {
            List<NxtStructDeliveryCofnigItemRegion> configItemRegionList = nxtStructDeliveryConfigItem.getRegionList();
            for (NxtStructDeliveryCofnigItemRegion configItemRegion : configItemRegionList) {

                configItemRegion.setDeliveryConfigItemId(nxtStructDeliveryConfigItem.getId());

                if (configItemRegion.getId() == null){
                    //需要新增
                    nxtStructDeliveryConfigItemRegionListForInsert.add(configItemRegion);
                }
                else {
                    configItemRegionIdSet.remove(configItemRegion.getId());
                    //需要更新
                    nxtStructDeliveryConfigItemRegionListForUpdate.add(configItemRegion);
                }
            }
        }

        //需要删除的itemRegion
        configRegionIdListForDelete = new ArrayList<>(configItemRegionIdSet);

        //执行删除
        deleteStructDeliveryConfigItemRegionList(configRegionIdListForDelete);
        //执行新增
        insertStructDeliveryConfigItemRegionList(nxtStructDeliveryConfigItemRegionListForInsert);
        //执行更新
        updateStructDeliveryConfigItemRegionList(nxtStructDeliveryConfigItemRegionListForUpdate);

    }

    /**
     * 执行 需要删除的 ConfigItemList
     * @param configItemIdList
     */
    private void deleteStructDeliveryConfigItemList(List<Long> configItemIdList){
        if (configItemIdList.size() > 0){
            nxtDeliveryConfigItemService.deleteByIdSet(configItemIdList);
            nxtDeliveryConfigItemRegionService.deleteByConfigItemIdSet(configItemIdList);
        }
    }

    /**
     * 执行 ConfigItemRegion 需要更新的List
     * @param nxtStructDeliveryConfigItemList
     */
    private void updateStructDeliveryConfigItemRegionList(List<NxtStructDeliveryCofnigItemRegion> nxtStructDeliveryConfigItemList){
        for (NxtStructDeliveryCofnigItemRegion item :
                nxtStructDeliveryConfigItemList) {
            NxtDeliveryConfigItemRegion nxtDeliveryConfigItemRegion = new NxtDeliveryConfigItemRegion();
            nxtDeliveryConfigItemRegion.setId(item.getId());
            nxtDeliveryConfigItemRegion.setDeliveryConfigItemId(item.getDeliveryConfigItemId());
            nxtDeliveryConfigItemRegion.setDeliveryRegionId(item.getRegionId());
            if (nxtDeliveryConfigItemRegion.getId() != null) {
                nxtDeliveryConfigItemRegionService.update(nxtDeliveryConfigItemRegion);
            }
        }
    }

    /**
     * 执行 ConfigItemRegion 需要新增的List
     * @param nxtStructDeliveryConfigItemRegionList
     */
    private void insertStructDeliveryConfigItemRegionList(List<NxtStructDeliveryCofnigItemRegion> nxtStructDeliveryConfigItemRegionList){
        for (NxtStructDeliveryCofnigItemRegion item :
                nxtStructDeliveryConfigItemRegionList) {
            NxtDeliveryConfigItemRegion nxtDeliveryConfigItemRegion = new NxtDeliveryConfigItemRegion();
            nxtDeliveryConfigItemRegion.setDeliveryConfigItemId(item.getDeliveryConfigItemId());
            nxtDeliveryConfigItemRegion.setDeliveryRegionId(item.getRegionId());
            nxtDeliveryConfigItemRegionService.insert(nxtDeliveryConfigItemRegion);
        }
    }

    /**
     * 执行 ConfigItemRegion 需要删除的List
     * @param configItemRegionIdList
     */
    private void deleteStructDeliveryConfigItemRegionList(List<Long> configItemRegionIdList){
        if (configItemRegionIdList.size() > 0){
            nxtDeliveryConfigItemRegionService.deleteByIdSet(configItemRegionIdList);
        }
    }

    /**
     * 获取运费模版Struct详情
     * @param nxtDeliveryConfig
     * @return
     */
    public NxtStructDeliveryConfig getDeliveryConfigAllDetail(NxtDeliveryConfig nxtDeliveryConfig){

        NxtStructDeliveryConfig nxtStructDeliveryConfig = new NxtStructDeliveryConfig();
        nxtStructDeliveryConfig.setId(nxtDeliveryConfig.getId());
        nxtStructDeliveryConfig.setName(nxtDeliveryConfig.getName());
        nxtStructDeliveryConfig.setItemList(getDeliveryConfigItemListAllDetail(nxtDeliveryConfig));

        return nxtStructDeliveryConfig;

    }

    /**
     * 取得运费模版条目Struct详情
     * @param nxtDeliveryConfig
     * @return
     */
    public List<NxtStructDeliveryConfigItem> getDeliveryConfigItemListAllDetail(NxtDeliveryConfig nxtDeliveryConfig){

        List<NxtStructDeliveryConfigItem> nxtStructDeliveryConfigItemList = new ArrayList<>();

        NxtDeliveryConfigItem nxtDeliveryConfigItemCondition = new NxtDeliveryConfigItem();
        nxtDeliveryConfigItemCondition.setDeliveryConfigId(nxtDeliveryConfig.getId());
        List<NxtDeliveryConfigItem> nxtDeliveryConfigItemList = nxtDeliveryConfigItemService.queryAll(nxtDeliveryConfigItemCondition);

        Map<Long,List<NxtStructDeliveryCofnigItemRegion>> itemRegionListMap = new HashMap<>();
        List<Long> configItemIdList = new ArrayList<>();

        for (NxtDeliveryConfigItem configItem :
                nxtDeliveryConfigItemList) {

            NxtStructDeliveryConfigItem nxtStructDeliveryConfigItem = new NxtStructDeliveryConfigItem();

            nxtStructDeliveryConfigItemList.add(nxtStructDeliveryConfigItem);

            nxtStructDeliveryConfigItem.setId(configItem.getId());
            nxtStructDeliveryConfigItem.setDeliveryConfigId(configItem.getDeliveryConfigId());
            nxtStructDeliveryConfigItem.setBillableQuantity(configItem.getBillableQuantity());
            nxtStructDeliveryConfigItem.setBillablePrice(configItem.getBillablePrice()/100F);
            nxtStructDeliveryConfigItem.setAdditionQuantity(configItem.getAdditionQuantity());
            nxtStructDeliveryConfigItem.setAdditionPrice(configItem.getAdditionPrice()/100F);

            List<NxtStructDeliveryCofnigItemRegion> nxtStructDeliveryCofnigItemRegionList = new ArrayList<>();

            nxtStructDeliveryConfigItem.setRegionList(nxtStructDeliveryCofnigItemRegionList);

            configItemIdList.add(configItem.getId());

            itemRegionListMap.put(configItem.getId(),nxtStructDeliveryCofnigItemRegionList);

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

                NxtStructDeliveryCofnigItemRegion nxtStructDeliveryCofnigItemRegion = new NxtStructDeliveryCofnigItemRegion();
                nxtStructDeliveryCofnigItemRegion.setId(configItemRegion.getId());
                nxtStructDeliveryCofnigItemRegion.setDeliveryConfigItemId(configItemRegion.getDeliveryConfigItemId());
                nxtStructDeliveryCofnigItemRegion.setRegionId(configItemRegion.getDeliveryRegionId());
                nxtStructDeliveryCofnigItemRegion.setRegionName(regionIdToRegionNameMap.get(configItemRegion.getDeliveryRegionId()));

                List<NxtStructDeliveryCofnigItemRegion> itemRegionList = itemRegionListMap.get(configItemRegion.getDeliveryConfigItemId());

                itemRegionList.add(nxtStructDeliveryCofnigItemRegion);

            }

        }

        return nxtStructDeliveryConfigItemList;

    }

}
