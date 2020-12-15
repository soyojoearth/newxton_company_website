package com.newxton.nxtframework.controller.api.front.ucenter;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtTransaction;
import com.newxton.nxtframework.service.NxtTransactionService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructUserTransaction;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/27
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserTransactionListController {

    @Resource
    private NxtTransactionService nxtTransactionService;

    @RequestMapping(value = "/api/user/transaction/list", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = true) Long userId,@RequestBody JSONObject jsonParam) {

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");

        if (offset == null || limit == null){
            return new NxtStructApiResult(53,"缺少offset或limit");
        }

        List<NxtTransaction> nxtTransactionList = nxtTransactionService.queryAllByUserIdLimit(offset,limit,userId);

        List<NxtStructUserTransaction> nxtStructUserTransactionList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (NxtTransaction nxtTransaction:nxtTransactionList) {
            NxtStructUserTransaction nxtStructUserTransaction = new NxtStructUserTransaction();
            nxtStructUserTransactionList.add(nxtStructUserTransaction);
            nxtStructUserTransaction.setId(nxtTransaction.getId());
            nxtStructUserTransaction.setDatelineReadable(sdf.format(new Date(nxtTransaction.getDateline())));
            nxtStructUserTransaction.setAmount(nxtTransaction.getAmount()/100F);
            if (nxtTransaction.getAmount() >= 0) {
                nxtStructUserTransaction.setTypeText("入账");
            }
            else {
                nxtStructUserTransaction.setTypeText("出账");
            }
            nxtStructUserTransaction.setEventText(this.eventText(nxtTransaction.getType()));
        }

        return new NxtStructApiResult(nxtStructUserTransactionList);

    }

    private String eventText(Integer type){
        if (type.equals(0)){
            return "---";
        }
        else if (type.equals(1)){
            return "充值";
        }
        else if (type.equals(2)){
            return "消费";
        }
        else if (type.equals(3)){
            return "退款";
        }
        else if (type.equals(4)){
            return "提现";
        }
        else if (type.equals(5)){
            return "撤销提现";
        }
        else if (type.equals(6)){
            return "佣金结算收入";
        }
        else {
            return "---";
        }
    }

}
