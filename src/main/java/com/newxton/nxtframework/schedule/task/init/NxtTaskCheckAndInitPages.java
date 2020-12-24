package com.newxton.nxtframework.schedule.task.init;

import com.newxton.nxtframework.entity.NxtWebPage;
import com.newxton.nxtframework.service.NxtWebPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/5
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtTaskCheckAndInitPages {

    private Logger logger = LoggerFactory.getLogger(NxtTaskCheckAndInitPages.class);

    @Resource
    private NxtWebPageService nxtWebPageService;

    /**
     * 检查&初始化页面数据
     * (web_key字段unique，集群情况下不用担心多实例同时insert)
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void exec(){

        //*************************任务代码**开始************************************************

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

        //*************************任务代码**结束************************************************

    }

}
