package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtDeliveryRegion;
import com.newxton.nxtframework.service.NxtDeliveryRegionService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/12
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminDeliveryRegionOrderSwapController {

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/api/admin/delivery_region/order_swap", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id1", required=false) Long id1,
                                     @RequestParam(value = "id2", required=false) Long id2
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id1 == null || id2 == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先查询*/
        NxtDeliveryRegion contentA = nxtDeliveryRegionService.queryById(id1);
        if (contentA == null){
            result.put("status", 49);
            result.put("message", "对应的1不存在");
            return result;
        }
        NxtDeliveryRegion contentB = nxtDeliveryRegionService.queryById(id2);
        if (contentB == null){
            result.put("status", 49);
            result.put("message", "对应的2不存在");
            return result;
        }

        long sortA = contentA.getSortId();
        long sortB = contentB.getSortId();

        /*交换*/
        contentA.setSortId(sortB);
        contentB.setSortId(sortA);

        nxtDeliveryRegionService.update(contentA);
        nxtDeliveryRegionService.update(contentB);

        return result;

    }

}
