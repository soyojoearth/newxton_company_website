package com.newxton.companywebsite.controller.api;

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
public class NxtApiAdminNewsDeleteController {

    @Resource
    private NxtContentService nxtContentService;

    @RequestMapping(value = "/api/admin/news/delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先查询，再删除*/
        NxtContent content = nxtContentService.queryById(id);
        if (content == null || !content.getContentType().equals(0)){
            result.put("status", 49);
            result.put("message", "对应的资讯不存在");
            return result;
        }

        nxtContentService.deleteById(content.getId());

        return result;

    }

}
