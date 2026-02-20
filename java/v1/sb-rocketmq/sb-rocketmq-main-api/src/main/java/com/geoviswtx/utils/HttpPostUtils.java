package com.geoviswtx.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Map;

@Slf4j
public class HttpPostUtils {

    public static <T> T post(String url, Map<String, String> headerMap, Object body, Class<T> clazz) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("query request for: {}.", url);

        // 创建HttpPost对象，并设置URL
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");

        // 设置请求头，如果需要的话
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }

        // 设置请求体，这里使用JSON格式的请求体
        String requestBody = objectMapper.writeValueAsString(body);
        StringEntity requestEntity = new StringEntity(requestBody);
        httpPost.setEntity(requestEntity);

        // 发送POST请求
        HttpResponse response = httpClient.execute(httpPost);

        // 获取响应实体
        HttpEntity responseEntity = response.getEntity();

        // 将响应实体转换为字符串
        String responseString = EntityUtils.toString(responseEntity);

        return objectMapper.readValue(responseString, clazz);
    }

}
