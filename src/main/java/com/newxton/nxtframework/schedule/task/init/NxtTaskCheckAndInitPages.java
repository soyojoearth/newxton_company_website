package com.newxton.nxtframework.schedule.task.init;

import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.entity.NxtWebPage;
import com.newxton.nxtframework.service.NxtCronjobService;
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

    @Resource
    private NxtCronjobService nxtCronjobService;

    /**
     * 检查&初始化页面数据
     * (web_key字段unique，集群情况下不用担心多实例同时insert)
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void exec(){

        NxtCronjob nxtCronjob = nxtCronjobService.queryByKey("NxtTaskCheckAndInitPages");

        if (nxtCronjob == null){
            //任务没有执行过
            NxtCronjob nxtCronjobNew = new NxtCronjob();
            nxtCronjobNew.setJobName("NxtTaskCheckAndInitPages");
            nxtCronjobNew.setJobKey("NxtTaskCheckAndInitPages");
            nxtCronjobNew.setJobStatus(1);//0:off(任务未开启) 1:on(任务等待执行)
            try {
                nxtCronjobService.insert(nxtCronjobNew);
            }
            catch (Exception e){
                logger.info("没成功insert，可能其它实际例子已经insert");
            }
        }
        else {
            if (!nxtCronjob.getJobStatus().equals(1)){
                //任务已经执行过
                logger.info("初始化[默认页面]数据任务已经执行过，跳过执行");
                return;
            }
            else {
                //再次执行任务
                logger.info("初始化[默认页面]数据任务，需再次执行，开始执行");
            }
        }

        /**
         * 防止集群多个实例并发执行，只需要一个实例在执行即可
         */
        nxtCronjob = nxtCronjobService.queryByKeyForUpdate("NxtTaskCheckAndInitPages");

        if (!nxtCronjob.getJobStatus().equals(1)) {
            logger.info("初始化[默认页面]任务，任务未开启，放弃执行");
            return;//0:off(任务未开启) 1:on(任务等待执行)
        }

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

        //任务执行完毕
        nxtCronjob.setJobStatus(0);
        nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        nxtCronjobService.update(nxtCronjob);

        logger.info("初始化[默认页面]任务，成功执行完毕");

    }

}
