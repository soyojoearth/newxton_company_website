package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtReviews;
import com.newxton.nxtframework.service.NxtReviewsService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
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
 */
@RestController
public class NxtApiAdminProductReviewsHiddenController {

    @Resource
    private NxtReviewsService nxtReviewsService;

    @RequestMapping(value = "/api/admin/product_reviews/hidden", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");
        Boolean isHidden = jsonParam.getBoolean("isHidden");

        if (id == null || isHidden == null) {
            return new NxtStructApiResult(53,"参数错误").toMap();
        }

        NxtReviews nxtReviews = nxtReviewsService.queryById(id);

        if (nxtReviews == null){
            return new NxtStructApiResult(53,"该评论不存在").toMap();
        }
        nxtReviews.setIsHidden(isHidden ? 1 : 0);
        nxtReviewsService.update(nxtReviews);

        return new NxtStructApiResult().toMap();

    }

}
