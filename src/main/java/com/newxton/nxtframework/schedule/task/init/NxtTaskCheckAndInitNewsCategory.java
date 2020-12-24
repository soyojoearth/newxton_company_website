package com.newxton.nxtframework.schedule.task.init;

import com.newxton.nxtframework.entity.NxtNewsCategory;
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
    private NxtNewsCategoryService nxtNewsCategoryService;

    /**
     * 检查&初始化资讯类别
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void exec(){


        //*************************任务代码**开始************************************************

        List<NxtNewsCategory> nxtNewsCategories = nxtNewsCategoryService.queryAllByLimit(0,1);
        if (nxtNewsCategories.size() > 0){
            logger.info("初始化[资讯分类]任务，已经存在数据，放弃执行");
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

        logger.info("初始化[资讯分类]任务，成功执行完毕");

    }

}
