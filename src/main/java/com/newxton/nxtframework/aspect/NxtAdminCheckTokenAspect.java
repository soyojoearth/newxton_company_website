package com.newxton.nxtframework.aspect;

import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtUserService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/21
 * @address Shenzhen, China
 *
 * 超级管理员权限验证
 *
 */
@Aspect
@Component
@Order(1)
public class NxtAdminCheckTokenAspect {

    private Logger logger = LoggerFactory.getLogger(NxtAdminCheckTokenAspect.class);

    @Resource
    private NxtUserService nxtUserService;

    @Pointcut("execution(public * com.newxton.nxtframework.controller.api.admin..*.*(..))")
    public void pointcut() {
        //这个pointcut里面的Class都至少需要管理员登录才能执行
    }

    @Around("pointcut()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {

        String objectName = pjp.getTarget().getClass().getSimpleName();

        if (objectName.contains("Login") || objectName.contains("Logout")){
            //除了login和logout以外，其它的都必须先登录
            return pjp.proceed();
        }

        Map<String,Object> errorResult = new HashMap<>();
        errorResult.put("status",41);
        errorResult.put("message","未登录");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String user_id = request.getHeader("user_id");
        String token = request.getHeader("token");

        if (user_id == null || token == null || token.isEmpty() || user_id.isEmpty()){
            return errorResult;
        }

        NxtUser user = nxtUserService.queryById(Long.valueOf(user_id));
        if (user == null || !user.getToken().equals(token)){
            //用户不存在 或 未登录
            return errorResult;
        }

        if (user.getStatus().equals(-1)){
            //已被拉入黑名单
            errorResult.put("status",-1);
            errorResult.put("message","已被禁止");
            return errorResult;
        }

        Object result = pjp.proceed();

        return result;

    }

}
