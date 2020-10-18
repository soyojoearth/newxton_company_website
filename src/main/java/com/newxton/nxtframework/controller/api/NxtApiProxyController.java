package com.newxton.nxtframework.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.newxton.nxtframework.component.NxtRequestSelfApiComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/18
 * @address Shenzhen, China
 *
 * 一次性接收客户端发来的包含了多个Api请求的JSON，然后替客户端一次性请求这多个Api，最后把结果一次性打包返回客户端。
 * 例如，客户端向本接口发了这样的数据：
 * [{
 *     "path": "/api/news/detail",
 *     "method": "POST",
 *     "content-type": "multipart/form-data",
 *     "params": {
 *         "id": 24
 *     }
 * }, {
 *     "path": "/api/normal_news_list",
 *     "method": "POST",
 *     "content-type": "multipart/form-data",
 *     "params": {
 *         "root_category_id": 2,
 *         "offset": 0,
 *         "limit": 4
 *     }
 * }
 * ]
 * 这就表示，客户端委托本接口一次性请求并返回这两个接口的数据(一篇新闻内容+N篇新闻推荐)，返回的数据如下格式：
 * {
 *     "/api/normal_news_list": {
 *         "request": {
 *             "path": "/api/normal_news_list",
 *             "method": "POST",
 *             "content-type": "multipart/form-data",
 *             "params": {
 *                 "offset": 0,
 *                 "limit": 4,
 *                 "root_category_id": 2
 *             }
 *         },
 *         "response": {
 *             "data": [{
 *                 "picUrl": "。。。。。。。。。。",
 *                 "id": 24,
 *                 "text": "。。。。。。",
 *                 "time": "2020-08-03",
 *                 "title": "。。。。。。。。。。。。。。。。。",
 *                 "categoryName": "。。。。。"
 *             },{
 *                 "picUrl": "。。。。。。。。。。",
 *                 "id": 24,
 *                 "text": "。。。。。。",
 *                 "time": "2020-08-03",
 *                 "title": "。。。。。。。。。。。。。。。。。",
 *                 "categoryName": "。。。。。"
 *             }],
 *             "message": "",
 *             "status": 0
 *         }
 *     },
 *     "/api/news/detail": {
 *         "request": {
 *             "path": "/api/news/detail",
 *             "method": "POST",
 *             "content-type": "multipart/form-data",
 *             "params": {
 *                 "id": 24
 *             }
 *         },
 *         "response": {
 *             "data": {
 *                 "datelineUpdate": 1596532686625,
 *                 "datelineUpdateReadable": "2020-08-04 17:18:06",
 *                 "isRecommend": 1596418999575,
 *                 "datelineCreateReadable": "2020-08-03 09:43:19",
 *                 "id": 24,
 *                 "contentTitle": "。。。。。。。",
 *                 "contentDetail": "。。。。",
 *                 "categoryId": 2,
 *                 "datelineCreate": 1596418999575
 *             },
 *             "message": "",
 *             "status": 0
 *         }
 *     }
 * }
 *
 * 如此一来，客户端在很多情况下本来要调用多次接口，现在只要合并一次就够了，省下了一些开销。
 */
@RestController
public class NxtApiProxyController {

    private Logger logger = LoggerFactory.getLogger(NxtApiProxyController.class);

    @Resource
    NxtRequestSelfApiComponent nxtRequestSelfApiComponent;

    @RequestMapping("/api/proxy")
    public Map<String,Object> index(@RequestBody String requestBody) throws InterruptedException {
        Map<String,Object> result = new HashMap<>();
        JSONArray jsonArray = JSON.parseArray(requestBody);
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String path = jsonObject.getString("path");
            String method = jsonObject.getString("method");
            String contentType = jsonObject.getString("content-type");
            Map<String,Object> params = jsonObject.getJSONObject("params").getInnerMap();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, Object> itemResponse = new HashMap<>();
                        Map<String, Object> itemRequest = new HashMap<>();
                        itemRequest.put("path",path);
                        itemRequest.put("method",method);
                        itemRequest.put("content-type",contentType);
                        itemRequest.put("params",params);
                        if (method.toUpperCase().equals("POST") && contentType.toLowerCase().equals("multipart/form-data")) {
                            itemResponse = nxtRequestSelfApiComponent.postFormAndReturnMap(path, params, null);
                        }
                        else {
                            itemResponse.put("status",11);
                            itemResponse.put("message","接口 method 或 content-type 不正确");
                        }
                        Map<String, Object> itemResult = new HashMap<>();
                        itemResult.put("request",itemRequest);
                        itemResult.put("response",itemResponse);
                        result.put(path,itemResult);
                    } catch (Exception e) {
                        logger.error(e.getStackTrace().toString());
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return result;
    }

}
