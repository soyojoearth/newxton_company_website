package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtReviews;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessOrderForm;
import com.newxton.nxtframework.process.NxtProcessReviews;
import com.newxton.nxtframework.service.NxtReviewsService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderForm;
import com.newxton.nxtframework.struct.NxtStructOrderFormProduct;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/7
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductReviewsDetailController {

    @Resource
    private NxtProcessOrderForm nxtProcessOrderForm;

    @Resource
    private NxtProcessReviews nxtProcessReviews;

    @Resource
    private NxtReviewsService nxtReviewsService;

    @RequestMapping(value = "/api/admin/product_reviews/detail", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");

        NxtReviews nxtReviews = nxtReviewsService.queryById(id);

        if (nxtReviews == null){
            return new NxtStructApiResult(53,"找不到该评论").toMap();
        }

        if (nxtReviews.getParentId() > 0){
            nxtReviews = nxtReviewsService.queryById(nxtReviews.getParentId());
        }

        if (nxtReviews == null){
            return new NxtStructApiResult(53,"找不到该评论的根评论").toMap();
        }

        try {

            Long orderFormId = nxtReviews.getOrderFormId();

            NxtStructOrderForm nxtStructOrderForm = nxtProcessOrderForm.orderFormDetail(orderFormId);

            if (nxtStructOrderForm == null){
                return new NxtStructApiResult(54,"找不到该订单").toMap();
            }

            //给订单详情中的物品，查询添加评论数据
            nxtProcessReviews.queryReviewsPutIntoStructOrderForm(nxtStructOrderForm);

            //检查物品，找这条根评论
            for (NxtStructOrderFormProduct item : nxtStructOrderForm.getOrderFormProductList()) {
                if (item.getReviewsItem() != null && item.getReviewsItem().getId().equals(nxtReviews.getId())){
                    return new NxtStructApiResult(item.getReviewsItem()).toMap();
                }
            }

            return new NxtStructApiResult("该评论详情无法找到").toMap();

        }
        catch (NxtException e){

            return new NxtStructApiResult(54,e.getNxtExecptionMessage()).toMap();

        }

    }


}
