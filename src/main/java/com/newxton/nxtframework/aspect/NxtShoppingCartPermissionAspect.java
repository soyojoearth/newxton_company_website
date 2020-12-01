package com.newxton.nxtframework.aspect;

import com.newxton.nxtframework.component.NxtUtilComponent;
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
 * 购物车--如果已登录就检查一下，没登录就当作匿名访客
 */
@Aspect
@Component
@Order(1)
public class NxtShoppingCartPermissionAspect {

    private Logger logger = LoggerFactory.getLogger(NxtUserCheckTokenAspect.class);

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private NxtUtilComponent utilComponent;

    @Pointcut("execution(public * com.newxton.nxtframework.controller.api.front.shoppingcart..*.*(..))")
    public void pointcut() {
        //这个pointcut里面的Class都需要已登录才能执行
    }

    @Around("pointcut()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {

        String userId = request.getHeader("user_id");
        String token = request.getHeader("token");

        /**
         * 如果有userId就检查一下token是否有效；如果没有就不检查，当作匿名访客使用购物车
         */
        if (userId != null){
            if (utilComponent.isNumeric(userId) && token != null) {
                NxtUser user = nxtUserService.queryById(Long.valueOf(userId));
                if (user == null || !user.getToken().equals(token)) {
                    //用户不存在 或 未登录
                    return new NxtStructApiResult(41, "用户登录token错误，请重新登录或以匿名使用购物车");
                }
                if (user.getStatus().equals(-1)) {
                    //已被拉入黑名单
                    return new NxtStructApiResult(-1, "该用户已被禁止");
                }
            }
            else {
                return new NxtStructApiResult(41, "用户登录token错误，请重新登录或以匿名使用购物车");
            }
        }

        Object result = pjp.proceed();

        return result;

    }


}
