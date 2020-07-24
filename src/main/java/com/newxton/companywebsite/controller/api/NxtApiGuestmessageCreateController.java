package com.newxton.companywebsite.controller.api;

import com.newxton.companywebsite.entity.NxtGuestmessage;
import com.newxton.companywebsite.service.NxtContentService;
import com.newxton.companywebsite.service.NxtGuestmessageService;
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
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiGuestmessageCreateController {

    @Resource
    private NxtGuestmessageService nxtGuestmessageService;

    @RequestMapping(value = "/api/guestmessage/create", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "guest_company", required=false) String guestCompany,
                                     @RequestParam(value = "guest_name", required=false) String guestName,
                                     @RequestParam(value = "guest_phone", required=false) String guestPhone,
                                     @RequestParam(value = "guest_email", required=false) String guestEmail,
                                     @RequestParam(value = "message_content", required=false) String messageContent
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (messageContent == null) {
            result.put("status", 52);
            result.put("message", "至少填上内容");
            return result;
        }

        NxtGuestmessage nxtGuestmessage = new NxtGuestmessage();
        nxtGuestmessage.setGuestCompany(guestCompany);
        nxtGuestmessage.setGuestName(guestName);
        nxtGuestmessage.setGuestPhone(guestPhone);
        nxtGuestmessage.setGuestEmail(guestEmail);
        nxtGuestmessage.setMessageContent(messageContent);
        nxtGuestmessage.setMessageDateline(System.currentTimeMillis());

        NxtGuestmessage nxtGuestmessageCreated = nxtGuestmessageService.insert(nxtGuestmessage);

        if (nxtGuestmessageCreated.getId() == null){
            result.put("status", 50);
            result.put("message", "系统错误");
            return result;
        }

        return result;

    }
}
