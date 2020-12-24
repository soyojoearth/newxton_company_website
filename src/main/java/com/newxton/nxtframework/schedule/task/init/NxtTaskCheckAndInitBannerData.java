package com.newxton.nxtframework.schedule.task.init;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtBanner;
import com.newxton.nxtframework.service.NxtBannerService;
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
 * @copyright NxtFramework
 */
@Component
public class NxtTaskCheckAndInitBannerData {

    private Logger logger = LoggerFactory.getLogger(NxtTaskCheckAndInitBannerData.class);

    @Resource
    private NxtBannerService nxtBannerService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    /**
     * 检查&初始化默认Banner图
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void exec(){

        //*************************任务代码**开始************************************************

        List<NxtBanner> nxtBannerList = nxtBannerService.queryAll(new NxtBanner());

        if (nxtBannerList.size() > 0){
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

        logger.info("初始化[滚动横幅]任务，成功执行完毕");

    }

}
