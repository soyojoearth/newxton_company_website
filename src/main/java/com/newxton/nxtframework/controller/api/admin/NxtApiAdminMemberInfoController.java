package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.entity.NxtUserLevel;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtTransactionService;
import com.newxton.nxtframework.service.NxtUserLevelService;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.admin.NxtStructAdminMember;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminMemberInfoController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUserLevelService nxtUserLevelService;

    @Resource
    private NxtTransactionService nxtTransactionService;

    @RequestMapping(value = "/api/admin/member/info", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long userId = jsonParam.getLong("userId");

        NxtUser nxtUser = nxtUserService.queryById(userId);

        if (nxtUser == null){
            return new NxtStructApiResult(53,"找不到该会员").toMap();
        }

        try {
            NxtStructAdminMember nxtStructAdminMember = this.memberDetail(nxtUser);
            return new NxtStructApiResult(nxtStructAdminMember).toMap();
        }
        catch (NxtException e){
            return new NxtStructApiResult(52,e.getNxtExecptionMessage()).toMap();
        }


    }

    /**
     * 获取用户详情
     * @param nxtUser
     * @return
     * @throws NxtException
     */
    private NxtStructAdminMember memberDetail(NxtUser nxtUser) throws NxtException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<Integer,String> mapLevelNumToName = new HashMap<>();
        List<NxtUserLevel> nxtUserLevelList = nxtUserLevelService.queryAll(new NxtUserLevel());
        for (NxtUserLevel nxtUserLevel : nxtUserLevelList) {
            mapLevelNumToName.put(nxtUserLevel.getNum(), nxtUserLevel.getName());
        }

        NxtStructAdminMember nxtStructAdminMember = new NxtStructAdminMember();
        nxtStructAdminMember.setUserId(nxtUser.getId());
        nxtStructAdminMember.setUsername(nxtUser.getUsername());
        nxtStructAdminMember.setPhone(nxtUser.getPhone());
        nxtStructAdminMember.setEmail(nxtUser.getEmail());
        nxtStructAdminMember.setDatelineRegister(nxtUser.getDatelineCreate());
        if (nxtUser.getDatelineCreate() != null) {
            nxtStructAdminMember.setDatelineRegisterReadable(sdf.format(new Date(nxtUser.getDatelineCreate())));
        }
        nxtStructAdminMember.setInviteesCount(nxtUser.getInviteesCount());
        nxtStructAdminMember.setLevelNum(nxtUser.getLevelNum());
        nxtStructAdminMember.setLevelName(mapLevelNumToName.getOrDefault(nxtUser.getLevelNum(),null));
        nxtStructAdminMember.setStatus(nxtUser.getStatus());
        nxtStructAdminMember.setStatusText(this.statusText(nxtUser.getStatus()));
        nxtStructAdminMember.setBlock(nxtUser.getStatus()!=null && nxtUser.getStatus().equals(-1));
        nxtStructAdminMember.setGender(nxtUser.getGender());
        nxtStructAdminMember.setCanInvite(!(nxtUser.getCanInvite() != null && nxtUser.getCanInvite().equals(0)));

        //邀请人
        if (nxtUser.getInviterUserId() != null){
            NxtUser nxtUserInviter = nxtUserService.queryById(nxtUser.getInviterUserId());
            if (nxtUserInviter != null){
                nxtStructAdminMember.setInviterUserId(nxtUserInviter.getId());
                nxtStructAdminMember.setInviterUsername(nxtUserInviter.getUsername());
            }
        }

        //余额
        Long balanceCount = nxtTransactionService.queryBalanceSumByUserId(nxtUser.getId());
        Float balance = balanceCount == null ? 0.00F : balanceCount/100F;
        nxtStructAdminMember.setBalance(balance);

        return nxtStructAdminMember;

    }

    private String statusText(Integer status){
        if (status.equals(0)){
            return "正常";
        }
        else if (status.equals(-1)){
            return "黑名单";
        }
        else {
            return null;
        }
    }

}
