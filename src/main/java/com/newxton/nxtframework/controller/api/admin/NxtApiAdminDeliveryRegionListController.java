package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtDeliveryRegion;
import com.newxton.nxtframework.service.NxtDeliveryRegionService;
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
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminDeliveryRegionListController {

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    @RequestMapping(value = "/api/admin/delivery_region/list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtDeliveryRegion> list = nxtDeliveryRegionService.queryAll(new NxtDeliveryRegion());

        List<Map<String,Object>> listResult = new ArrayList<>();

        List<Map<String,Object>> listSimpleResult = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            NxtDeliveryRegion region = list.get(i);

            if (region.getRegionPid().equals(0L)){
                Map<String, Object> item = new HashMap<>();
                item.put("region",region);
                //搜寻下级
                List<Map<String,Object>> subRegionList = findSubRegion(region,list);
                item.put("sub_region_list",subRegionList);
                listResult.add(item);

                //前端简化显示listSimpleResult
                Map<String, Object> simpleItem = new HashMap<>();
                simpleItem.put("region_name",region.getRegionName());
                simpleItem.put("region_name_display",region.getRegionName());
                simpleItem.put("region_id",region.getId());
                simpleItem.put("region_pid",region.getRegionPid());

                listSimpleResult.add(simpleItem);

                if (subRegionList.size() > 0){
                    this.addSubRegionListToSimpleList(subRegionList,listSimpleResult,"--");
                }

            }

        }

        result.put("list",listResult);

        result.put("list_simple",listSimpleResult);

        return result;

    }


    /**
     * 遍历搜寻下级类别，最终成树状
     * @param regionParent
     * @param list
     * @return
     */
    private List<Map<String,Object>> findSubRegion(NxtDeliveryRegion regionParent, List<NxtDeliveryRegion> list){

        List<Map<String,Object>> listResult = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            NxtDeliveryRegion region = list.get(i);
            if (region.getRegionPid().equals(regionParent.getId())){

                Map<String, Object> item = new HashMap<>();
                item.put("region",region);
                //搜寻下级
                item.put("sub_region_list",findSubRegion(region,list));
                listResult.add(item);

            }
        }

        return listResult;

    }

    /**
     * 照顾前端开发工程师，增加这样的显示
     * 分类
     * --分类
     * ----分类
     * 分类
     * --分类
     * @param subCategoryList
     * @param listSimpleResult
     * @param preStr
     */
    private void addSubRegionListToSimpleList(List<Map<String,Object>> subCategoryList, List<Map<String,Object>> listSimpleResult,String preStr){
        for (int i = 0; i < subCategoryList.size(); i++) {
            Map<String, Object> item = subCategoryList.get(i);
            NxtDeliveryRegion region = (NxtDeliveryRegion)item.get("region");
            List<Map<String,Object>> sub_region_list = (List<Map<String,Object>>)item.get("sub_region_list");

            Map<String, Object> simpleItem = new HashMap<>();
            simpleItem.put("region_name",region.getRegionName());
            simpleItem.put("region_name_display",preStr+region.getRegionName());
            simpleItem.put("region_id",region.getId());
            simpleItem.put("region_pid",region.getRegionPid());

            listSimpleResult.add(simpleItem);

            if (sub_region_list.size()>0){
                this.addSubRegionListToSimpleList(sub_region_list,listSimpleResult,preStr + "--");
            }
        }
    }

}
