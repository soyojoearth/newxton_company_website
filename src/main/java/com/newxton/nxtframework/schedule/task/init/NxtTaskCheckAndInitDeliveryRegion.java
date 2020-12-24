package com.newxton.nxtframework.schedule.task.init;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newxton.nxtframework.entity.NxtDeliveryRegion;
import com.newxton.nxtframework.exception.NxtException;
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

    /**
     * 检查&初始化地区列表
     * 感谢作者数据收集：https://github.com/airyland/china-area-data
     * https://raw.githubusercontent.com/airyland/china-area-data/master/data.json
     */
    public void exec() throws Exception {

        //*************************任务代码**开始************************************************

        List<NxtDeliveryRegion> nxtDeliveryRegionList = nxtDeliveryRegionService.queryAllByLimit(0,1);
        if (nxtDeliveryRegionList.size() > 0){
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

            findRegionListAndInsert(mapRegion,"86",nxtDeliveryRegion.getId(),0);
        }
        catch (Exception e){
            throw new NxtException("china_region.json解析JSON出错");
        }

        //*************************任务代码**结束************************************************

        logger.info("初始化[地区数据]任务，成功执行完毕");

    }

    /**
     * 遍历导入地区
     * @param mapRegion
     * @param regionCode
     * @param parentId
     */
    private void findRegionListAndInsert(Map<String,Map<String,String>> mapRegion, String regionCode,Long parentId,Integer level){
        if (level >= 2){//最多导入二级，到城市
            return;
        }
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
            findRegionListAndInsert(mapRegion,key,nxtDeliveryRegion.getId(),level+1);
        }
    }


}
