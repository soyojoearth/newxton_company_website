package com.newxton.nxtframework;

import com.newxton.nxtframework.interceptor.NxtAdminInterceptor;
import com.newxton.nxtframework.interceptor.NxtAdminSupperPermissionInterceptor;
import com.newxton.nxtframework.interceptor.NxtAdminWritePermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 */
@Configuration
public class NxtWebConfig implements WebMvcConfigurer {

    @Bean
    public NxtAdminInterceptor getAdminInterceptor() {
        return new NxtAdminInterceptor();
    }

    @Bean
    public NxtAdminSupperPermissionInterceptor getAdminSupperPermissionInterceptor() {
        return new NxtAdminSupperPermissionInterceptor();
    }

    @Bean
    public NxtAdminWritePermissionInterceptor getAdminWritePermissionInterceptor() {
        return new NxtAdminWritePermissionInterceptor();
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //尾部斜杠
        configurer.setUseTrailingSlashMatch(false);/*严格分辨尾部斜杠：有斜杠和没斜杠不属于同一路径*/
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(deviceResolverHandlerInterceptor());

        /*管理后台需要已登录才能操作的api*/
        registry.addInterceptor(getAdminInterceptor())
                .addPathPatterns("/api/admin/*")
                .addPathPatterns("/api/admin/*/*")
                .addPathPatterns("/api/admin/*/*/*")
                .excludePathPatterns("/api/admin/login")
                .excludePathPatterns("/api/admin/logout");
        /*以上这个Interceptor一定要放在最前面，因为下面的几个Intercepter不再检查登录状态*/

        /*需要超级管理员权限的api*/
        registry.addInterceptor(getAdminSupperPermissionInterceptor())
                .addPathPatterns("/api/admin/reset_user_pwd")
                .addPathPatterns("/api/admin/block_user")
                .addPathPatterns("/api/admin/reset_user_type")
                .addPathPatterns("/api/admin/create_user")
                .addPathPatterns("/api/admin/setting_save")
        ;

        /*只需要编辑管理权限的api*/
        registry.addInterceptor(getAdminWritePermissionInterceptor())
                .addPathPatterns("/api/admin/*/create")
                .addPathPatterns("/api/admin/*/update")
                .addPathPatterns("/api/admin/*/delete")
                .addPathPatterns("/api/admin/*/recommend")
                .addPathPatterns("/api/admin/*/order_swap")
        ;

        /*仅需要只读管理权限的api*/
        //剩下的api就只读了

    }


    @Bean
    public DeviceResolverHandlerInterceptor
    deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    @Bean
    public DeviceHandlerMethodArgumentResolver
    deviceHandlerMethodArgumentResolver() {
        return new DeviceHandlerMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(deviceHandlerMethodArgumentResolver());
    }

}
