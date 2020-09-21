package com.newxton.supercompanyapi.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.newxton.supercompanyapi.entity.NxtUser;
import com.newxton.supercompanyapi.service.NxtUserService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/23
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
public class NxtAdminSupperPermissionInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private NxtUserService nxtUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String,Object> errorResult = new HashMap<>();
        errorResult.put("status",43);
        errorResult.put("message","权限不够:需超级管理员权限");

        String user_id = request.getHeader("user_id");
        NxtUser user = nxtUserService.queryById(Long.valueOf(user_id));

        /*NxtAdminInterceptor已经检查过登录状态了，这里就不再重复检查*/

        if (!user.getType().equals(1)){
            //检查不通过
            sendJsonMessage(response,errorResult);
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        super.afterCompletion(request, response, handler, ex);
    }



    /**
     * 将某个对象转换成json格式并发送到客户端
     * @param response
     * @param obj
     * @throws Exception
     */
    public void sendJsonMessage(HttpServletResponse response, Object obj) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat));
        writer.close();
        response.flushBuffer();
    }

}
