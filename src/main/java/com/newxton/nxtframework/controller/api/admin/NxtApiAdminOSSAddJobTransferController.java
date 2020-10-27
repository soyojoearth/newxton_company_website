package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.service.NxtCronjobService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/27
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminOSSAddJobTransferController {
    @Resource
    private NxtCronjobService nxtCronjobService;

    @Transactional
    @RequestMapping(value = "/api/admin/oss_addjob_transfer", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "location_from", required = false) String locationFrom,
                                     @RequestParam(value = "location_to", required = false) String locationTo) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (locationFrom == null || locationTo == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        //提交任务
        Map<String, Object> data = new HashMap<>();
        data.put("statusDescription", "当前没有进行任务");

        Integer statusMoveLocalImageToQiniu = 0;
        Integer statusMoveQiniuImageToLocal = 0;

        if (locationFrom.equals("local") && locationTo.equals("qiniu")) {
            statusMoveLocalImageToQiniu = 1;
            statusMoveQiniuImageToLocal = 0;
            data.put("statusDescription", "正在把图片从服务器搬到七牛云");
        }
        if (locationFrom.equals("qiniu") && locationTo.equals("local")) {
            statusMoveLocalImageToQiniu = 0;
            statusMoveQiniuImageToLocal = 1;
            data.put("statusDescription", "正在把图片从七牛云搬到服务器");
        }

        result.put("data", data);

        NxtCronjob nxtCronjobMoveLocalImageToQiniu = nxtCronjobService.queryByKeyForUpdate("moveLocalImageToQiniu");
        if (nxtCronjobMoveLocalImageToQiniu == null) {
            nxtCronjobMoveLocalImageToQiniu = new NxtCronjob();
            nxtCronjobMoveLocalImageToQiniu.setJobKey("moveLocalImageToQiniu");
            nxtCronjobMoveLocalImageToQiniu.setJobStatus(statusMoveLocalImageToQiniu);
            nxtCronjobMoveLocalImageToQiniu.setJobName("将本地图片移动到七牛云");
            nxtCronjobMoveLocalImageToQiniu.setJobStatusDateline(System.currentTimeMillis());
            nxtCronjobService.insert(nxtCronjobMoveLocalImageToQiniu);
        } else {
            nxtCronjobMoveLocalImageToQiniu.setJobStatus(statusMoveLocalImageToQiniu);
            nxtCronjobMoveLocalImageToQiniu.setJobStatusDateline(System.currentTimeMillis());
            nxtCronjobService.update(nxtCronjobMoveLocalImageToQiniu);
        }

        NxtCronjob nxtCronjobMoveQiniuImageToLocal = nxtCronjobService.queryByKeyForUpdate("moveQiniuImageToLocal");
        if (nxtCronjobMoveQiniuImageToLocal == null) {
            nxtCronjobMoveQiniuImageToLocal = new NxtCronjob();
            nxtCronjobMoveQiniuImageToLocal.setJobKey("moveQiniuImageToLocal");
            nxtCronjobMoveQiniuImageToLocal.setJobStatus(statusMoveQiniuImageToLocal);
            nxtCronjobMoveQiniuImageToLocal.setJobName("将七牛云图片移动到本地");
            nxtCronjobMoveQiniuImageToLocal.setJobStatusDateline(System.currentTimeMillis());
            nxtCronjobService.insert(nxtCronjobMoveQiniuImageToLocal);
        } else {
            nxtCronjobMoveQiniuImageToLocal.setJobStatus(statusMoveQiniuImageToLocal);
            nxtCronjobMoveQiniuImageToLocal.setJobStatusDateline(System.currentTimeMillis());
            nxtCronjobService.update(nxtCronjobMoveQiniuImageToLocal);
        }

        return result;

    }
}