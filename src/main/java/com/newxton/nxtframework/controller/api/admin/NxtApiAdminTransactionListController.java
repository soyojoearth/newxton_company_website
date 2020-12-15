package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtTransaction;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtTransactionService;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.admin.NxtStructAdminTransaction;
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
public class NxtApiAdminTransactionListController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtTransactionService nxtTransactionService;

    @RequestMapping(value = "/api/admin/transaction/list", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");
        Integer type = jsonParam.getInteger("type");//交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
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

        List<NxtTransaction> nxtTransactionList = nxtTransactionService.adminQueryList(offset, limit, userId, type);
        Long count = nxtTransactionService.adminQueryCount(userId, type);
        List<NxtStructAdminTransaction> list = this.assemblyStructAdminTransaction(nxtTransactionList);

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("list", list);

        return new NxtStructApiResult(result).toMap();
    }

    /**
     * 装配资金列表结构化数据
     * @param nxtTransactionList
     * @return
     */
    private List<NxtStructAdminTransaction> assemblyStructAdminTransaction(List<NxtTransaction> nxtTransactionList) {

        //交易类型（1:充值 2:消费 3:退款 4:提现 5:撤销提现 6:佣金结算收入）
        Map<Integer,String> mapType = new HashMap<>();
        mapType.put(1,"充值");
        mapType.put(2,"消费");
        mapType.put(3,"退款");
        mapType.put(4,"提现");
        mapType.put(5,"撤销提现");
        mapType.put(6,"佣金结算收入");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Long> userIdList = new ArrayList<>();

        List<NxtStructAdminTransaction> nxtStructAdminTransactionList = new ArrayList<>();

        for (NxtTransaction nxtTransaction : nxtTransactionList) {
            userIdList.add(nxtTransaction.getUserId());
            NxtStructAdminTransaction nxtStructAdminTransaction = new NxtStructAdminTransaction();
            nxtStructAdminTransactionList.add(nxtStructAdminTransaction);

            nxtStructAdminTransaction.setId(nxtTransaction.getId());
            nxtStructAdminTransaction.setUserId(nxtTransaction.getUserId());
            nxtStructAdminTransaction.setAmount(nxtTransaction.getAmount()/100F);
            if (nxtTransaction.getBalance() != null) {
                nxtStructAdminTransaction.setBalance(nxtTransaction.getBalance() / 100F);
            }
            nxtStructAdminTransaction.setDateline(nxtTransaction.getDateline());
            nxtStructAdminTransaction.setDatelineReadable(sdf.format(new Date(nxtTransaction.getDateline())));
            nxtStructAdminTransaction.setType(nxtTransaction.getType());
            nxtStructAdminTransaction.setTypeText(mapType.getOrDefault(nxtTransaction.getType(),null));
        }

        //查询用户
        Map<Long,String> mapUserIdToName = new HashMap();
        List<NxtUser> nxtUserList = nxtUserService.selectByIdSet(0,Integer.MAX_VALUE,userIdList);
        for (NxtUser nxtUser : nxtUserList) {
            mapUserIdToName.put(nxtUser.getId(), nxtUser.getUsername());
        }
        //设置用户名
        for (NxtStructAdminTransaction item : nxtStructAdminTransactionList) {
            item.setUsername(mapUserIdToName.getOrDefault(item.getUserId(),null));
        }

        return nxtStructAdminTransactionList;

    }

}
