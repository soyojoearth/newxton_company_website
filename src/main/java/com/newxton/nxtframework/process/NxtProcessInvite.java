package com.newxton.nxtframework.process;

import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructInvitees;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/1
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtProcessInvite {

    @Resource
    private NxtUserService nxtUserService;

    /**
     * 用户下家列表
     * @param offset
     * @param limit
     * @param inviterUserId
     * @param inviterLevel 几级下家
     * @return
     */
    public List<NxtStructInvitees> userInvitees(Long offset,Long limit,Long inviterUserId,Long inviterLevel) throws NxtException{

        List<NxtUser> nxtUserList;

        if (inviterLevel.equals(1L)){
            nxtUserList = nxtUserService.queryAllLevelOneInviteesUserIdLimit(offset,limit,inviterUserId);
        }
        else if (inviterLevel.equals(2L)){
            nxtUserList = nxtUserService.queryAllLevelTwoInviteesUserIdLimit(offset,limit,inviterUserId);
        }
        else if (inviterLevel.equals(3L)){
            nxtUserList = nxtUserService.queryAllLevelThreeInviteesUserIdLimit(offset,limit,inviterUserId);
        }
        else {
            throw new NxtException("最多支持3级下家查询");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<NxtStructInvitees> nxtStructInviteesList = new ArrayList<>();

        for (NxtUser user :
                nxtUserList) {
            NxtStructInvitees nxtStructInvitees = new NxtStructInvitees();
            nxtStructInvitees.setUserId(user.getId());
            nxtStructInvitees.setUsername(user.getUsername());
            nxtStructInvitees.setEmail(user.getEmail());
            nxtStructInvitees.setInviteesCount(user.getInviteesCount());
            if (user.getDatelineCreate() != null) {
                nxtStructInvitees.setDatelineCreate(user.getDatelineCreate());
                nxtStructInvitees.setDatelineCreateReadable(sdf.format(new Date(user.getDatelineCreate())));
            }
            else {
                nxtStructInvitees.setDatelineCreateReadable("---");
            }
            nxtStructInviteesList.add(nxtStructInvitees);
        }

        return nxtStructInviteesList;

    }

}
