package com.newxton.nxtframework.schedule.task.init;

import com.newxton.nxtframework.entity.NxtUserLevel;
import com.newxton.nxtframework.service.NxtUserLevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/10
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtTaskCheckAndInitUserLevel {

    private Logger logger = LoggerFactory.getLogger(NxtTaskCheckAndInitUserLevel.class);

    @Resource
    private NxtUserLevelService nxtUserLevelService;

    /**
     * 检查&初始化用户等级数据
     * (web_key字段unique，集群情况下不用担心多实例同时insert)
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void exec(){

        //*************************任务代码**开始************************************************

        NxtUserLevel nxtUserLevel1 = nxtUserLevelService.queryByNum(1);
        if (nxtUserLevel1 == null){
            NxtUserLevel nxtUserLevel = new NxtUserLevel();
            nxtUserLevel.setNum(1);
            nxtUserLevel.setName("普通会员");
            nxtUserLevel.setCost(0L);
            nxtUserLevel.setDiscount(100L);
            nxtUserLevelService.insert(nxtUserLevel);
        }
        NxtUserLevel nxtUserLevel2 = nxtUserLevelService.queryByNum(2);
        if (nxtUserLevel2 == null){
            NxtUserLevel nxtUserLevel = new NxtUserLevel();
            nxtUserLevel.setNum(2);
            nxtUserLevel.setName("青铜会员");
            nxtUserLevel.setCost(100000L);
            nxtUserLevel.setDiscount(98L);
            nxtUserLevelService.insert(nxtUserLevel);
        }
        NxtUserLevel nxtUserLevel3 = nxtUserLevelService.queryByNum(3);
        if (nxtUserLevel3 == null){
            NxtUserLevel nxtUserLevel = new NxtUserLevel();
            nxtUserLevel.setNum(3);
            nxtUserLevel.setName("白银会员");
            nxtUserLevel.setCost(200000L);
            nxtUserLevel.setDiscount(96L);
            nxtUserLevelService.insert(nxtUserLevel);
        }
        NxtUserLevel nxtUserLevel4 = nxtUserLevelService.queryByNum(4);
        if (nxtUserLevel4 == null){
            NxtUserLevel nxtUserLevel = new NxtUserLevel();
            nxtUserLevel.setNum(4);
            nxtUserLevel.setName("黄金会员");
            nxtUserLevel.setCost(500000L);
            nxtUserLevel.setDiscount(94L);
            nxtUserLevelService.insert(nxtUserLevel);
        }
        NxtUserLevel nxtUserLevel5 = nxtUserLevelService.queryByNum(5);
        if (nxtUserLevel5 == null){
            NxtUserLevel nxtUserLevel = new NxtUserLevel();
            nxtUserLevel.setNum(5);
            nxtUserLevel.setName("白金会员");
            nxtUserLevel.setCost(1000000L);
            nxtUserLevel.setDiscount(92L);
            nxtUserLevelService.insert(nxtUserLevel);
        }
        NxtUserLevel nxtUserLevel6 = nxtUserLevelService.queryByNum(6);
        if (nxtUserLevel6 == null){
            NxtUserLevel nxtUserLevel = new NxtUserLevel();
            nxtUserLevel.setNum(6);
            nxtUserLevel.setName("钻石会员");
            nxtUserLevel.setCost(2000000L);
            nxtUserLevel.setDiscount(90L);
            nxtUserLevelService.insert(nxtUserLevel);
        }
        NxtUserLevel nxtUserLevel7 = nxtUserLevelService.queryByNum(7);
        if (nxtUserLevel7 == null){
            NxtUserLevel nxtUserLevel = new NxtUserLevel();
            nxtUserLevel.setNum(7);
            nxtUserLevel.setName("合作伙伴");
            nxtUserLevel.setCost(5000000L);
            nxtUserLevel.setDiscount(90L);
            nxtUserLevelService.insert(nxtUserLevel);
        }

        //*************************任务代码**结束************************************************

        logger.info("初始化[用户等级]任务，成功执行完毕");

    }

}
