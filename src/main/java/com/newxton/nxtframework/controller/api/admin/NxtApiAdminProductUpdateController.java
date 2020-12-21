package com.newxton.nxtframework.controller.api.admin;

import com.google.gson.Gson;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessProduct;
import com.newxton.nxtframework.struct.NxtStructApiResult;
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

        try {
            NxtStructProduct nxtStructProduct = gson.fromJson(json,NxtStructProduct.class);
            if (nxtStructProduct.getCategoryId() == null){
                return new NxtStructApiResult(52,"请选择类别").toMap();
            }
            if (nxtStructProduct.getDeliveryConfigId() == null){
                return new NxtStructApiResult(52,"请选择运费模版").toMap();
            }
            try {
                NxtStructProduct detail =  nxtProcessProduct.saveProductAllDetail(nxtStructProduct);
                return new NxtStructApiResult(detail).toMap();
            }
            catch (NxtException e){
                return new NxtStructApiResult(53,e.getNxtExecptionMessage()).toMap();
            }
        }
        catch (Exception e){
            return new NxtStructApiResult(53,"json解析错误或者缺少参数").toMap();
        }

    }

}
