package com.newxton.nxtframework.model;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtProductBrand;
import com.newxton.nxtframework.service.NxtProductBrandService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtModelProductBrand {

    @Resource
    NxtProductBrandService nxtProductBrandService;

    public Map<String, Object> save(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");
        String brandName = jsonParam.getString("brandName");
        Long uploadfileId = jsonParam.getLong("uploadfileId");

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        NxtProductBrand nxtProductBrand;

        if (id == null){
            nxtProductBrand = new NxtProductBrand();
        }
        else {
            nxtProductBrand = nxtProductBrandService.queryById(id);
        }

        if (nxtProductBrand == null){
            result.put("status", 49);
            result.put("message", "找不到对应的内容");
            return result;
        }

        nxtProductBrand.setBrandName(brandName);
        nxtProductBrand.setUploadfileId(uploadfileId);

        if (id == null){
            nxtProductBrandService.insert(nxtProductBrand);
        }
        else {
            nxtProductBrandService.update(nxtProductBrand);
        }

        result.put("detail",nxtProductBrand);

        return result;

    }

}
