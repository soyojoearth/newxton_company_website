package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtAclUserRole;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtAclUserRoleService;
import com.newxton.nxtframework.service.NxtUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
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
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminCreateUserController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtAclUserRoleService nxtAclUserRoleService;

    @CacheEvict(cacheNames = {"getUserRoleGroupActionIdSet","getUserActionIdSet"},allEntries = true,beforeInvocation = false)
    @Transactional
    @RequestMapping(value = "/api/admin/create_user", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "new_user_name", required=false) String newUserName,
                                     @RequestParam(value = "new_user_pwd", required=false) String newUserPwd,
                                     @RequestParam(value = "new_user_role", required=false) Long newUserRole
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (newUserName == null || newUserPwd == null || newUserRole == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        newUserName = newUserName.trim();

        NxtUser userRepeat = nxtUserService.queryByUsername(newUserName);

        if (userRepeat != null) {
            result.put("status", 45);
            result.put("message", "用户名重复");
            return result;
        }

        NxtUser user = new NxtUser();

        //创建salt和密码
        String saltNew = getRandomString(32);
        String pwdSaltNew = newUserPwd + saltNew;
        newUserPwd = DigestUtils.md5Hex(pwdSaltNew).toLowerCase();

        user.setUsername(newUserName);
        user.setSalt(saltNew);
        user.setPassword(newUserPwd);
        user.setStatus(0);

        //增加用户
        NxtUser userCreated = nxtUserService.insert(user);

        if (userCreated.getId() == null){
            result.put("status", 50);
            result.put("message", "系统错误");
            return result;
        }

        NxtAclUserRole nxtAclUserRole = new NxtAclUserRole();
        nxtAclUserRole.setUserId(userCreated.getId());
        nxtAclUserRole.setRoleId(newUserRole);
        nxtAclUserRoleService.insert(nxtAclUserRole);


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


