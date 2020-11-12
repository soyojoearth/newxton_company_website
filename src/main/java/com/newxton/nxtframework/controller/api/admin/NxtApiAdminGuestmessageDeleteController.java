package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtGuestmessage;
import com.newxton.nxtframework.service.NxtGuestmessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminGuestmessageDeleteController {

    @Resource
    private NxtGuestmessageService nxtGuestmessageService;

    @RequestMapping(value = "/api/admin/guestmessage/delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        /*先查询，再删除*/
        NxtGuestmessage nxtGuestmessage = nxtGuestmessageService.queryById(id);
        if (nxtGuestmessage == null){
            result.put("status", 49);
            result.put("message", "对应的留言不存在");
            return result;
        }

        nxtGuestmessageService.deleteById(nxtGuestmessage.getId());

        return result;

    }

}
