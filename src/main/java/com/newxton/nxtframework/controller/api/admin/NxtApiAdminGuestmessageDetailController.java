package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtGuestmessage;
import com.newxton.nxtframework.service.NxtGuestmessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminGuestmessageDetailController {

    @Resource
    private NxtGuestmessageService nxtGuestmessageService;

    @RequestMapping(value = "/api/admin/guestmessage/detail", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        NxtGuestmessage nxtGuestmessage = nxtGuestmessageService.queryById(id);
        if (nxtGuestmessage == null){
            result.put("status", 49);
            result.put("message", "对应的内容不存在");
            return result;
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, Object> item = new HashMap<>();
        item.put("id",nxtGuestmessage.getId());
        item.put("guestCompany",nxtGuestmessage.getGuestCompany());
        item.put("guestName",nxtGuestmessage.getGuestName());
        item.put("guestPhone",nxtGuestmessage.getGuestPhone());
        item.put("guestEmail",nxtGuestmessage.getGuestEmail());
        item.put("messageContent",nxtGuestmessage.getMessageContent());
        item.put("messageDateline",nxtGuestmessage.getMessageDateline());
        item.put("messageDatelineReadable",sdf.format(new Date(nxtGuestmessage.getMessageDateline())));

        result.put("detail",item);

        return result;

    }

}
