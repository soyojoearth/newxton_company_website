package com.newxton.nxtframework.controller.api.front.shoppingcart;

import com.newxton.nxtframework.entity.NxtShoppingCart;
import com.newxton.nxtframework.entity.NxtShoppingCartProduct;
import com.newxton.nxtframework.service.NxtShoppingCartProductService;
import com.newxton.nxtframework.service.NxtShoppingCartService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/22
 * @address Shenzhen, China
 */
@RestController
public class NxtApiUserShoppingCartInfoController {

    @Resource
    private NxtShoppingCartService nxtShoppingCartService;

    @Resource
    private NxtShoppingCartProductService nxtShoppingCartProductService;

    @RequestMapping(value = "/api/user/shopping_cart/info", method = RequestMethod.POST)
    public NxtStructApiResult exec(@RequestHeader(value = "user_id", required = true) Long userId) {

        Long countChecked = 0L;
        Long countAll = 0L;

        //查询购物车
        NxtShoppingCart nxtShoppingCart = nxtShoppingCartService.queryByUserId(userId);
        if (nxtShoppingCart != null){
            List<NxtShoppingCartProduct> nxtShoppingCartProductList = nxtShoppingCartProductService.queryAllProductByShoppingCartId(nxtShoppingCart.getId());
            for (NxtShoppingCartProduct item :
                    nxtShoppingCartProductList) {
                if (item.getChecked() > 0){
                    countChecked += item.getQuantity();
                }
                countAll += item.getQuantity();
            }
        }

        Map<String,Object> data = new HashMap<>();
        data.put("countChecked",countChecked);
        data.put("countAll",countAll);

        return new NxtStructApiResult(data);

    }

}
