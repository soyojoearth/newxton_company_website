package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.service.NxtProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductSetTrashController {

    @Resource
    private NxtProductService nxtProductService;

    @RequestMapping(value = "/api/admin/product/set_trash", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");
        Boolean isTrash = jsonParam.getBoolean("isTrash");

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先查询*/
        NxtProduct content = nxtProductService.queryById(id);
        if (content == null){
            result.put("status", 49);
            result.put("message", "对应的产品不存在");
            return result;
        }

        content.setIsTrash(isTrash ? 1 : 0);//1放入回收站 0恢复

        nxtProductService.update(content);

        return result;

    }

}
