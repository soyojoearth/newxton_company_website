package com.newxton.nxtframework.component;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/10/18
 * @address Shenzhen, China
 */
@Component
public class NxtRequestSelfApiComponent {

    @Value("${newxton.config.apiURL}")
    private String apiURL;

    /**
     * 发送POST表单数据到接口Path
     * @param path
     * @param params
     * @param header
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public Map<String,Object> postFormAndReturnMap(String path, Map<String,Object> params, Map<String,String> header) throws IOException, UnsupportedEncodingException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(apiURL+path);
//        httpPost.setHeader("Content-Type","multipart/form-data");

        if (header != null){
            Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> item = iterator.next();
                httpPost.setHeader(item.getKey(),item.getValue());
            }
        }

        if (params != null) {
            List<NameValuePair> nvpList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                nvpList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvpList, "UTF-8");
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();
        String resultPre = EntityUtils.toString(entity,"UTF-8");

//        Gson gson = new Gson();

        Map<String,Object> innerMap = JSON.parseObject(resultPre).getInnerMap();

//        Map<String,Object> mapData = gson.fromJson(resultPre, new TypeToken<Map<String,Object>>(){}.getType());

        return innerMap;

    }


}
