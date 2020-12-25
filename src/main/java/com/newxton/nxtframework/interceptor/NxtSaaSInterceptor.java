package com.newxton.nxtframework.interceptor;

import com.newxton.nxtframework.component.NxtSaaSCoreComponent;
import com.newxton.nxtframework.schedule.NxtCronJobSaaSCore;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })//按需配置
public class NxtSaaSInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(NxtCronJobSaaSCore.class);

    @Resource
    private NxtSaaSCoreComponent nxtSaaSCoreComponent;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        String NxtTenantCondition;
        String NxtTenantInsertKey;
        String NxtTenantInsertValue;

        if (Thread.currentThread().getName().contains("pool")){
            //线程池，不分割租户空间
//            logger.info("线程："+Thread.currentThread().getName()+" 线程池不分割租户空间");
            NxtTenantCondition = "100 = 100";
            NxtTenantInsertKey = "";
            NxtTenantInsertValue = "";//线程池是不可以插进任何东西的，tenant_id字段不得null
        }
        else {
            //非线程池，分割租户空间
            Long tenantId = nxtSaaSCoreComponent.findTenantId();
            if (tenantId == null){
                //找不到租户id时，什么也不要查出来
                NxtTenantCondition = "1 = 2";
                //tenant_id字段不得null,故意插不进
                NxtTenantInsertKey = "";
                NxtTenantInsertValue = "";
            }
            else {
                NxtTenantCondition = "tenant_id = " + tenantId;
                NxtTenantInsertKey = "tenant_id,";
                NxtTenantInsertValue = tenantId + ",";
            }
        }

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        String newSql = sql.replace("@NxtTenantCondition@",NxtTenantCondition);

        newSql = newSql.replace("@NxtTenantInsertKey@",NxtTenantInsertKey);
        newSql = newSql.replace("@NxtTenantInsertValue@",NxtTenantInsertValue);

        //修改sql语句
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, newSql);

        return invocation.proceed();

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
