package com.newxton.companywebsite.controller.api;

import com.newxton.companywebsite.entity.NxtUser;
import com.newxton.companywebsite.service.NxtUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/23
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminBlockUserController {


    @Resource
    private NxtUserService nxtUserService;

    @RequestMapping(value = "/api/admin/block_user", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "block_user_id", required = false) Long blockUserId,
                                     @RequestParam(value = "is_block", required = false) Long isBlock
                                     ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (blockUserId == null || isBlock == null){
            result.put("status",52);
            result.put("message","参数错误");
            return result;
        }

        NxtUser user = nxtUserService.queryById(blockUserId);

        if (user == null){
            result.put("status",44);
            result.put("message","用户不存在");
            return result;
        }

        String newToken = getRandomString(32);
        newToken = DigestUtils.md5Hex(newToken).toLowerCase();

        if (isBlock.equals(1L)) {
            //拉黑，并且踢下线
            user.setStatus(-1);
            user.setToken(newToken);
        }
        if (isBlock.equals(0L))  {
            //解除拉黑
            user.setStatus(0);
        }

        //更新
        nxtUserService.update(user);

        return result;

    }

    /**
     * 获取随机字符串
     * @param length
     * @return
     */
    public String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_";
        Random random=new Random();
        StringBuffer buffet=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(str.length()-1);
            buffet.append(str.charAt(number));
        }
        return buffet.toString();
    }

}
