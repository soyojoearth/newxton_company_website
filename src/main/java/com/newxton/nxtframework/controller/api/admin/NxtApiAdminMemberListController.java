package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.entity.NxtUserLevel;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtUserLevelService;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.admin.NxtStructAdminMember;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminMemberListController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUserLevelService nxtUserLevelService;

    @RequestMapping(value = "/api/admin/member/list", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");
        Long userId = jsonParam.getLong("userId");
        String username = jsonParam.getString("username");
        Integer levelNum = jsonParam.getInteger("levelNum");
        String datelineRegisterBegin = jsonParam.getString("datelineRegisterBegin");
        String datelineRegisterEnd = jsonParam.getString("datelineRegisterEnd");

        if (offset == null || limit == null){
            return new NxtStructApiResult(53,"缺少参数：offset、limit").toMap();
        }

        try {
            List<NxtStructAdminMember> list = this.queryMemberList(offset,limit,userId,username,levelNum,datelineRegisterBegin,datelineRegisterEnd);
            Long count = this.queryMemberCount(userId,username,levelNum,datelineRegisterBegin,datelineRegisterEnd);
            Map<String,Object> result = new HashMap<>();
            result.put("list",list);
            result.put("count",count);
            return new NxtStructApiResult(result).toMap();
        }
        catch (NxtException e){
            return new NxtStructApiResult(52,e.getNxtExecptionMessage()).toMap();
        }

    }

    /**
     * 获取会员统计数字
     * @param userId
     * @param username
     * @param datelineRegisterBegin
     * @param datelineRegisterEnd
     * @return
     * @throws NxtException
     */
    private Long queryMemberCount(Long userId, String username, Integer levelNum, String datelineRegisterBegin, String datelineRegisterEnd) throws NxtException {

        Long datelineCreateBegin = null;
        Long datelineCreateEnd = null;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            if (datelineRegisterBegin != null && !datelineRegisterBegin.trim().isEmpty()) {
                date = dateFormat.parse(datelineRegisterBegin);
                datelineCreateBegin = date.getTime();
            }
            if (datelineRegisterEnd != null && !datelineRegisterEnd.trim().isEmpty()) {
                date = dateFormat.parse(datelineRegisterEnd);
                datelineCreateEnd = date.getTime();
            }
        } catch (Exception e) {
            throw new NxtException("日期转化错误");
        }

        return nxtUserService.adminQueryMemberCount(userId,username,levelNum,datelineCreateBegin,datelineCreateEnd);

    }

    /**
     * 获取会员列表，结构化友好可视数据
     * @param offset
     * @param limit
     * @param userId
     * @param username
     * @param datelineRegisterBegin
     * @param datelineRegisterEnd
     * @return
     * @throws NxtException
     */
    private List<NxtStructAdminMember> queryMemberList(Long offset, Long limit, Long userId, String username, Integer levelNum, String datelineRegisterBegin, String datelineRegisterEnd) throws NxtException{

        Long datelineCreateBegin = null;
        Long datelineCreateEnd = null;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            if (datelineRegisterBegin != null && !datelineRegisterBegin.trim().isEmpty()){
                date = dateFormat.parse(datelineRegisterBegin);
                datelineCreateBegin = date.getTime();
            }
            if (datelineRegisterEnd != null && !datelineRegisterEnd.trim().isEmpty()){
                date = dateFormat.parse(datelineRegisterEnd);
                datelineCreateEnd = date.getTime();
            }
        }
        catch (Exception e) {
            throw new NxtException("日期转化错误");
        }

        Map<Integer,String> mapLevelNumToName = new HashMap<>();
        List<NxtUserLevel> nxtUserLevelList = nxtUserLevelService.queryAll(new NxtUserLevel());
        for (NxtUserLevel nxtUserLevel : nxtUserLevelList) {
            mapLevelNumToName.put(nxtUserLevel.getNum(), nxtUserLevel.getName());
        }

        List<NxtUser> list = nxtUserService.adminQueryMemberList(offset,limit,userId,username,levelNum,datelineCreateBegin,datelineCreateEnd);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<NxtStructAdminMember> nxtStructAdminMemberList = new ArrayList<>();

        for (NxtUser nxtUser : list) {
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
            nxtStructAdminMemberList.add(nxtStructAdminMember);
        }

        return nxtStructAdminMemberList;

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
