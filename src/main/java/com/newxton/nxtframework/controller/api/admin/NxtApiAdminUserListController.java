package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.entity.NxtAclRole;
import com.newxton.nxtframework.entity.NxtAclUserRole;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtAclRoleService;
import com.newxton.nxtframework.service.NxtAclUserRoleService;
import com.newxton.nxtframework.service.NxtUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminUserListController {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtAclUserRoleService nxtAclUserRoleService;

    @Resource
    private NxtAclRoleService nxtAclRoleService;

    @RequestMapping(value = "/api/admin/user_list", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "page_number", required = false) Integer pageNumber
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (pageNumber == null) {
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        //roleId 映射 role name
        List<NxtAclRole> nxtAclRoleList = nxtAclRoleService.queryAll(new NxtAclRole());
        Map<Long,String> mapRoleIdToName = new HashMap<>();
        for (NxtAclRole nxtAclRole :
                nxtAclRoleList) {
            mapRoleIdToName.put(nxtAclRole.getId(), nxtAclRole.getRoleName());
        }

        //userId 映射 roleId
        List<NxtAclUserRole> nxtAclUserRoleList = nxtAclUserRoleService.queryAll(new NxtAclUserRole());
        Map<Long,Long> mapUserIdToRoleId = new HashMap<>();
        for (NxtAclUserRole nxtAclUserRole :
                nxtAclUserRoleList) {
            mapUserIdToRoleId.put(nxtAclUserRole.getUserId(), nxtAclUserRole.getRoleId());
        }

        int limit = 20;
        int offset = 0;

        if (pageNumber > 1){
            offset = limit * (pageNumber-1);
        }

        List<NxtUser> list = nxtUserService.queryAllByLimit(offset,limit);

        List<Map<String,Object>> listResult = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {

            NxtUser user = list.get(i);



            Map<String, Object> item = new HashMap<>();

            item.put("id",user.getId());
            item.put("username",user.getUsername());
            item.put("status",user.getStatus());
            item.put("roleId",null);
            item.put("roleName","--");

            Long roleId = mapUserIdToRoleId.get(user.getId());

            if (roleId != null){
                item.put("roleId",roleId);
                String roleName = mapRoleIdToName.get(roleId);
                if (roleName != null){
                    item.put("roleName",roleName);
                }
            }

            if (user.getStatus().equals(-1)){
                item.put("status_description","已被拉黑");
            }
            if (user.getStatus().equals(0)){
                item.put("status_description","正常");
            }

            listResult.add(item);
        }

        result.put("list",listResult);

        return result;

    }


}
