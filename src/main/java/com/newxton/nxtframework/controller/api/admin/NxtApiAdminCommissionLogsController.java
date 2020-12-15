package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.service.*;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.admin.NxtStructAdminCommission;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/9
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminCommissionLogsController {

    @Resource
    private NxtCommissionService nxtCommissionService;

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtOrderFormProductService nxtOrderFormProductService;

    @RequestMapping(value = "/api/admin/commission/logs", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Integer offset = jsonParam.getInteger("offset");
        Integer limit = jsonParam.getInteger("limit");
        String orderFormSerialNum = jsonParam.getString("orderFormSerialNum");
        String username = jsonParam.getString("username");
        Boolean isPaid = jsonParam.getBoolean("isPaid");
        Boolean isReceive = jsonParam.getBoolean("isReceive");
        Boolean isTransfer = jsonParam.getBoolean("isTransfer");

        Long userId = null;
        if (username != null && !username.trim().isEmpty()){
            NxtUser nxtUser = nxtUserService.queryByUsername(username);
            if (nxtUser == null){
                return new NxtStructApiResult(53,"找不到该用户:"+username).toMap();
            }
            else {
                userId = nxtUser.getId();
            }
        }
        Long orderFormId = null;
        if (orderFormSerialNum != null && !orderFormSerialNum.trim().isEmpty()){
            NxtOrderForm nxtOrderForm = nxtOrderFormService.queryBySerialNum(orderFormSerialNum);
            if (nxtOrderForm == null){
                return new NxtStructApiResult(53,"找不到该订单:"+orderFormSerialNum).toMap();
            }
            else {
                orderFormId = nxtOrderForm.getId();
            }
        }


        List<NxtCommission> nxtCommissionList = nxtCommissionService.adminQueryList(offset,limit,userId,orderFormId,isPaid,isReceive,isTransfer);
        Long count = nxtCommissionService.adminQueryCount(userId,orderFormId,isPaid,isReceive,isTransfer);

        List<NxtStructAdminCommission> nxtStructAdminCommissionList = this.assemblyStructAdminCommission(nxtCommissionList);

        Map<String,Object> result = new HashMap<>();
        result.put("count",count);
        result.put("list",nxtStructAdminCommissionList);

        return new NxtStructApiResult(result).toMap();

    }

    /**
     * 装配结构化数据列表
     * @param nxtCommissionList
     * @return
     */
    private List<NxtStructAdminCommission> assemblyStructAdminCommission(List<NxtCommission> nxtCommissionList){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Long> orderFormIdList = new ArrayList<>();
        List<Long> orderFormProductIdList = new ArrayList<>();
        List<Long> userIdList = new ArrayList<>();

        List<NxtStructAdminCommission> list = new ArrayList<>();

        for (NxtCommission nxtCommission : nxtCommissionList) {
            NxtStructAdminCommission nxtStructAdminCommission = new NxtStructAdminCommission();
            list.add(nxtStructAdminCommission);

            orderFormIdList.add(nxtCommission.getOrderFormId());
            orderFormProductIdList.add(nxtCommission.getOrderFormProductId());
            userIdList.add(nxtCommission.getUserId());

            nxtStructAdminCommission.setId(nxtCommission.getId());
            nxtStructAdminCommission.setOrderFormId(nxtCommission.getOrderFormId());
            nxtStructAdminCommission.setOrderFormProductId(nxtCommission.getOrderFormProductId());

            nxtStructAdminCommission.setQuantityDeal(nxtCommission.getQuantityDeal());
            nxtStructAdminCommission.setQuantityRefund(nxtCommission.getQuantityRefund());

            nxtStructAdminCommission.setInviterUserId(nxtCommission.getUserId());

            nxtStructAdminCommission.setCommissionAmount(nxtCommission.getCommissionAmount()/100F);
            nxtStructAdminCommission.setCommissionRate(nxtCommission.getCommissionRate());
            nxtStructAdminCommission.setInviterLevel(nxtCommission.getInviterLevel());
            Long dateline = nxtCommission.getDatelineCreate();
            if (nxtCommission.getDatelineReceived() != null){
                dateline = nxtCommission.getDatelineReceived();
            }
            nxtStructAdminCommission.setDateline(dateline);
            nxtStructAdminCommission.setDatelineReadable(sdf.format(new Date(dateline)));

            nxtStructAdminCommission.setStatusText(this.statusText(nxtCommission));

        }

        //查询用户
        Map<Long,String> mapUserIdToName = new HashMap();
        List<NxtUser> nxtUserList = nxtUserService.selectByIdSet(0,Integer.MAX_VALUE,userIdList);
        for (NxtUser nxtUser : nxtUserList) {
            mapUserIdToName.put(nxtUser.getId(), nxtUser.getUsername());
        }

        //查询订单
        Map<Long,String> mapOrderFormIdToSerialNum = new HashMap<>();
        List<NxtOrderForm> nxtOrderFormList = nxtOrderFormService.selectByIdSet(orderFormIdList);
        for (NxtOrderForm nxtOrderForm : nxtOrderFormList) {
            mapOrderFormIdToSerialNum.put(nxtOrderForm.getId(), nxtOrderForm.getSerialNum());
        }

        //查询订单物品、成交价
        Map<Long,String> mapOrderFormProductIdToName = new HashMap<>();
        Map<Long,Float> mapOrderFormProductIdToPriceDeal = new HashMap<>();

        List<NxtOrderFormProduct> nxtOrderFormProductList = nxtOrderFormProductService.selectAllByIdSet(orderFormProductIdList);
        for (NxtOrderFormProduct nxtOrderFormProduct : nxtOrderFormProductList) {
            mapOrderFormProductIdToName.put(nxtOrderFormProduct.getId(), nxtOrderFormProduct.getProductName());
            mapOrderFormProductIdToPriceDeal.put(nxtOrderFormProduct.getId(),nxtOrderFormProduct.getProductPriceDeal()/100F);
        }

        for (NxtStructAdminCommission item : list) {
            item.setInviterUsername(mapUserIdToName.getOrDefault(item.getInviterUserId(),null));
            item.setOrderFormSerialNum(mapOrderFormIdToSerialNum.getOrDefault(item.getOrderFormId(),null));
            item.setOrderFormProductName(mapOrderFormProductIdToName.getOrDefault(item.getOrderFormProductId(),null));
            item.setPriceDeal(mapOrderFormProductIdToPriceDeal.getOrDefault(item.getOrderFormProductId(),null));
        }

        return list;

    }

    private String statusText(NxtCommission nxtCommission){
        if (nxtCommission.getIsPaid().equals(0)){
            return "未支付";
        }
        if (nxtCommission.getDatelineReceived() == null){
            return "等待确认收货";
        }
        if (nxtCommission.getIsTransfer().equals(0)){
            return "未结算";
        }
        else {
            return "已结算到余额";
        }
    }

}
