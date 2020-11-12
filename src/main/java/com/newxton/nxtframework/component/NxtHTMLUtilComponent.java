package com.newxton.nxtframework.component;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/28
 * @address Shenzhen, China
 */
@Component
public class NxtHTMLUtilComponent {

    @Resource
    private HttpServletRequest request;

    /**
     * 模拟浏览器抓取当前url页面的html，包含解析ajax
     * @param jsWaitTime
     * @return
     */
    public String parseAjaxHtmlWithSelfUrl(long jsWaitTime){
        /*方法一：自动解析ajax后丢给搜索引擎*/
        String url = "";
        url = request.getScheme()
                +"://" + request.getServerName()
                + ":" +request.getServerPort()
                + request.getServletPath();
        if (request.getQueryString() != null){
            url += "?" + request.getQueryString();
        }
        String html = getAjaxHtmlWithURL(url,jsWaitTime);
        return html;
    }

    /**
     * 模拟浏览器抓取url，并解析其中的ajax
     * @param url
     * @param jsWaitTime
     * @return
     */
    public String getAjaxHtmlWithURL(String url,long jsWaitTime){

        //新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);

        //当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(true);
        //是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setCssEnabled(true);
        //很重要，启用JS
        webClient.getOptions().setJavaScriptEnabled(true);
        //很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        HtmlPage page = null;

        try {
            //尝试加载网页
            page = webClient.getPage(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            webClient.close();
        }

        //异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
        webClient.waitForBackgroundJavaScript(jsWaitTime);

        //直接将加载完成的页面转换成xml格式的字符串
        String pageXml = page.asXml();

        return pageXml;

    }

}
