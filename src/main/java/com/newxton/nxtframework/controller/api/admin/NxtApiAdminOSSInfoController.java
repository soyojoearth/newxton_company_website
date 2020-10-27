package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.entity.NxtUploadfile;
import com.newxton.nxtframework.service.NxtCronjobService;
import com.newxton.nxtframework.service.NxtUploadfileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/27
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminOSSInfoController {

    @Resource
    private NxtCronjobService nxtCronjobService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @RequestMapping(value = "/api/admin/oss_info", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtCronjob> list = nxtCronjobService.queryAll(new NxtCronjob());

        Map<String, Object> data = new HashMap<>();

        data.put("statusDescription", "当前没有进行任务");

        for (NxtCronjob nxtCronjob :
                list) {
            if (nxtCronjob.getJobKey().equals("moveLocalImageToQiniu") && nxtCronjob.getJobStatus() > 0){
                data.put("statusDescription", "正在把图片从服务器搬到七牛云");
            }
            if (nxtCronjob.getJobKey().equals("moveQiniuImageToLocal") && nxtCronjob.getJobStatus() > 0){
                data.put("statusDescription", "正在把图片从七牛云搬到服务器");
            }
        }

        //统计服务器本机图片数量
        NxtUploadfile nxtUploadfileCondition1 = new NxtUploadfile();
        nxtUploadfileCondition1.setFileLocation(3);
        Long countLocal = nxtUploadfileService.queryCount(nxtUploadfileCondition1);

        //统计七牛云OSS图片数量
        NxtUploadfile nxtUploadfileCondition2 = new NxtUploadfile();
        nxtUploadfileCondition2.setFileLocation(1);
        Long totalQiniu = nxtUploadfileService.queryCount(nxtUploadfileCondition2);

        data.put("totalLocal", countLocal);
        data.put("totalQiniu", totalQiniu);

        result.put("data", data);

        return result;

    }
}
