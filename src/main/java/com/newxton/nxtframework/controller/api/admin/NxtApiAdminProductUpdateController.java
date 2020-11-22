package com.newxton.nxtframework.controller.api.admin;

import com.google.gson.Gson;
import com.newxton.nxtframework.process.NxtProcessProduct;
import com.newxton.nxtframework.struct.NxtStructProduct;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductUpdateController {

    @Resource
    private NxtProcessProduct nxtProcessProduct;

    @RequestMapping(value = "/api/admin/product/update", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestBody String json) {

        Gson gson = new Gson();

        NxtStructProduct nxtStructProduct = gson.fromJson(json,NxtStructProduct.class);

        return nxtProcessProduct.saveProductAllDetail(nxtStructProduct);

    }

}
