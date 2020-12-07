package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtReviews;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtReviewsService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/7
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminProductReviewsReplyController {

    @Resource
    private NxtReviewsService nxtReviewsService;

    @RequestMapping("/api/admin/product_reviews/reply")
    public Map<String, Object> exec(@RequestBody JSONObject jsonParam,@RequestHeader(value = "user_id", required = true) Long userId) {

        Long id = jsonParam.getLong("id");
        String content = jsonParam.getString("content");

        if (id == null || content == null){
            return new NxtStructApiResult(53,"缺少id或content").toMap();
        }

        //查询有没有根评论
        NxtReviews rootReviews = nxtReviewsService.queryById(id);

        if (rootReviews == null) {
            return new NxtStructApiResult(53,"找不到要回复的评论").toMap();
        }

        if (rootReviews.getParentId() > 0){
            return new NxtStructApiResult(53,"提供的id必须是根评论id").toMap();
        }

        NxtReviews nxtReviews = new NxtReviews();
        nxtReviews.setOriginType(1);//0:用户评 1:管理员回复
        nxtReviews.setUserId(userId);//管理员userId
        nxtReviews.setProductId(rootReviews.getProductId());
        nxtReviews.setOrderFormId(rootReviews.getOrderFormId());
        nxtReviews.setOrderFormProductId(rootReviews.getOrderFormProductId());
        nxtReviews.setDateline(System.currentTimeMillis());
        nxtReviews.setContent(content);
        nxtReviews.setParentId(rootReviews.getId());
        nxtReviews.setIsRecommend(0);
        nxtReviews.setIsHidden(0);//管理员评论，不隐藏

        nxtReviewsService.insert(nxtReviews);

        return new NxtStructApiResult().toMap();

    }

}
