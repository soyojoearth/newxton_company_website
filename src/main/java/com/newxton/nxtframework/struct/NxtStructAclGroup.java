package com.newxton.nxtframework.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/13
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructAclGroup {

    public Long id;
    public String groupName;
    public String groupRemark;
    public List<Long> groupActionList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark;
    }

    public List<Long> getGroupActionList() {
        return groupActionList;
    }

    public void setGroupActionList(List<Long> groupActionList) {
        this.groupActionList = groupActionList;
    }

}
