package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.entity.NxtWithdraw;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.service.NxtWithdrawService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.admin.NxtStructAdminWithdraw;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/10
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminWithdrawListController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtWithdrawService nxtWithdrawService;

    @RequestMapping(value = "/api/admin/withdraw/list", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");
        Integer status = jsonParam.getInteger("status");//状态（0:已申请 1:已批准 2:已拒绝 3:已汇款）
        String username = jsonParam.getString("username");

        if (offset == null || limit == null) {
            return new NxtStructApiResult(53, "缺少参数：offset、limit").toMap();
        }

        Long userId = null;
        if (username != null && !username.trim().isEmpty()) {
            NxtUser nxtUser = nxtUserService.queryByUsername(username);
            if (nxtUser == null) {
                return new NxtStructApiResult(49, "该用户不存在：" + username).toMap();
            } else {
                userId = nxtUser.getId();
            }
        }

        List<NxtWithdraw> nxtWithdrawList = nxtWithdrawService.adminQueryList(offset, limit, userId, status);
        Long count = nxtWithdrawService.adminQueryCount(userId, status);

        List<NxtStructAdminWithdraw> list = this.assemblyStructAdminWithdraw(nxtWithdrawList);

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("list", list);

        return new NxtStructApiResult(result).toMap();
    }

    /**
     * 装配资金列表结构化数据
     * @param nxtWithdrawList
     * @return
     */
    private List<NxtStructAdminWithdraw> assemblyStructAdminWithdraw(List<NxtWithdraw> nxtWithdrawList) {

        //状态（0:已申请 1:已批准 2:已拒绝 3:已汇款）
        Map<Integer,String> mapStatus = new HashMap<>();
        mapStatus.put(0,"已申请");
        mapStatus.put(1,"已批准");
        mapStatus.put(2,"已拒绝");
        mapStatus.put(3,"已汇款");

        //平台（0:银行 1:微信 2:支付宝 3:paypal 888:现金）
        Map<Integer,String> mapPlatform = new HashMap<>();
        mapPlatform.put(0,"银行");
        mapPlatform.put(1,"微信");
        mapPlatform.put(2,"支付宝");
        mapPlatform.put(3,"paypal");
        mapPlatform.put(888,"现金");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Long> userIdList = new ArrayList<>();

        List<NxtStructAdminWithdraw> nxtStructAdminWithdrawList = new ArrayList<>();

        for (NxtWithdraw nxtWithdraw : nxtWithdrawList) {

            userIdList.add(nxtWithdraw.getUserId());

            NxtStructAdminWithdraw nxtStructAdminWithdraw = new NxtStructAdminWithdraw();
            nxtStructAdminWithdrawList.add(nxtStructAdminWithdraw);

            nxtStructAdminWithdraw.setId(nxtWithdraw.getId());
            nxtStructAdminWithdraw.setUserId(nxtWithdraw.getUserId());
            nxtStructAdminWithdraw.setTransactionId(nxtWithdraw.getTransactionId());
            nxtStructAdminWithdraw.setStatus(nxtWithdraw.getStatus());
            nxtStructAdminWithdraw.setStatusText(mapStatus.getOrDefault(nxtWithdraw.getStatus(),null));

            nxtStructAdminWithdraw.setAmount(nxtWithdraw.getAmount()/100F);

            nxtStructAdminWithdraw.setDatelineCreate(nxtWithdraw.getDatelineCreate());
            nxtStructAdminWithdraw.setDatelineEnd(nxtWithdraw.getDatelineEnd());
            nxtStructAdminWithdraw.setDatelineCreateReadable(sdf.format(new Date(nxtWithdraw.getDatelineCreate())));
            if (nxtWithdraw.getDatelineEnd() != null){
                nxtStructAdminWithdraw.setDatelineEndReadable(sdf.format(new Date(nxtWithdraw.getDatelineEnd())));
            }
            nxtStructAdminWithdraw.setPlatform(nxtWithdraw.getPlatform());
            nxtStructAdminWithdraw.setPlatformText(mapPlatform.getOrDefault(nxtWithdraw.getPlatform(),null));
            nxtStructAdminWithdraw.setPlatformPerson(nxtWithdraw.getPlatformPerson());
            nxtStructAdminWithdraw.setPlatformAccount(nxtWithdraw.getPlatformAccount());
            nxtStructAdminWithdraw.setPlatformSerialNum(nxtWithdraw.getPlatformSerialNum());
            nxtStructAdminWithdraw.setRemarkUser(nxtWithdraw.getRemarkUser());
            nxtStructAdminWithdraw.setRemarkAdmin(nxtWithdraw.getRemarkAdmin());
        }

        //查询用户
        Map<Long,String> mapUserIdToName = new HashMap();
        List<NxtUser> nxtUserList = nxtUserService.selectByIdSet(0,Integer.MAX_VALUE,userIdList);
        for (NxtUser nxtUser : nxtUserList) {
            mapUserIdToName.put(nxtUser.getId(), nxtUser.getUsername());
        }

        //设置用户名
        for (NxtStructAdminWithdraw item : nxtStructAdminWithdrawList) {
            item.setUsername(mapUserIdToName.getOrDefault(item.getUserId(),null));
        }

        return nxtStructAdminWithdrawList;

    }

}
