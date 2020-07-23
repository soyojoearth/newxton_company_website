package com.newxton.companywebsite;

import com.newxton.companywebsite.interceptor.NxtAdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
    public NxtAdminInterceptor permissionInterceptorAdminInterceptor() {
        return new NxtAdminInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        /*后台管理Api的token权限检查*/
        registry.addInterceptor(permissionInterceptorAdminInterceptor()).addPathPatterns("/api/admin/*").excludePathPatterns("/api/admin/login");


    }

}
