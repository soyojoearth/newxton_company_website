package com.newxton.nxtframework.schedule;

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
public class ScheduledSystemInit {

    private Logger logger = LoggerFactory.getLogger(ScheduledSystemInit.class);

    @Resource
    private NxtUtilComponent nxtUtilComponent;

    @Resource
    private NxtAclActionService nxtAclActionService;

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtAclUserActionService nxtAclUserActionService;

    @Resource
    private NxtSettingService nxtSettingService;

    @Resource
    private NxtWebPageService nxtWebPageService;

    @Resource
    private NxtBannerService nxtBannerService;

    @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
    public void exec() {

        //检查acl_action
        this.checkAndInitAclAction();

        //检查&创建 admin 账户 & 赋予 所有管理权限
        this.checkAndInitAdminUser();

        //检查&初始化默认系统配置
        this.checkAndInitSystemConfig();

        //检查&初始化默认系统配置
        this.checkAndInitBannerData();

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

        NxtSetting nxtSetting0 = nxtSettingService.queryBySettingKey("beianCode");
        if (nxtSetting0 == null){
            nxtSetting0 = new NxtSetting();
            nxtSetting0.setSettingKey("beianCode");
            nxtSetting0.setSettingName("备案号");
            nxtSetting0.setSettingValue("");
            nxtSetting0.setDisplayType("input");
            nxtSetting0.setDatelineUpdated(System.currentTimeMillis());
            nxtSetting0.setPlaceholder("");
            nxtSettingService.insert(nxtSetting0);
        }

        NxtSetting nxtSetting1 = nxtSettingService.queryBySettingKey("statCode");
        if (nxtSetting1 == null){
            nxtSetting1 = new NxtSetting();
            nxtSetting1.setSettingKey("statCode");
            nxtSetting1.setSettingName("统计代码");
            nxtSetting1.setSettingValue("");
            nxtSetting1.setDisplayType("textarea");
            nxtSetting1.setDatelineUpdated(System.currentTimeMillis());
            nxtSettingService.insert(nxtSetting1);
        }

        NxtSetting nxtSetting2 = nxtSettingService.queryBySettingKey("contactCode");
        if (nxtSetting2 == null){
            nxtSetting2 = new NxtSetting();
            nxtSetting2.setSettingKey("contactCode");
            nxtSetting2.setSettingName("客服代码");
            nxtSetting2.setSettingValue("");
            nxtSetting2.setDisplayType("textarea");
            nxtSetting2.setDatelineUpdated(System.currentTimeMillis());
            nxtSettingService.insert(nxtSetting2);
        }

        NxtSetting nxtSetting3 = nxtSettingService.queryBySettingKey("contactLink");
        if (nxtSetting3 == null){
            nxtSetting3 = new NxtSetting();
            nxtSetting3.setSettingKey("contactLink");
            nxtSetting3.setSettingName("客服链接");
            nxtSetting3.setSettingValue("");
            nxtSetting3.setDisplayType("input");
            nxtSetting3.setDatelineUpdated(System.currentTimeMillis());
            nxtSetting3.setPlaceholder("用户点击网页上的一个按钮，然后会打开一个客服窗口，这里填写该按钮的链接");
            nxtSettingService.insert(nxtSetting3);
        }


        NxtSetting nxtSetting4 = nxtSettingService.queryBySettingKey("ossLocation");
        if (nxtSetting4 == null){
            nxtSetting4 = new NxtSetting();
            nxtSetting4.setSettingKey("ossLocation");
            nxtSetting4.setSettingName("图片存储位置");
            nxtSetting4.setSettingValue("");
            nxtSetting4.setDisplayType("input");
            nxtSetting4.setDatelineUpdated(System.currentTimeMillis());
            nxtSetting4.setPlaceholder("存储在本机，填写：local   存储在七牛云，填写：qiniu （不填或填错，默认local）");
            nxtSettingService.insert(nxtSetting4);
        }

        NxtSetting nxtSetting5 = nxtSettingService.queryBySettingKey("ossQiniuAccessKey");
        if (nxtSetting5 == null){
            nxtSetting5 = new NxtSetting();
            nxtSetting5.setSettingKey("ossQiniuAccessKey");
            nxtSetting5.setSettingName("七牛云AccessKey");
            nxtSetting5.setSettingValue("--");
            nxtSetting5.setDisplayType("input");
            nxtSetting5.setDatelineUpdated(System.currentTimeMillis());
            nxtSettingService.insert(nxtSetting5);
        }

        NxtSetting nxtSetting6 = nxtSettingService.queryBySettingKey("ossQiniuSecretKey");
        if (nxtSetting6 == null){
            nxtSetting6 = new NxtSetting();
            nxtSetting6.setSettingKey("ossQiniuSecretKey");
            nxtSetting6.setSettingName("七牛云SecretKey");
            nxtSetting6.setSettingValue("--");
            nxtSetting6.setDisplayType("input");
            nxtSetting6.setDatelineUpdated(System.currentTimeMillis());
            nxtSettingService.insert(nxtSetting6);
        }

        NxtSetting nxtSetting7 = nxtSettingService.queryBySettingKey("ossQiniuBucket");
        if (nxtSetting7 == null){
            nxtSetting7 = new NxtSetting();
            nxtSetting7.setSettingKey("ossQiniuBucket");
            nxtSetting7.setSettingName("七牛云bucket");
            nxtSetting7.setSettingValue("--");
            nxtSetting7.setDisplayType("input");
            nxtSetting7.setDatelineUpdated(System.currentTimeMillis());
            nxtSettingService.insert(nxtSetting7);
        }

        NxtSetting nxtSetting8 = nxtSettingService.queryBySettingKey("ossQiniuDomain");
        if (nxtSetting8 == null){
            nxtSetting8 = new NxtSetting();
            nxtSetting8.setSettingKey("ossQiniuDomain");
            nxtSetting8.setSettingName("七牛云OSS域名");
            nxtSetting8.setSettingValue("--");
            nxtSetting8.setDisplayType("input");
            nxtSetting8.setDatelineUpdated(System.currentTimeMillis());
            nxtSettingService.insert(nxtSetting8);
        }

        NxtWebPage nxtWebPage1 = nxtWebPageService.queryByKey("about_us");
        if (nxtWebPage1 == null){
            nxtWebPage1 = new NxtWebPage();
            nxtWebPage1.setDatelineUpdate(System.currentTimeMillis());
            nxtWebPage1.setWebKey("about_us");
            nxtWebPage1.setWebTitle("关于我们");
            nxtWebPage1.setContentTitle("关于我们");
            nxtWebPage1.setContentDetail("");
            nxtWebPageService.insert(nxtWebPage1);
        }

        NxtWebPage nxtWebPage2 = nxtWebPageService.queryByKey("join_us");
        if (nxtWebPage2 == null){
            nxtWebPage2 = new NxtWebPage();
            nxtWebPage2.setDatelineUpdate(System.currentTimeMillis());
            nxtWebPage2.setWebKey("join_us");
            nxtWebPage2.setWebTitle("加入我们");
            nxtWebPage2.setContentTitle("加入我们");
            nxtWebPage2.setContentDetail("");
            nxtWebPageService.insert(nxtWebPage2);
        }

    }

    private void checkAndInitBannerData(){

       List<NxtBanner> nxtBannerList = nxtBannerService.queryAll(new NxtBanner());

       if (nxtBannerList.size() == 0){

           NxtBanner nxtBanner = new NxtBanner();
           nxtBanner.setLocationName("首页");
           nxtBanner.setClickUrl("#");
           nxtBanner.setUploadfileId(1L);

           nxtBannerService.insert(nxtBanner);

       }

    }


}
