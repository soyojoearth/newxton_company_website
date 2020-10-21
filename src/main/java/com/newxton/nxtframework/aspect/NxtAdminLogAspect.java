package com.newxton.nxtframework.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/20
 * @address Shenzhen, China
 *
 * 记录管理员的操作日志
 *
 */
@Aspect
@Component
@Order(999)
public class NxtAdminLogAspect {

    private Logger logger = LoggerFactory.getLogger(NxtAdminLogAspect.class);

    @Pointcut("execution(public * com.newxton.nxtframework.controller.api.admin..*.*(..))")
    public void pointcut() {
    }

    @Pointcut("execution(public * com.newxton.nxtframework.controller.api.front..*.*(..))")
    public void pointcutTest() {
    }

//    @Before("pointcutTest()")
//    public void beforeMethod(JoinPoint joinPoint) throws Throwable {
//        Object args = joinPoint.getArgs();
//        String objectName = joinPoint.getTarget().getClass().getSimpleName();
//        logger.info("对象 ：{} 执行之钱 ", objectName);
//        String methodName = joinPoint.getSignature().getName();
//        logger.info("方法 ：{} 执行之钱 ", methodName);
//    }

//    @After("pointcutTest()")
//    public void afterMethod(JoinPoint joinPoint) throws Throwable {
//        String objectName = joinPoint.getTarget().getClass().getSimpleName();
//        logger.info("对象 ：{} 执行之后 ", objectName);
//        String methodName = joinPoint.getSignature().getName();
//        logger.info("方法 ：{} 执行之后 ", methodName);
//    }
//
//    @Around("pointcut()")
//    public Object zaroundMethod1(ProceedingJoinPoint pjp) throws Throwable {
//
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//
//        String user_id = request.getHeader("user_id");
//
//        String token = request.getHeader("token");
//
//        Object args = pjp.getArgs();
//        String objectName = pjp.getTarget().getClass().getSimpleName();
//        logger.info("2对象 ：{} 执行之前 ", objectName);
//        String methodName = pjp.getSignature().getName();
//        logger.info("2方法 ：{} 执行之前 ", methodName);
//        Object result = pjp.proceed();
//        logger.info("2方法 ：{} 执行之后 ,返回值:{}", methodName, JSON.toJSONString(result));
//        return result;
//    }

}
