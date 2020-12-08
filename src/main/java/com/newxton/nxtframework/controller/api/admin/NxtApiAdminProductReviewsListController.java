package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.process.NxtProcessReviews;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.admin.NxtStructAdminProductReviewsItem;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/7
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminProductReviewsListController {

    @Resource
    private NxtProcessReviews nxtProcessReviews;

    @RequestMapping(value = "/api/admin/product_reviews/list", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody JSONObject jsonParam) {

        Long offset = jsonParam.getLong("offset");
        Long limit = jsonParam.getLong("limit");
        String productUrl = jsonParam.getString("productUrl");

        Long productId = null;

        if (offset == null || limit == null){
            return new NxtStructApiResult(53,"缺少参数：offset或limit").toMap();
        }

        if (productUrl != null && !productUrl.trim().isEmpty()) {
            String pattern = "productId=(\\d+)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(productUrl);
            if (m.find()) {
                productId = Long.valueOf(m.group(1));
            } else {
                return new NxtStructApiResult(53, "产品链接不对").toMap();
            }
        }

        List<NxtStructAdminProductReviewsItem> list = nxtProcessReviews.adminProductReviewsItemList(offset,limit,productId);
        Long count = nxtProcessReviews.adminProductReviewsItemCount(offset,limit,productId);

        Map<String,Object> result = new HashMap<>();
        result.put("list",list);
        result.put("count",count);

        return new NxtStructApiResult(result).toMap();

    }

}
