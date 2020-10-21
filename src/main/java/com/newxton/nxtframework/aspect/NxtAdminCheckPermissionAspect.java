package com.newxton.nxtframework.aspect;

import com.newxton.nxtframework.component.NxtAclComponent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/21
 * @address Shenzhen, China
 */
@Aspect
@Component
@Order(2)
public class NxtAdminCheckPermissionAspect {

    private Logger logger = LoggerFactory.getLogger(NxtAdminCheckPermissionAspect.class);

    @Resource
    private NxtAclComponent nxtAclComponent;

    @Pointcut("execution(public * com.newxton.nxtframework.controller.api.admin..*.*(..))")
    public void pointcut() {
        //这个pointcut里面的Class都需要超级管理员权限才能执行
    }

    @Around("pointcut()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {

        String objectName = pjp.getTarget().getClass().getSimpleName();

        if (objectName.contains("Login") || objectName.contains("Logout")){
            //除了login和logout以外，其它的都必须先登录
            return pjp.proceed();
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String user_id = request.getHeader("user_id");
        Long userId = Long.valueOf(user_id);

        boolean hasPermission = false;

        //查询ActionId
        Map<String,Long> mapClassNameToActionId = nxtAclComponent.getMapClassNameToActionId();
        if (mapClassNameToActionId.containsKey(objectName)){
            Long actionId = mapClassNameToActionId.get(objectName);
            /*先直接查询用户关联的权限*/
            Set<Long> userActionSet = nxtAclComponent.getUserActionIdSet(userId);
            if (userActionSet.contains(actionId)){
                //有权
                hasPermission = true;
            }
            /*再间接通过角色、权限组查询用户拥有的权限*/
            Set<Long> userRoleGroupActionSet = nxtAclComponent.getUserRoleGroupActionIdSet(userId);
            if (userRoleGroupActionSet.contains(actionId)){
                //有权
                hasPermission = true;
            }
        }

        if (hasPermission){
            Object result = pjp.proceed();
            return result;
        }
        else {
            Map<String,Object> errorResult = new HashMap<>();
            errorResult.put("status",43);
            errorResult.put("message","权限不够");
            return errorResult;
        }

    }

}
