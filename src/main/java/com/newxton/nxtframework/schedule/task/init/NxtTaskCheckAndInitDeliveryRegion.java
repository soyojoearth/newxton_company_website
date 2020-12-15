package com.newxton.nxtframework.schedule.task.init;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.entity.NxtDeliveryRegion;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtCronjobService;
import com.newxton.nxtframework.service.NxtDeliveryRegionService;
import com.qiniu.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/5
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtTaskCheckAndInitDeliveryRegion {

    private Logger logger = LoggerFactory.getLogger(NxtTaskCheckAndInitDeliveryRegion.class);

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    @Resource
    private NxtCronjobService nxtCronjobService;


    /**
     * 检查&初始化地区列表
     * 感谢作者数据收集：https://github.com/airyland/china-area-data
     * https://raw.githubusercontent.com/airyland/china-area-data/master/data.json
     */
    public void exec() throws Exception {

        NxtCronjob nxtCronjob = nxtCronjobService.queryByKey("checkAndInitDeliveryRegion");

        if (nxtCronjob == null){
            //任务没有执行过
            NxtCronjob nxtCronjobNew = new NxtCronjob();
            nxtCronjobNew.setJobName("checkAndInitDeliveryRegion");
            nxtCronjobNew.setJobKey("checkAndInitDeliveryRegion");
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
                logger.info("初始化[地区数据]任务已经执行过，跳过执行");
                return;
            }
            else {
                //再次执行任务
                logger.info("初始化[地区数据]任务，需再次执行，开始执行");
            }
        }

        /**
         * 防止集群多个实例并发执行，只需要一个实例在执行即可
         */
        nxtCronjob = nxtCronjobService.queryByKeyForUpdate("checkAndInitDeliveryRegion");

        if (!nxtCronjob.getJobStatus().equals(1)) {
            logger.info("初始化[地区数据]任务，任务未开启，放弃执行");
            return;//0:off(任务未开启) 1:on(任务等待执行)
        }

        //*************************任务代码**开始************************************************

        List<NxtDeliveryRegion> nxtDeliveryRegionList = nxtDeliveryRegionService.queryAllByLimit(0,1);
        if (nxtDeliveryRegionList.size() > 0){
            nxtCronjob.setJobStatus(0);//0:off(任务未开启) 1:on(任务等待执行)
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
            nxtCronjobService.update(nxtCronjob);
            logger.info("初始化[地区数据]任务，已经存在数据，放弃执行");
            return;
        }

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/init/china_region.json");
        byte[] fileBytesAll = IOUtils.toByteArray(inputStream);
        String json = new String(fileBytesAll, StandardCharsets.UTF_8);

        Gson gson = new Gson();
        try {
            Map<String, Map<String,String>> mapRegion = gson.fromJson(json,new TypeToken<Map<String,Map<String,String>>>(){}.getType());

            NxtDeliveryRegion nxtDeliveryRegion = new NxtDeliveryRegion();
            nxtDeliveryRegion.setRegionPid(0L);
            nxtDeliveryRegion.setRegionName("中国");
            nxtDeliveryRegionService.insert(nxtDeliveryRegion);
            nxtDeliveryRegion.setSortId(nxtDeliveryRegion.getId());
            nxtDeliveryRegionService.update(nxtDeliveryRegion);

            findRegionListAndInsert(mapRegion,"86",nxtDeliveryRegion.getId());
        }
        catch (Exception e){
            throw new NxtException("china_region.json解析JSON出错");
        }

        //*************************任务代码**结束************************************************

        //任务执行完毕
        nxtCronjob.setJobStatus(0);//0:off(任务未开启) 1:on(任务等待执行)
        nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
        nxtCronjobService.update(nxtCronjob);

        logger.info("初始化[地区数据]任务，成功执行完毕");

    }

    /**
     * 遍历导入地区
     * @param mapRegion
     * @param regionCode
     * @param parentId
     */
    private void findRegionListAndInsert(Map<String,Map<String,String>> mapRegion, String regionCode,Long parentId){
        if (!mapRegion.containsKey(regionCode)){
            return;
        }
        Map<String,String> mapChildren = mapRegion.get(regionCode);
        List<String> keyList = new ArrayList<>();
        keyList.addAll(mapChildren.keySet());
        for (String key : keyList) {
            String regionName = mapChildren.get(key);
            NxtDeliveryRegion nxtDeliveryRegion = new NxtDeliveryRegion();
            nxtDeliveryRegion.setRegionPid(parentId);
            nxtDeliveryRegion.setRegionName(regionName);
            nxtDeliveryRegionService.insert(nxtDeliveryRegion);
            nxtDeliveryRegion.setSortId(nxtDeliveryRegion.getId());
            nxtDeliveryRegionService.update(nxtDeliveryRegion);
            findRegionListAndInsert(mapRegion,key,nxtDeliveryRegion.getId());
        }
    }


}
