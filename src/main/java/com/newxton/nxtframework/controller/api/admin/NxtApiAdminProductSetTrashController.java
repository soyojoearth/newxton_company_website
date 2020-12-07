package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.service.NxtProductService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructOrderFromCreate;
import com.newxton.nxtframework.struct.admin.NxtStructAdminProductSetTrashPost;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductSetTrashController {

    @Resource
    private NxtProductService nxtProductService;

    @RequestMapping(value = "/api/admin/product/set_trash", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody String json) {

        Gson gson = new Gson();
        NxtStructAdminProductSetTrashPost postData;
        try {
            postData = gson.fromJson(json, NxtStructAdminProductSetTrashPost.class);
        }
        catch (Exception e){
            return new NxtStructApiResult(54,"json数据不对").toMap();
        }


        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (postData.getId() != null) {
            /*先查询*/
            NxtProduct nxtProduct = nxtProductService.queryById(postData.getId());
            if (nxtProduct == null) {
                return new NxtStructApiResult(49, "对应的产品不存在").toMap();
            }
            nxtProduct.setIsTrash(postData.getTrash() ? 1 : 0);//1放入回收站 0恢复
            nxtProductService.update(nxtProduct);
        }

        if (postData.getIdList() != null){
            for (Long productId : postData.getIdList()) {
                /*先查询*/
                NxtProduct nxtProduct = nxtProductService.queryById(productId);
                if (nxtProduct != null) {
                    nxtProduct.setIsTrash(postData.getTrash() ? 1 : 0);//1放入回收站 0恢复
                    nxtProductService.update(nxtProduct);
                }
            }
        }

        return new NxtStructApiResult().toMap();

    }

}
