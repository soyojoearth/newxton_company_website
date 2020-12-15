package com.newxton.nxtframework.controller.api.front.orderform;

import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.entity.NxtOrderFormProduct;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessReviews;
import com.newxton.nxtframework.service.NxtOrderFormProductService;
import com.newxton.nxtframework.service.NxtOrderFormService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderFormReivewsCreate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/27
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiOrderFormReviewsCreateController {



    @Resource
    private NxtProcessReviews nxtProcessReviews;

    @RequestMapping("/api/order_form/reviews/create")
    public NxtStructApiResult exec(@RequestBody String json, @RequestHeader(value = "user_id", required = true) Long userId) {

        Gson gson = new Gson();

        NxtStructOrderFormReivewsCreate nxtStructOrderFormReivewsCreate;

        try {
            nxtStructOrderFormReivewsCreate = gson.fromJson(json, NxtStructOrderFormReivewsCreate.class);
        }
        catch (Exception e){
            return new NxtStructApiResult(54,"json数据不对");
        }

        try {
            nxtProcessReviews.create(userId,nxtStructOrderFormReivewsCreate);
        }
        catch (NxtException e){
            return new NxtStructApiResult(54,e.getNxtExecptionMessage());
        }

        return new NxtStructApiResult();

    }

}
