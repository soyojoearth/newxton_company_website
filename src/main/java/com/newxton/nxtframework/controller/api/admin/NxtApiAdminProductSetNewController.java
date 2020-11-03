package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.service.NxtProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductSetNewController {

    @Resource
    private NxtProductService nxtProductService;

    @RequestMapping(value = "/api/admin/product/set_new", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id,
                                     @RequestParam(value = "set_new", required=false) Integer setNew
                                     ) {

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

        if (setNew != null && !setNew.equals(0)) {
            content.setIsNew(1);//新品
        }
        else {
            content.setIsNew(0);//取消新品
        }

        nxtProductService.update(content);

        return result;

    }

}
