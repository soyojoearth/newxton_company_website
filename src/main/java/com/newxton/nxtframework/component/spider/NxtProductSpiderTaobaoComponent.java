package com.newxton.nxtframework.component.spider;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.newxton.nxtframework.component.spider.interfa.NxtComponentInterfaceProductSpider;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.struct.NxtStructProductSku;
import com.newxton.nxtframework.struct.NxtStructProductSpiderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/19
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 抓取淘宝商品
 */
@Component
public class NxtProductSpiderTaobaoComponent implements NxtComponentInterfaceProductSpider {

    private Logger logger = LoggerFactory.getLogger(NxtProductSpiderTaobaoComponent.class);

    /**
     * 判断url是不是本Spider负责的类型
     * @param url
     * @return
     */
    @Override
    public Boolean isMatchUrl(String url){
        if (url.toLowerCase().contains("taobao.com")){
            return true;
        }
        return false;
    }

    //抓取到产品名称
    private String productName = null;

    //抓取到的产品价格
    private Float price = null;

    //抓取到全部n张产品主图
    private List<String> pictureList = new ArrayList<>();

    //抓取到所有产品规格
    private List<NxtStructProductSku> skuList = new ArrayList<>();

    //抓取到所有产品详情
    private String productDescription = null;

    /**
     * 抓取第三方平台商品，并返回NxtStructProductSpiderResult对象
     * @param url
     * @return
     * @throws NxtException
     */
    @Override
    public NxtStructProductSpiderResult catchProductFromUrl(String url) throws NxtException {

        if (true) {
            throw new NxtException("该链接类型抓取，需联系我们二次开发");
        }

        /**
         * 在这里编写代码抓取商品，然后返回NxtStructProductSpiderResult对象
         */
        NxtStructProductSpiderResult nxtStructProductSpiderResult = new NxtStructProductSpiderResult();

        /**
         * 以下是基础示例代码和说明
         */
        //抓取到产品名称
        productName = null;

        //抓取到的产品价格
        price = null;

        //抓取到全部n张产品主图
        pictureList = new ArrayList<>();

        //抓取到所有产品规格
        skuList = new ArrayList<>();

        //抓取到所有产品详情
        productDescription = null;

        //假数据 赋值【仅供参考数据格式】
        this.processFake(url);

        //真正抓取过程，请写入这个方法里面
//        this.processWork(url);

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

    /**
     * 假数据 赋值【仅供参考数据格式】
     * @param url
     * @throws NxtException
     */
    private void processFake(String url) throws NxtException{

        try {
            Thread.sleep(3000);
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

    }

    /**
     * 抓取页面与元素分析
     * @param url
     * @throws NxtException
     */
    private void processWork(String url) throws NxtException{

        /**
         * 使用html unit进行抓取，可以配合jsoup分析数据
         */

        WebClient webClient=new WebClient(BrowserVersion.CHROME); // 实例化Web客户端端
        webClient.getOptions().setCssEnabled(false); // 取消css支持
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setScreenWidth(1920);
        webClient.getOptions().setScreenHeight(5000);//超高的模拟屏幕，直接触发下拉加载
        try {

            HtmlPage page = webClient.getPage(url); // 解析获取页面
            webClient.waitForBackgroundJavaScript(10000);

            //此处开始分析页面数据、赋值


        }
        catch (Exception e){
            logger.warn("Spider抓取url错误:"+e.getMessage());
        }
        finally {
            webClient.close();
        }

    }

}
