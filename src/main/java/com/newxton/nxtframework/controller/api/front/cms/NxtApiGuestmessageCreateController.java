package com.newxton.nxtframework.controller.api.front.cms;

import com.google.gson.Gson;
import com.newxton.nxtframework.entity.NxtGuestmessage;
import com.newxton.nxtframework.service.NxtGuestmessageService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructGuestMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public NxtStructApiResult exec(@RequestBody String json) {

        Gson gson = new Gson();
        NxtStructGuestMessage nxtStructGuestMessage = gson.fromJson(json, NxtStructGuestMessage.class);

        if (nxtStructGuestMessage.getMessageContent() == null) {
            return new NxtStructApiResult(52,"至少填上内容");
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
            return new NxtStructApiResult(50,"系统错误");
        }

        return new NxtStructApiResult();

    }
}
