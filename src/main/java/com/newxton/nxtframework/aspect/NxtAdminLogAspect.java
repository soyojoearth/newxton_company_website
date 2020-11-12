package com.newxton.nxtframework.aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


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



}
