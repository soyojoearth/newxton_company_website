package com.newxton.nxtframework.controller.api.front.ucenter;

import com.newxton.nxtframework.component.NxtWebUtilComponent;
import com.newxton.nxtframework.entity.NxtUploadfile;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtUploadfileService;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import com.newxton.nxtframework.struct.NxtStructUserInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiUserInfoController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtWebUtilComponent nxtWebUtilComponent;

    @RequestMapping(value = "/api/user/info", method = RequestMethod.POST)
    public NxtStructApiResult index(@RequestHeader(value = "user_id", required = true) Long userId) {


        NxtUser user = nxtUserService.queryById(userId);

        if (user == null) {
            return new NxtStructApiResult(42,"用户不存在");
        }

        if (user.getInviteCode() == null){
            //自动生成inviteCode
            while (true){
                int max=1999999999;
                int min=1111111;
                Random random = new Random();
                Long inviteCode = (long)random.nextInt(max)%(max-min+1) + min;
                NxtUser userRepeat = nxtUserService.queryByInviteCode(inviteCode.toString());
                if (userRepeat == null) {
                    user.setInviteCode(inviteCode);
                    nxtUserService.update(user);
                    break;
                }
            }
        }

        String inviteUrl = nxtWebUtilComponent.getDomainPath()+"/invite?id="+user.getInviteCode();
        String inviteQrCodePicUrl = nxtWebUtilComponent.getDomainPath()+"/inviteUrlQrImage_"+user.getInviteCode()+".png";

        NxtStructUserInfo nxtStructUserInfo = new NxtStructUserInfo();
        nxtStructUserInfo.setUsername(user.getUsername());
        nxtStructUserInfo.setEmail(user.getEmail());
        nxtStructUserInfo.setPhone(user.getPhone());
        nxtStructUserInfo.setInviteCode(user.getInviteCode());
        nxtStructUserInfo.setInviteUrl(inviteUrl);
        nxtStructUserInfo.setInviteUrlQrImageUrl(inviteQrCodePicUrl);

        if (user.getAvatarId() != null){
            NxtUploadfile nxtUploadfile = nxtUploadfileService.queryById(user.getAvatarId());
            nxtStructUserInfo.setAvatarPicUrl(nxtUploadfile.getUrlpath());
        }

        return new NxtStructApiResult(nxtStructUserInfo);

    }

}
