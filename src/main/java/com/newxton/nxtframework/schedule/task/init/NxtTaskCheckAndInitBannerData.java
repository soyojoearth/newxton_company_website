package com.newxton.nxtframework.schedule.task.init;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtBanner;
import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.service.NxtBannerService;
import com.newxton.nxtframework.service.NxtCronjobService;
import com.qiniu.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/5
 * @address Shenzhen, China
 */
@Component
public class NxtTaskCheckAndInitBannerData {

    private Logger logger = LoggerFactory.getLogger(NxtTaskCheckAndInitBannerData.class);

    @Resource
    private NxtBannerService nxtBannerService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @Resource
    private NxtCronjobService nxtCronjobService;

    /**
     * 检查&初始化默认Banner图
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void exec(){

        NxtCronjob nxtCronjob = nxtCronjobService.queryByKey("checkAndInitBannerData");

        if (nxtCronjob == null){
            //任务没有执行过
            NxtCronjob nxtCronjobNew = new NxtCronjob();
            nxtCronjobNew.setJobName("checkAndInitBannerData");
            nxtCronjobNew.setJobKey("checkAndInitBannerData");
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
                logger.info("初始化[滚动横幅]数据任务已经执行过，跳过执行");
                return;
            }
            else {
                //再次执行任务
                logger.info("初始化[滚动横幅]数据任务，需再次执行，开始执行");
            }
        }

        /**
         * 防止集群多个实例并发执行，只需要一个实例在执行即可
         */
        nxtCronjob = nxtCronjobService.queryByKeyForUpdate("checkAndInitBannerData");

        if (!nxtCronjob.getJobStatus().equals(1)) {
            logger.info("初始化[滚动横幅]任务，任务未开启，放弃执行");
            return;//0:off(任务未开启) 1:on(任务等待执行)
        }

        //*************************任务代码**开始************************************************

        List<NxtBanner> nxtBannerList = nxtBannerService.queryAll(new NxtBanner());

        if (nxtBannerList.size() > 0){
            nxtCronjob.setJobStatus(0);
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
            nxtCronjobService.update(nxtCronjob);
            logger.info("初始化[滚动横幅]任务，已经存在数据，放弃执行");
            return;
        }

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/init/images/banner1.png");
        try {
            byte[] fileBytesAll = IOUtils.toByteArray(inputStream);
            Map<String,Object> result = nxtUploadImageComponent.saveUploadImage(fileBytesAll,"png");
            if (result.containsKey("id")){
                NxtBanner nxtBanner = new NxtBanner();
                nxtBanner.setLocationName("首页");
                nxtBanner.setClickUrl("#");
                nxtBanner.setUploadfileId((long)result.get("id"));
                nxtBannerService.insert(nxtBanner);
            }
        }
        catch (IOException e){
            logger.info("Resource读取初始化banner图，错误");
        }

        //*************************任务代码**结束************************************************

        //任务执行完毕
        nxtCronjob.setJobStatus(0);
        nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        nxtCronjobService.update(nxtCronjob);

        logger.info("初始化[滚动横幅]任务，成功执行完毕");

    }

}
