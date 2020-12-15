package com.newxton.nxtframework.schedule.task.init;

import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.entity.NxtNewsCategory;
import com.newxton.nxtframework.service.NxtCronjobService;
import com.newxton.nxtframework.service.NxtNewsCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/5
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtTaskCheckAndInitNewsCategory {

    private Logger logger = LoggerFactory.getLogger(NxtTaskCheckAndInitNewsCategory.class);

    @Resource
    private NxtCronjobService nxtCronjobService;

    @Resource
    private NxtNewsCategoryService nxtNewsCategoryService;

    /**
     * 检查&初始化资讯类别
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void exec(){

        NxtCronjob nxtCronjob = nxtCronjobService.queryByKey("checkAndInitNewsCategory");

        if (nxtCronjob == null){
            //任务没有执行过
            NxtCronjob nxtCronjobNew = new NxtCronjob();
            nxtCronjobNew.setJobName("checkAndInitNewsCategory");
            nxtCronjobNew.setJobKey("checkAndInitNewsCategory");
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
                logger.info("初始化[资讯分类]数据任务已经执行过，跳过执行");
                return;
            }
            else {
                //再次执行任务
                logger.info("初始化[资讯分类]数据任务，需再次执行，开始执行");
            }
        }

        /**
         * 防止集群多个实例并发执行，只需要一个实例在执行即可
         */
        nxtCronjob = nxtCronjobService.queryByKeyForUpdate("checkAndInitNewsCategory");

        if (!nxtCronjob.getJobStatus().equals(1)) {
            logger.info("初始化[资讯分类]任务，任务未开启，放弃执行");
            return;//0:off(任务未开启) 1:on(任务等待执行)
        }

        //*************************任务代码**开始************************************************

        List<NxtNewsCategory> nxtNewsCategories = nxtNewsCategoryService.queryAllByLimit(0,1);
        if (nxtNewsCategories.size() > 0){
            logger.info("初始化[资讯分类]任务，已经存在数据，放弃执行");
            nxtCronjob.setJobStatus(0);
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
            nxtCronjobService.update(nxtCronjob);
            return;
        }
        List<String> categoryList = new ArrayList<>();
        categoryList.add("行业资讯");
        categoryList.add("政策解读");
        categoryList.add("新闻公告");
        for (String categoryName : categoryList) {
            NxtNewsCategory nxtNewsCategory = new NxtNewsCategory();
            nxtNewsCategory.setCategoryName(categoryName);
            nxtNewsCategory.setCategoryPid(0L);
            nxtNewsCategoryService.insert(nxtNewsCategory);
        }

        //*************************任务代码**结束************************************************

        //任务执行完毕
        nxtCronjob.setJobStatus(0);//0:off(任务未开启) 1:on(任务等待执行)
        nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        nxtCronjobService.update(nxtCronjob);

        logger.info("初始化[资讯分类]任务，成功执行完毕");

    }

}
