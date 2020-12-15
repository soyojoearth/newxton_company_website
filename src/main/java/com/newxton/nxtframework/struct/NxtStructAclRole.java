package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/13
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructAclRole {

    public Long id;
    public String roleName;
    public String roleRemark;
    public List<Long> roleGroupList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }

    public List<Long> getRoleGroupList() {
        return roleGroupList;
    }

    public void setRoleGroupList(List<Long> roleGroupList) {
        this.roleGroupList = roleGroupList;
    }

}
