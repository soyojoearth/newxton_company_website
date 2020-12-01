package com.newxton.nxtframework.aspect;

import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructApiResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/24
 * @address Shenzhen, China
 */
@Aspect
@Component
@Order(1)
public class NxtUserCheckTokenAspect {

    private Logger logger = LoggerFactory.getLogger(NxtUserCheckTokenAspect.class);

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private HttpServletRequest request;

    @Pointcut("execution(public * com.newxton.nxtframework.controller.api.front.ucenter..*.*(..)) || execution(public * com.newxton.nxtframework.controller.api.front.orderform..*.*(..))  || execution(public * com.newxton.nxtframework.controller.api.front.commission..*.*(..))")
    public void pointcut() {
        //这个pointcut里面的Class都需要已登录才能执行
    }

    @Around("pointcut()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {

        String user_id = request.getHeader("user_id");
        String token = request.getHeader("token");

        if (user_id == null || token == null || token.isEmpty() || user_id.isEmpty()){
            return new NxtStructApiResult(41,"未登录");
        }

        NxtUser user = nxtUserService.queryById(Long.valueOf(user_id));
        if (user == null || !user.getToken().equals(token)){
            //用户不存在 或 未登录
            return new NxtStructApiResult(41,"未登录");
        }

        if (user.getStatus().equals(-1)){
            //已被拉入黑名单
            return new NxtStructApiResult(-1,"已被禁止");
        }

        Object result = pjp.proceed();

        return result;

    }


}
