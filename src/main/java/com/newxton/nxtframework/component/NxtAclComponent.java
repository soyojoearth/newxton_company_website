package com.newxton.nxtframework.component;

import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/21
 * @address Shenzhen, China
 */
@Component
public class NxtAclComponent {

    private Logger logger = LoggerFactory.getLogger(NxtAclComponent.class);

    @Resource
    private NxtAclActionService nxtAclActionService;

    @Resource
    private NxtCronjobService nxtCronjobService;

    /**
     * 清理缓存
     * 由于实例可能由k8s部署多个，所以需要清除每个实例的缓存，就提交任务，分布式执行
     *
     */
    public void addJobForCleanCache(){
        NxtCronjob nxtCronjob = nxtCronjobService.queryByKeyForUpdate("cleanAclCache");
        if (nxtCronjob == null){
            nxtCronjob = new NxtCronjob();
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
            nxtCronjob.setJobStatus(1);
            nxtCronjob.setJobName("清理Acl缓存");
            nxtCronjob.setJobKey("cleanAclCache");
            nxtCronjobService.insert(nxtCronjob);
        }
        else {
            nxtCronjob.setJobStatus(1);
            nxtCronjob.setJobStatusDateline(System.currentTimeMillis());
            nxtCronjobService.update(nxtCronjob);
        }
    }

    /**
     * 直接清理本实例Acl缓存
     */
    @CacheEvict(cacheNames = {"getUserRoleGroupActionIdSet","getUserActionIdSet"},allEntries = true,beforeInvocation = false)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void cleanCache() {
        // 注解 @CacheEvict 就执行了清理，代码块里面什么也不用写
    }

    /**
     * ClassName -> ActionId 映射表
     * @return
     */
    @Cacheable("getMapClassNameToActionId")
    public Map<String,Long> getMapClassNameToActionId(){
        List<NxtAclAction> list = nxtAclActionService.queryAll(new NxtAclAction());
        Map<String,Long> mapClassNameToActionId = new HashMap<>();
        for (NxtAclAction nxtAclAction : list) {
            mapClassNameToActionId.put(nxtAclAction.getActionClass(),nxtAclAction.getId());
        }
        logger.info("getMapClassNameToActionId");
        return mapClassNameToActionId;
    }

    @Resource
    private NxtAclUserActionService nxtAclUserActionService;

    /**
     * 用户直接关联的权限Action
     * @param userId
     * @return
     */
    @Cacheable("getUserActionIdSet")
    public Set<Long> getUserActionIdSet(Long userId){
        Set<Long> userActionSet = new HashSet<>();
        NxtAclUserAction nxtAclUserAction = new NxtAclUserAction();
        nxtAclUserAction.setUserId(userId);
        List<NxtAclUserAction> list = nxtAclUserActionService.queryAll(nxtAclUserAction);
        for (NxtAclUserAction item : list) {
                userActionSet.add(item.getActionId());
        }
        logger.info("getUserActionIdSet:{}",userId);
        return userActionSet;
    }

    @Resource
    private NxtAclRoleGroupService nxtAclRoleGroupService;

    /**
     * 用户通过角色关联的权限
     * @param userId
     * @return
     */
    @Cacheable("getUserRoleGroupActionIdSet")
    public Set<Long> getUserRoleGroupActionIdSet(Long userId){
        Set<Long> userActionSet = new HashSet<>();
        /*再间接通过角色、权限组查询用户拥有的权限*/
        //角色 -> 权限组
        List<NxtAclRoleGroup> listAclRoleGroup = nxtAclRoleGroupService.queryAll(new NxtAclRoleGroup());
        //权限组 -> 权限ID
        Map<Long,Set<Long>> mapGroupIdToActionIdSet = getMapGroupIdToActionIdSet();
        //1、查询用户的角色
        List<NxtAclUserRole> listAclUserRole = getListAclUserRole(userId);
        for (NxtAclUserRole nxtAclUserRole : listAclUserRole) {
            //2、查询该role关联的group
            Long roleId = nxtAclUserRole.getRoleId();
            for (NxtAclRoleGroup nxtAclRoleGroup : listAclRoleGroup) {
                if (nxtAclRoleGroup.getRoleId().equals(roleId)) {
                    Long groupId = nxtAclRoleGroup.getGroupId();
                    //3、查询该groupId关联的action
                    userActionSet.addAll(mapGroupIdToActionIdSet.get(groupId));
                }
            }
        }
        logger.info("getUserRoleGroupActionIdSet:{}",userId);
        return userActionSet;
    }

    @Resource
    private NxtAclGroupActionService nxtAclGroupActionService;

    /**
     * 取 权限组 -> 权限ID集合 映射表
     * @return
     */
    private Map<Long,Set<Long>> getMapGroupIdToActionIdSet(){
        Map<Long,Set<Long>> mapGroupIdToActionIdSet = new HashMap<>();
        List<NxtAclGroupAction> list = nxtAclGroupActionService.queryAll(new NxtAclGroupAction());
        for (NxtAclGroupAction nxtAclGroupAction : list) {
            if (!mapGroupIdToActionIdSet.containsKey(nxtAclGroupAction.getGroupId())){
                mapGroupIdToActionIdSet.put(nxtAclGroupAction.getGroupId(),new HashSet<>());
            }
            mapGroupIdToActionIdSet.get(nxtAclGroupAction.getGroupId()).add(nxtAclGroupAction.getActionId());
        }
        logger.info("getMapGroupIdToActionIdSet");
        return mapGroupIdToActionIdSet;
    }


    @Resource
    private NxtAclUserRoleService nxtAclUserRoleService;

    /**
     * 取某个用户的角色列表
     * @param userId
     * @return
     */
    private List<NxtAclUserRole> getListAclUserRole(Long userId){
        NxtAclUserRole nxtAclUserRole = new NxtAclUserRole();
        nxtAclUserRole.setUserId(userId);
        List<NxtAclUserRole> list = nxtAclUserRoleService.queryAll(nxtAclUserRole);
        return list;
    }

}
