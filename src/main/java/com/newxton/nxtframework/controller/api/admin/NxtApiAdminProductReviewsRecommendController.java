package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.entity.NxtReviews;
import com.newxton.nxtframework.service.NxtProductService;
import com.newxton.nxtframework.service.NxtReviewsService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/7
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminProductReviewsRecommendController {

    @Resource
    private NxtReviewsService nxtReviewsService;

    @RequestMapping(value = "/api/admin/product_reviews/recommend", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");
        Boolean isRecommend = jsonParam.getBoolean("isRecommend");

        if (id == null || isRecommend == null) {
            return new NxtStructApiResult(53,"参数错误").toMap();
        }

        NxtReviews nxtReviews = nxtReviewsService.queryById(id);

        if (nxtReviews == null){
            return new NxtStructApiResult(53,"该评论不存在").toMap();
        }
        nxtReviews.setIsRecommend(isRecommend ? 1 : 0);
        nxtReviewsService.update(nxtReviews);

        return new NxtStructApiResult().toMap();

    }

}
