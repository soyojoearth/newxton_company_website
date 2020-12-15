package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.service.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/4
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminDeliveryRegionDeleteController {

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    @Resource
    private NxtDeliveryConfigItemRegionService nxtDeliveryConfigItemRegionService;

    @RequestMapping(value = "/api/admin/delivery_region/delete", method = RequestMethod.POST)
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
        NxtDeliveryRegion content = nxtDeliveryRegionService.queryById(id);
        if (content == null){
            result.put("status", 49);
            result.put("message", "找不到对应的内容");
            return result;
        }

        //检查冲突
        NxtDeliveryConfigItemRegion nxtDeliveryConfigItemRegion = new NxtDeliveryConfigItemRegion();
        nxtDeliveryConfigItemRegion.setDeliveryRegionId(content.getId());

        Long count = nxtDeliveryConfigItemRegionService.queryCount(nxtDeliveryConfigItemRegion);

        if (count > 0){
            result.put("status", 55);
            result.put("message", "该地区已被运费模版引用，请先取消引用");
            return result;
        }

        nxtDeliveryRegionService.deleteById(content.getId());

        return result;

    }

}
