package com.newxton.nxtframework.schedule;

import com.newxton.nxtframework.component.NxtGlobalSettingComponent;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/8
 * @address Shenzhen, China
 * Cronjob 系统初始化，检查默认配置
 */
@Component
public class CronInitConfig {

    private Logger logger = LoggerFactory.getLogger(CronInitConfig.class);

    @Resource
    private NxtUtilComponent nxtUtilComponent;

    @Resource
    private NxtAclActionService nxtAclActionService;

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtAclUserActionService nxtAclUserActionService;

    @Resource
    private NxtWebPageService nxtWebPageService;

    @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
    public void exec() {
        //检查&初始化acl_action
        this.checkAndInitAclAction();
        //检查&创建 admin 账户 & 赋予 所有管理权限
        this.checkAndInitAdminUser();
        //检查&初始化默认系统配置
        this.checkAndInitSystemConfig();
    }

    /**
     * 检查NxtAclAction表里面的class是否齐全，不全就自动添加
     */
    private void checkAndInitAclAction(){
        List<NxtAclAction> aclActionList = nxtAclActionService.queryAll(new NxtAclAction());
        Set<String> actionClassSet = new HashSet<>();
        for (NxtAclAction nxtAclAction :
                aclActionList) {
            actionClassSet.add(nxtAclAction.getActionClass());
        }
        String basePackages = "com.newxton.nxtframework.controller.api.admin.";
        Reflections reflections = new Reflections(basePackages);
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(RestController.class);

        List<Class<?>> classList = new LinkedList<Class<?>>(classSet);

        for (Class<?> item :
                classList) {
            String className = item.getName().replace(basePackages,"");
            String actionName = className.replace("NxtApiAdmin","").replace("Controller","");
            if (className.contains("Login") || className.contains("Logout")){
                continue;
            }
            if (!actionClassSet.contains(className)){
                //插入
                NxtAclAction nxtAclAction = new NxtAclAction();
                nxtAclAction.setActionClass(className);
                nxtAclAction.setActionName(actionName);
                nxtAclActionService.insert(nxtAclAction);
            }
        }
    }

    /**
     * 检查&创建默认admin账户
     */
    private void checkAndInitAdminUser(){
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

    }

    /**
     * 检查&初始化默认系统配置
     */
    private void checkAndInitSystemConfig(){

        NxtWebPage nxtWebPageAboutUs = nxtWebPageService.queryByKey("about_us");
        if (nxtWebPageAboutUs == null){
            nxtWebPageAboutUs = new NxtWebPage();
            nxtWebPageAboutUs.setDatelineUpdate(System.currentTimeMillis());
            nxtWebPageAboutUs.setWebKey("about_us");
            nxtWebPageAboutUs.setWebTitle("关于我们");
            nxtWebPageAboutUs.setContentTitle("关于我们");
            nxtWebPageAboutUs.setContentDetail("");
            nxtWebPageService.insert(nxtWebPageAboutUs);
        }

        NxtWebPage nxtWebPageJoinUs = nxtWebPageService.queryByKey("join_us");
        if (nxtWebPageJoinUs == null){
            nxtWebPageJoinUs = new NxtWebPage();
            nxtWebPageJoinUs.setDatelineUpdate(System.currentTimeMillis());
            nxtWebPageJoinUs.setWebKey("join_us");
            nxtWebPageJoinUs.setWebTitle("加入我们");
            nxtWebPageJoinUs.setContentTitle("加入我们");
            nxtWebPageJoinUs.setContentDetail("");
            nxtWebPageService.insert(nxtWebPageJoinUs);
        }

        NxtWebPage nxtWebPagePrivacyPolicy = nxtWebPageService.queryByKey("privacy_policy");
        if (nxtWebPagePrivacyPolicy == null){
            nxtWebPagePrivacyPolicy = new NxtWebPage();
            nxtWebPagePrivacyPolicy.setDatelineUpdate(System.currentTimeMillis());
            nxtWebPagePrivacyPolicy.setWebKey("privacy_policy");
            nxtWebPagePrivacyPolicy.setWebTitle("隐私政策");
            nxtWebPagePrivacyPolicy.setContentTitle("隐私政策");
            nxtWebPagePrivacyPolicy.setContentDetail("");
            nxtWebPageService.insert(nxtWebPagePrivacyPolicy);
        }

        NxtWebPage nxtWebPageTermsState = nxtWebPageService.queryByKey("terms_state");
        if (nxtWebPageTermsState == null){
            nxtWebPageTermsState = new NxtWebPage();
            nxtWebPageTermsState.setDatelineUpdate(System.currentTimeMillis());
            nxtWebPageTermsState.setWebKey("terms_state");
            nxtWebPageTermsState.setWebTitle("条款声明");
            nxtWebPageTermsState.setContentTitle("条款声明");
            nxtWebPageTermsState.setContentDetail("");
            nxtWebPageService.insert(nxtWebPageTermsState);
        }

        NxtWebPage nxtWebPageBuyGuide = nxtWebPageService.queryByKey("buy_guide");
        if (nxtWebPageBuyGuide == null){
            nxtWebPageBuyGuide = new NxtWebPage();
            nxtWebPageBuyGuide.setDatelineUpdate(System.currentTimeMillis());
            nxtWebPageBuyGuide.setWebKey("buy_guide");
            nxtWebPageBuyGuide.setWebTitle("购买指南");
            nxtWebPageBuyGuide.setContentTitle("购买指南");
            nxtWebPageBuyGuide.setContentDetail("");
            nxtWebPageService.insert(nxtWebPageBuyGuide);
        }

    }

}
