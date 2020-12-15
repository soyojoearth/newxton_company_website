package com.newxton.nxtframework.component;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/9/6
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtUtilComponent {

    /**
     * 获取随机字符串
     * @param length
     * @return
     */
    public String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_";
        Random random=new Random();
        StringBuffer buffet=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(str.length()-1);
            buffet.append(str.charAt(number));
        }
        return buffet.toString();
    }

    public boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }

}
