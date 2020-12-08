package com.newxton.nxtframework.schedule.task.init;

import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.entity.NxtProductCategory;
import com.newxton.nxtframework.service.NxtCronjobService;
import com.newxton.nxtframework.service.NxtProductCategoryService;
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
 */
@Component
public class NxtTaskCheckAndInitProductCategory {

    private Logger logger = LoggerFactory.getLogger(NxtTaskCheckAndInitProductCategory.class);

    @Resource
    private NxtCronjobService nxtCronjobService;

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    /**
     * 检查&初始化产品类别
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void exec(){

        NxtCronjob nxtCronjob = nxtCronjobService.queryByKey("checkAndInitProductCategory");

        if (nxtCronjob == null){
            //任务没有执行过
            NxtCronjob nxtCronjobNew = new NxtCronjob();
            nxtCronjobNew.setJobName("checkAndInitProductCategory");
            nxtCronjobNew.setJobKey("checkAndInitProductCategory");
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
                logger.info("初始化[产品分类]数据任务已经执行过，跳过执行");
                return;
            }
            else {
                //再次执行任务
                logger.info("初始化[产品分类]数据任务，需再次执行，开始执行");
            }
        }


        /**
         * 防止集群多个实例并发执行，只需要一个实例在执行即可
         */
        nxtCronjob = nxtCronjobService.queryByKeyForUpdate("checkAndInitProductCategory");

        if (!nxtCronjob.getJobStatus().equals(1)) {
            logger.info("初始化[产品分类]任务，任务未开启，放弃执行");
            return;//0:off(任务未开启) 1:on(任务等待执行)
        }


        //*************************任务代码**开始************************************************

        List<NxtProductCategory> nxtProductCategoryList = nxtProductCategoryService.queryAllByLimit(0,1);
        if (nxtProductCategoryList.size() > 0){
            nxtCronjob.setJobStatus(0);
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
            nxtCronjobService.update(nxtCronjob);
            logger.info("初始化[产品分类]任务，已经存在数据，放弃执行");
            return;
        }
        List<String> categoryList = new ArrayList<>();
        categoryList.add("产品类别A");
        categoryList.add("产品类别B");
        categoryList.add("产品类别C");
        for (String categoryName : categoryList) {
            NxtProductCategory nxtProductCategory = new NxtProductCategory();
            nxtProductCategory.setCategoryName(categoryName);
            nxtProductCategory.setCategoryPid(0L);
            nxtProductCategoryService.insert(nxtProductCategory);
        }

        //*************************任务代码**结束************************************************

        //任务执行完毕
        nxtCronjob.setJobStatus(0);//0:off(任务未开启) 1:on(任务等待执行)
        nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        nxtCronjobService.update(nxtCronjob);

        logger.info("初始化[产品分类]任务，成功执行完毕");

    }


}
