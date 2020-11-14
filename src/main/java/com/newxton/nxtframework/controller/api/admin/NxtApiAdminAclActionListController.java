package com.newxton.nxtframework.controller.api.admin;

import com.github.yt.web.result.PackageResponseBody;
import com.newxton.nxtframework.entity.NxtAclAction;
import com.newxton.nxtframework.service.NxtAclActionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/28
 * @address Shenzhen, China
 */
@RestController
public class NxtApiAdminAclActionListController {

    @Resource
    private NxtAclActionService nxtAclActionService;

    @RequestMapping(value = "/api/admin/acl_action_list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtAclAction> list = nxtAclActionService.queryAll(new NxtAclAction());
        result.put("list", list);

        return result;

    }


    /**
     * 引入 yt-web 自动包装返回体，上面的方法可以简写成如下方式
     * 参考： https://github.com/javawebers/yt/tree/master/yt-web
     * <p>
     *
     * 稍有一点不同的地方：
     * 原结果的结构体（list）
     * {
     *     "status": 0,
     *     "message": ""
     *     "list": []
     * }
     *
     * 框架结构体（result），在 SimpleResultConfig 中配置，所有接口统一
     * {
     *     "status": 0,
     *     "message": "操作成功",
     *     "result": []
     * }
     *
     */
    @PackageResponseBody
    @PostMapping(value = "/api/admin/acl_action_list2")
    public List<NxtAclAction> index2() {
        return nxtAclActionService.queryAll(new NxtAclAction());
    }


}
