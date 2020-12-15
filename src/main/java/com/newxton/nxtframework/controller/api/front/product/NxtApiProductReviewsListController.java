package com.newxton.nxtframework.controller.api.front.product;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.process.NxtProcessReviews;
import com.newxton.nxtframework.service.NxtReviewsService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructProductReviewsItem;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiProductReviewsListController {

    @Resource
    private NxtReviewsService nxtReviewsService;

    @Resource
    private NxtProcessReviews nxtProcessReviews;

    @RequestMapping("/api/product_reviews/list")
    public NxtStructApiResult exec(@RequestBody JSONObject jsonParam) {

        Long productId = jsonParam.getLong("productId");
        Integer offset = jsonParam.getInteger("offset");
        Integer limit = jsonParam.getInteger("limit");
        Integer requirePages = jsonParam.getInteger("requirePages");

        if (offset == null || limit == null){
            return new NxtStructApiResult(54,"缺少：offset、limit");
        }

        List<NxtStructProductReviewsItem> list = nxtProcessReviews.productReviewsItemList(offset,limit,productId);

        Map<String,Object> data = new HashMap<>();

        data.put("list",list);

        if (requirePages != null && requirePages.equals(1)) {
            Long count = nxtReviewsService.queryUserReviewsCountByProductId(productId);
            data.put("pages",(long)Math.ceil(count / (float)limit));
        }

        return new NxtStructApiResult(data);

    }

}
