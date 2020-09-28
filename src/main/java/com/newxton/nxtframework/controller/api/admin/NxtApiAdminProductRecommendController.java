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
public class NxtApiAdminProductRecommendController {

    @Resource
    private NxtProductService nxtProductService;

    @RequestMapping(value = "/api/admin/product/recommend", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id,
                                     @RequestParam(value = "recommend", required=false) Integer recommend
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
            result.put("message", "对应的资讯不存在");
            return result;
        }

        if (recommend != null && !recommend.equals(0)) {
            content.setIsRecommend(1);//推荐
        }
        else {
            content.setIsRecommend(0);//撤销推荐
        }

        nxtProductService.update(content);

        return result;

    }

}
