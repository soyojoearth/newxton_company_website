package com.newxton.supercompanyapi.controller.api.admin;

import com.newxton.supercompanyapi.entity.NxtUser;
import com.newxton.supercompanyapi.service.NxtUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, Object> index(
            @RequestHeader("user_id") Long adminUserId,
            @RequestParam(value = "block_user_id", required = false) Long blockUserId,
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

        if (adminUserId.equals(user.getId())){
            //不能调整自己
            result.put("status",53);
            result.put("message","不能调整自己");
            return result;
        }

        NxtUser userPrepare = new NxtUser();
        userPrepare.setId(user.getId());

        String newToken = getRandomString(32);
        newToken = DigestUtils.md5Hex(newToken).toLowerCase();

        if (isBlock.equals(1L)) {
            //拉黑，并且踢下线
            userPrepare.setStatus(-1);
            userPrepare.setToken(newToken);
        }
        if (isBlock.equals(0L))  {
            //解除拉黑
            userPrepare.setStatus(0);
        }

        //更新
        nxtUserService.update(userPrepare);

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
