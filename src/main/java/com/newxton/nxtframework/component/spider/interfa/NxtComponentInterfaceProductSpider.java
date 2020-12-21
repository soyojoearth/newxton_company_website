package com.newxton.nxtframework.component.spider.interfa;

import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.struct.NxtStructProductSpiderResult;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/19
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public interface NxtComponentInterfaceProductSpider {

    /**
     * 判断url是不是本Spider负责的类型
     * @param url
     * @return
     */
    Boolean isMatchUrl(String url);

    /**
     * 根据url抓取第三方平台商品，并返回NxtStructProduct对象
     * @param url
     * @return
     * @throws NxtException
     */
    NxtStructProductSpiderResult catchProductFromUrl(String url) throws NxtException;

}
