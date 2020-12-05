package com.newxton.nxtframework.schedule;

import com.newxton.nxtframework.schedule.task.init.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/8
 * @address Shenzhen, China
 * Cronjob 系统初始化，检查默认配置
 */
@Component
public class NxtCronInitConfigAndData {

    private Logger logger = LoggerFactory.getLogger(NxtCronInitConfigAndData.class);

    @Resource
    private NxtTaskCheckAndInitPages nxtTaskCheckAndInitPages;

    @Resource
    private NxtTaskCheckAndInitAclAction nxtTaskCheckAndInitAclAction;

    @Resource
    private NxtTaskCheckAndInitAdminUser nxtTaskCheckAndInitAdminUser;

    @Resource
    private NxtTaskCheckAndInitBannerData nxtTaskCheckAndInitBannerData;

    @Resource
    private NxtTaskCheckAndInitDeliveryRegion nxtTaskCheckAndInitDeliveryRegion;

    @Resource
    private NxtTaskCheckAndInitNewsCategory nxtTaskCheckAndInitNewsCategory;

    @Resource
    private NxtTaskCheckAndInitProductCategory nxtTaskCheckAndInitProductCategory;

    /**
     * 启动时，仅执行一次
     * @throws Exception
     */
    @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
    public void exec() throws Exception {

        //检查&初始化acl_action
        nxtTaskCheckAndInitAclAction.exec();

        //检查&创建 admin 账户 & 赋予 所有管理权限
        nxtTaskCheckAndInitAdminUser.exec();

        //检查&初始化默认页面数据
        nxtTaskCheckAndInitPages.exec();

        //检查&初始化默认Banner图
        nxtTaskCheckAndInitBannerData.exec();

        //检查&初始化资讯类别
        nxtTaskCheckAndInitNewsCategory.exec();

        //检查&初始化产品类别
        nxtTaskCheckAndInitProductCategory.exec();

        //检查&初始化地区列表
        nxtTaskCheckAndInitDeliveryRegion.exec();


        //检查&初始化示例资讯

        //检查&初始化示例产品

    }

}
