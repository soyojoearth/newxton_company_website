package com.newxton.supercompanyapi;

import com.newxton.supercompanyapi.interceptor.NxtAdminInterceptor;
import com.newxton.supercompanyapi.interceptor.NxtAdminSupperPermissionInterceptor;
import com.newxton.supercompanyapi.interceptor.NxtAdminWritePermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
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

}
