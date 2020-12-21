package com.newxton.nxtframework.component.spider;

import com.newxton.nxtframework.component.spider.interfa.NxtComponentInterfaceProductSpider;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.struct.NxtStructProductSku;
import com.newxton.nxtframework.struct.NxtStructProductSkuValue;
import com.newxton.nxtframework.struct.NxtStructProductSpiderResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/19
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 抓取京东商品
 */
@Component
public class NxtProductSpiderJDComponent implements NxtComponentInterfaceProductSpider {

    /**
     * 判断url是不是本Spider负责的类型
     * @param url
     * @return
     */
    @Override
    public Boolean isMatchUrl(String url){
        if (url.toLowerCase().contains("jd.com")){
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
         * 以下是基础示例代码和说明
         */
        //抓取到产品名称
        String productName = null;

        //抓取到的产品价格
        Float price = null;

        //抓取到全部n张产品主图
        List<String> pictureList = new ArrayList<>();

        //抓取到所有产品规格
        List<NxtStructProductSku> skuList = new ArrayList<>();

        String productDescription = null;


        //这里开始，抓取以上所需的5个数据，根据下面的代码那样赋值
        try {
            Thread.sleep(3000);//写完后删除这里
        }
        catch (Exception e){

        }

        /**
         * 抓取到的产品名称
         */
        productName = "测试抓取产品"+url;
        /**
         * 抓取到的价格
         */
        price = 168.88F;
        /**
         * 抓取到N张主图，有多少加多少
         */
        pictureList.add("https://cbu01.alicdn.com/img/ibank/2020/486/146/15432641684_1968605068.jpg");
        pictureList.add("https://cbu01.alicdn.com/img/ibank/2019/852/428/11335824258_1968605068.jpg");
        pictureList.add("https://cbu01.alicdn.com/img/ibank/2019/127/197/11335791721_1968605068.jpg");
        pictureList.add("https://cbu01.alicdn.com/img/ibank/2019/309/797/11335797903_1968605068.jpg");

        /**
         * 抓取到规格1（如果有）
         */
        NxtStructProductSku productSku1 = new NxtStructProductSku("颜色");
        productSku1.addSkuValue("红色");
        productSku1.addSkuValue("蓝色");
        skuList.add(productSku1);

        /**
         * 抓取到规格2（如果有）
         */
        NxtStructProductSku productSku2 = new NxtStructProductSku("内存");
        productSku2.addSkuValue("128G");
        productSku2.addSkuValue("256G");
        skuList.add(productSku2);

        /**
         * 产品描述(HTML)
         */
        productDescription = "这是测试产品的<b>描述</b>.";


        /**
         * 10秒内要返回结果，否则 throw new NxtException("网络超时，抓取失败");
         */

        nxtStructProductSpiderResult.setProductName(productName);
        nxtStructProductSpiderResult.setPrice(price);
        nxtStructProductSpiderResult.setPictureList(pictureList);
        nxtStructProductSpiderResult.setSkuList(skuList);
        nxtStructProductSpiderResult.setProductDescription(productDescription);


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
