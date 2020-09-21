package com.newxton.supercompanyapi.controller.api.front;

import com.newxton.supercompanyapi.entity.NxtUser;
import com.newxton.supercompanyapi.service.NxtUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiHelloController {

    @Resource
    private NxtUserService nxtUserService;


    @RequestMapping("/api/hello")
    public String index() {

        //这里可供k8s用来检测容器运行状态

        NxtUser user = nxtUserService.queryById(1L);

        return "hello world";

    }

}
