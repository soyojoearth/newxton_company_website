package com.newxton.nxtframework.controller.api.front.orderform;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.process.NxtProcessReviews;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderForm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/27
 * @address Shenzhen, China
 * 个人中心--订单中所有评论详情&列表
 */
@RestController
public class NxtApiOrderFormReviewsDetailController {

    @Resource
    private NxtProcessOrderForm nxtProcessOrderForm;

    @Resource
    private NxtProcessReviews nxtProcessReviews;

    @RequestMapping("/api/order_form/reviews/detail")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam, @RequestHeader(value = "user_id", required = true) Long userId) {

        Long orderFormId = jsonParam.getLong("id");



        try {

            NxtStructOrderForm nxtStructOrderForm = nxtProcessOrderForm.orderFormDetail(orderFormId);

            if (nxtStructOrderForm == null){
                return new NxtStructApiResult(54,"找不到该订单");
            }
            if (!nxtStructOrderForm.getUserId().equals(userId)){
                return new NxtStructApiResult(54,"该订单不属于你");
            }

            //给订单详情中的物品，查询添加评论数据
            nxtProcessReviews.queryReviewsPutIntoStructOrderForm(nxtStructOrderForm);

            return new NxtStructApiResult(nxtStructOrderForm);

        }
        catch (NxtException e){

            return new NxtStructApiResult(54,e.getNxtExecptionMessage());

        }


    }

}
