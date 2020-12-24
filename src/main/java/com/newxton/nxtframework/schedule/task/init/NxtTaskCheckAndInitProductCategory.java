package com.newxton.nxtframework.schedule.task.init;

import com.newxton.nxtframework.entity.NxtProductCategory;
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
 * @copyright NxtFramework
 */
@Component
public class NxtTaskCheckAndInitProductCategory {

    private Logger logger = LoggerFactory.getLogger(NxtTaskCheckAndInitProductCategory.class);

    @Resource
    private NxtProductCategoryService nxtProductCategoryService;

    /**
     * 检查&初始化产品类别
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void exec(){

        //*************************任务代码**开始************************************************

        List<NxtProductCategory> nxtProductCategoryList = nxtProductCategoryService.queryAllByLimit(0,1);
        if (nxtProductCategoryList.size() > 0){
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

        logger.info("初始化[产品分类]任务，成功执行完毕");

    }


}
