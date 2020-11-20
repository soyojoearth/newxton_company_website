package com.newxton.nxtframework.controller.api.front;

import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtGuestmessage;
import com.newxton.nxtframework.service.NxtGuestmessageService;
import com.newxton.nxtframework.struct.NxtStructGuestMessage;
import org.springframework.web.bind.annotation.*;

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
public class NxtApiGuestmessageCreateController {

    @Resource
    private NxtGuestmessageService nxtGuestmessageService;

    @RequestMapping(value = "/api/guestmessage/create", method = RequestMethod.POST)
    public Map<String, Object> exec(@RequestBody String json) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        Gson gson = new Gson();
        NxtStructGuestMessage nxtStructGuestMessage = gson.fromJson(json, NxtStructGuestMessage.class);

        if (nxtStructGuestMessage.getMessageContent() == null) {
            result.put("status",52);
            result.put("message","至少填上内容");
            return result;
        }

        NxtGuestmessage nxtGuestmessage = new NxtGuestmessage();
        nxtGuestmessage.setGuestCompany(nxtStructGuestMessage.getGuestCompany());
        nxtGuestmessage.setGuestName(nxtStructGuestMessage.getGuestName());
        nxtGuestmessage.setGuestPhone(nxtStructGuestMessage.getGuestPhone());
        nxtGuestmessage.setGuestEmail(nxtStructGuestMessage.getGuestEmail());
        nxtGuestmessage.setMessageContent(nxtStructGuestMessage.getMessageContent());
        nxtGuestmessage.setMessageDateline(System.currentTimeMillis());

        NxtGuestmessage nxtGuestmessageCreated = nxtGuestmessageService.insert(nxtGuestmessage);

        if (nxtGuestmessageCreated.getId() == null){
            result.put("status",50);
            result.put("message","系统错误");
            return result;
        }

        return result;

    }
}
