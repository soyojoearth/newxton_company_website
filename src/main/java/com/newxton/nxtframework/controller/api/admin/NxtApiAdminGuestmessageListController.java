package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtGuestmessage;
import com.newxton.nxtframework.service.NxtGuestmessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminGuestmessageListController {

    @Resource
    private NxtGuestmessageService nxtGuestmessageService;

    @RequestMapping(value = "/api/admin/guestmessage/list", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "page_number", required = false) Integer pageNumber) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (pageNumber == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        int limit = 20;
        int offset = 0;

        if (pageNumber > 1){
            offset = limit * (pageNumber-1);
        }

        List<NxtGuestmessage> list = nxtGuestmessageService.queryDescAllByLimit(offset,limit);

        List<Map<String,Object>> listResult = new ArrayList<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < list.size(); i++) {
            NxtGuestmessage nxtGuestmessage = list.get(i);

            Map<String, Object> item = new HashMap<>();
            item.put("id",nxtGuestmessage.getId());
            item.put("guestCompany",nxtGuestmessage.getGuestCompany());
            item.put("guestName",nxtGuestmessage.getGuestName());
            item.put("guestPhone",nxtGuestmessage.getGuestPhone());
            item.put("guestEmail",nxtGuestmessage.getGuestEmail());
            item.put("messageContent",nxtGuestmessage.getMessageContent());
            item.put("messageDateline",nxtGuestmessage.getMessageDateline());
            item.put("messageDatelineReadable",sdf.format(new Date(nxtGuestmessage.getMessageDateline())));

            listResult.add(item);
        }
        result.put("list",listResult);

        return result;

    }

}
