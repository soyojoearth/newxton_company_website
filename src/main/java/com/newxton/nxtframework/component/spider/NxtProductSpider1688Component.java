package com.newxton.nxtframework.component.spider;

import com.newxton.nxtframework.component.spider.interfa.NxtComponentInterfaceProductSpider;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.struct.NxtStructProductSku;
import com.newxton.nxtframework.struct.NxtStructProductSpiderResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/19
 * @address Shenzhen, China
 * @github NxtFramework
 * 抓取1688商品
 */
@Component
public class NxtProductSpider1688Component implements NxtComponentInterfaceProductSpider {

    /**
     * 判断url是不是本Spider负责的类型
     * @param url
     * @return
     */
    @Override
    public Boolean isMatchUrl(String url){
        if (url.toLowerCase().contains("1688.com")){
            return true;
        }
        return false;
    }

    /**
     * 抓取第三方平台商品，并返回NxtStructProductSpiderResult对象
     * @param url
     * @return
     * @throws NxtException
     */
    @Override
    public NxtStructProductSpiderResult catchProductFromUrl(String url) throws NxtException {

        /**
         * 在这里编写代码抓取商品，然后返回NxtStructProductSpiderResult对象
         */
        NxtStructProductSpiderResult nxtStructProductSpiderResult = new NxtStructProductSpiderResult();

        /**
         * 以下是基础代码和说明
         */
        //抓取到产品名称
        String productName = null;

        //抓取到的产品价格
        Float price = null;

        //抓取到全部n张产品主图
        List<String> pictureList = new ArrayList<>();

        //抓取到所有产品规格
        List<NxtStructProductSku> skuList = new ArrayList<>();


        //这里开始，抓取数据，把对应数据赋值给 productName、price，把抓取到的全部主图加入pictureList
        //写代码......




        // 把抓取到的全部规格组成若干对象NxtStructProductSku加入skuList
        // NxtStructProductSku这个对象及子对象里面的id和skuId都不需要set，其它都需要set
        //写代码......

        /**
         * 10秒内要返回结果，否则 throw new NxtException("网络超时，抓取失败");
         */

        nxtStructProductSpiderResult.setProductName(productName);
        nxtStructProductSpiderResult.setPrice(price);
        nxtStructProductSpiderResult.setPictureList(pictureList);
        nxtStructProductSpiderResult.setSkuList(skuList);


        return nxtStructProductSpiderResult;

    }

    /**
     * 所有要用到到函数、方法都写到这个class里面，
     * pom.xml已经 配置 htmlunit、jsoup两个组件，
     * htmlunit可以解析动态javascript结果，jsoup很方便解析抓取html文件
     * 10秒内要返回结果，否则 throw new NxtException("网络超时，抓取失败");
     */

    //写代码......


}
