package com.newxton.supercompanyapi.controller.api.admin;

import com.newxton.supercompanyapi.entity.NxtUser;
import com.newxton.supercompanyapi.service.NxtUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/23
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminUserListController {

    @Resource
    private NxtUserService nxtUserService;

    @RequestMapping(value = "/api/admin/user_list", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "page_number", required = false) Integer pageNumber
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (pageNumber == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        int limit = 20;
        int offset = 0;

        if (pageNumber > 1){
            offset = limit * (pageNumber-1);
        }

        List<NxtUser> list = nxtUserService.queryAllByLimit(offset,limit);

        List<Map<String,Object>> listResult = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {

            NxtUser user = list.get(i);

            Map<String, Object> item = new HashMap<>();

            item.put("id",user.getId());
            item.put("username",user.getUsername());
            item.put("status",user.getStatus());
            item.put("type",user.getType());

            if (user.getType().equals(1)){
                item.put("type_description","超级管理员");
            }
            if (user.getType().equals(2)){
                item.put("type_description","小编");
            }
            if (user.getType().equals(0)){
                item.put("type_description","只读用户");
            }

            if (user.getStatus().equals(-1)){
                item.put("status_description","已被拉黑");
            }
            if (user.getStatus().equals(0)){
                item.put("status_description","正常");
            }

            listResult.add(item);
        }

        result.put("list",listResult);

        return result;

    }


}
