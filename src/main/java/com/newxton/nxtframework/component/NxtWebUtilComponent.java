package com.newxton.nxtframework.component;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/26
 * @address Shenzhen, China
 */
@Component
public class NxtWebUtilComponent {

    @Resource
    private HttpServletRequest request;

    /**
     * 判断访客是不是搜索引擎
     * @return
     */
    public boolean isSpider(){

        String userAgent = request.getHeader("User-Agent");

        Matcher m = Pattern.compile("(googlebot|baiduspider|bingbot|spider)").matcher(userAgent.toLowerCase());
        if (m.find()) {
            //是搜索引擎蜘蛛
            return true;
        }
        else {
            //普通访客
            return false;
        }
    }

}
