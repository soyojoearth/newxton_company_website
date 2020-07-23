package com.newxton.companywebsite.controller.api;

import com.newxton.companywebsite.entity.NxtUser;
import com.newxton.companywebsite.service.NxtUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/23
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminResetUserTypeController {
    @Resource
    private NxtUserService nxtUserService;

    @RequestMapping(value = "/api/admin/reset_user_type", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestHeader("user_id") Long adminUserId,
            @RequestParam(value = "reset_user_id", required = false) Long resetUserId,
            @RequestParam(value = "reset_user_type", required = false) Integer resetUserType
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (resetUserId == null || resetUserType == null){
            result.put("status",52);
            result.put("message","参数错误");
            return result;
        }

        NxtUser user = nxtUserService.queryById(resetUserId);

        if (user == null){
            result.put("status",44);
            result.put("message","用户不存在");
            return result;
        }

        if (adminUserId.equals(user.getId())){
            //不能调整自己
            result.put("status",53);
            result.put("message","不能调整自己");
            return result;
        }

        if (resetUserType.equals(1)) {
            //设为超级管理员
            user.setType(1);
        }
        if (resetUserType.equals(2))  {
            //设为小编
            user.setType(2);
        }
        if (resetUserType.equals(0))  {
            //设为只读访客
            user.setType(0);
        }

        //更新
        nxtUserService.update(user);

        return result;

    }
}
