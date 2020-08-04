package com.newxton.companywebsite.controller.api.admin;

import com.newxton.companywebsite.entity.NxtContent;
import com.newxton.companywebsite.service.NxtContentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminNewsOrderSwapController {

    @Resource
    private NxtContentService nxtContentService;

    @RequestMapping(value = "/api/admin/news/order_swap", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "news_id_a", required=false) Long newsIdA,
                                     @RequestParam(value = "news_id_b", required=false) Long newsIdB
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (newsIdA == null || newsIdB == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先查询*/
        NxtContent contentA = nxtContentService.queryById(newsIdA);
        if (contentA == null){
            result.put("status", 49);
            result.put("message", "对应的A不存在");
            return result;
        }
        NxtContent contentB = nxtContentService.queryById(newsIdB);
        if (contentB == null){
            result.put("status", 49);
            result.put("message", "对应的B不存在");
            return result;
        }

        long sortA = contentA.getSortId();
        long sortB = contentB.getSortId();

        /*交换*/
        contentA.setSortId(sortB);
        contentB.setSortId(sortA);

        nxtContentService.update(contentA);
        nxtContentService.update(contentB);

        return result;

    }

}
