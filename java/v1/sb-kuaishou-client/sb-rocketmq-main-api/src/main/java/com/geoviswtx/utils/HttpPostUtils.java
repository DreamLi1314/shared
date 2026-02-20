package com.geoviswtx.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpPostUtils {

    public static <T> T post(String url, Map<String, String> headerMap, Object body, Class<T> clazz) throws Exception {
        return post(url,headerMap, body,clazz, null);
    }

    public static <T> T post(String url, Map<String, String> headerMap, Object body, Class<T> clazz,
                             String contentType)
            throws Exception
    {
        if(headerMap == null) {
            headerMap = new HashMap<>();
        }

        HttpClient httpClient = HttpClients.createDefault();
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("query request for: {}.", url);

        // 创建HttpPost对象，并设置URL
        HttpPost httpPost = new HttpPost(url);

        if(contentType == null) {
            httpPost.setHeader("Content-Type", "application/json");
        }
        else {
            httpPost.setHeader("Content-Type", contentType);
        }

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

    public static <T> T postForm(String url, Map<String, String> formMap, Class<T> clazz)
            throws Exception
    {
        HttpClient httpClient = HttpClients.createDefault();
        ObjectMapper objectMapper = new ObjectMapper();

        List<NameValuePair> params = new ArrayList<>();

        for (Map.Entry<String, String> entry : formMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        // 构建请求体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");

        // 创建POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(formEntity);

        // 发送请求
        HttpResponse response = httpClient.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 请求成功
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);
            // 处理响应体
            return objectMapper.readValue(responseBody, clazz);
        }
        else {
            // 请求失败
            // 处理错误
            log.error("请求错误！url: {}", url);
            throw new RuntimeException("请求错误！" + url);
        }
    }

}
