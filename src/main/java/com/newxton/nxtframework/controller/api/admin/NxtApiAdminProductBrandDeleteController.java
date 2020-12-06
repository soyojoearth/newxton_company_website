package com.newxton.nxtframework.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.entity.NxtProductBrand;
import com.newxton.nxtframework.entity.R;
import com.newxton.nxtframework.enums.RStatus;
import com.newxton.nxtframework.service.NxtProductBrandService;
import com.newxton.nxtframework.service.NxtProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/4
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminProductBrandDeleteController {

    @Resource
    private NxtProductBrandService nxtProductBrandService;

    @Resource
    private NxtProductService nxtProductService;

    @RequestMapping(value = "/api/admin/product_brand/delete", method = RequestMethod.POST)
    public R<String> index(@RequestBody JSONObject jsonParam) {

        Long id = jsonParam.getLong("id");

        if (id == null) {
            return R.error(RStatus.ERROR_PARAM);
        }

        /*先查询*/
        NxtProductBrand content = nxtProductBrandService.queryById(id);
        if (content == null) {
            return R.error(RStatus.NO_CONTENT);
        }

        //检查冲突
        NxtProduct nxtProductCondition = new NxtProduct();
        nxtProductCondition.setBrandId(content.getId());

        Long count = nxtProductService.queryCount(nxtProductCondition);

        if (count > 0) {
            return R.error(RStatus.QUOTE_IN_ADVANCE);
        }

        nxtProductBrandService.deleteById(content.getId());

        return R.ok();

    }

}
