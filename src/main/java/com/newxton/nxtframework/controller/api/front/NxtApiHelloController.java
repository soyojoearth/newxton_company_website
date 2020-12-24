package com.newxton.nxtframework.controller.api.front;

import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiHelloController {

    @RequestMapping("/api/hello")
    public NxtStructApiResult exec() {
        return new NxtStructApiResult();
    }

}
