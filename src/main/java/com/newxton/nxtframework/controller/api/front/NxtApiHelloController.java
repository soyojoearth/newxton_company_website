package com.newxton.nxtframework.controller.api.front;

import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiHelloController {

    @Resource
    private NxtUserService nxtUserService;


    @RequestMapping("/api/hello")
    public Map<String,Object> exec() {

        //心跳检查

        NxtUser user = nxtUserService.queryById(1L);

        Map<String,Object> result = new HashMap<>();
        result.put("status",0);
        result.put("message","hello world");

        return result;

    }

}
