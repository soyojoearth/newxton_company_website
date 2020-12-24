package com.newxton.nxtframework.schedule.task.init;

import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.NxtAclAction;
import com.newxton.nxtframework.entity.NxtAclUserAction;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtAclActionService;
import com.newxton.nxtframework.service.NxtAclUserActionService;
import com.newxton.nxtframework.service.NxtUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/5
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtTaskCheckAndInitAdminUser {

    private Logger logger = LoggerFactory.getLogger(NxtTaskCheckAndInitAdminUser.class);

    @Resource
    private NxtAclActionService nxtAclActionService;

    @Resource
    private NxtAclUserActionService nxtAclUserActionService;

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUtilComponent nxtUtilComponent;

    /**
     * 检查&创建默认admin账户
     * 这个任务比较特殊，每次启动都要执行一次，所以最后不需要setJobStatus(0)；并且同时单独一个实例执行。
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void exec(){

        logger.info("初始化[admin用户]数据任务，开始执行");

        //*************************任务代码**开始************************************************

        String defaultAdminPwd = "nxtframework.com";
        NxtUser adminUser = nxtUserService.queryByUsername("admin");
        if (adminUser == null){
            String salt = nxtUtilComponent.getRandomString(32);
            String pwdSalt = defaultAdminPwd+salt;
            String userPwd = DigestUtils.md5Hex(pwdSalt).toLowerCase();
            String newToken = nxtUtilComponent.getRandomString(32);
            newToken = DigestUtils.md5Hex(newToken).toLowerCase();
            adminUser = new NxtUser();
            adminUser.setUsername("admin");
            adminUser.setPassword(userPwd);
            adminUser.setToken(newToken);
            adminUser.setSalt(salt);
            adminUser.setStatus(0);
            adminUser.setIsAdmin(1);
            adminUser = nxtUserService.insert(adminUser);
        }
        else {
            if (!adminUser.getStatus().equals(0)){
                adminUser.setStatus(0);
                adminUser = nxtUserService.update(adminUser);
            }
        }

        //检查&赋予admin所有权限
        NxtAclUserAction nxtAclUserActionCondition = new NxtAclUserAction();
        nxtAclUserActionCondition.setUserId(adminUser.getId());
        List<NxtAclUserAction> aclUserActionList = nxtAclUserActionService.queryAll(nxtAclUserActionCondition);
        Set<Long> actionIdSet = new HashSet<>();
        for (NxtAclUserAction nxtAclUserAction :
                aclUserActionList) {
            actionIdSet.add(nxtAclUserAction.getActionId());
        }

        List<NxtAclAction> aclActionList = nxtAclActionService.queryAll(new NxtAclAction());
        for (NxtAclAction nxtAclAction :
                aclActionList) {
            if (!actionIdSet.contains(nxtAclAction.getId())){
                //插入
                NxtAclUserAction nxtAclUserActionNew = new NxtAclUserAction();
                nxtAclUserActionNew.setUserId(adminUser.getId());
                nxtAclUserActionNew.setActionId(nxtAclAction.getId());
                nxtAclUserActionService.insert(nxtAclUserActionNew);
            }
        }

        //*************************任务代码**结束************************************************

        logger.info("初始化[admin用户]任务，成功执行完毕");

    }

}
