package com.geoviswtx;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

public class SimpleTests {

    @Test
    void test() throws Exception {
        long unixTimestamp = Instant.now().getEpochSecond();
        System.out.println(unixTimestamp);

        HttpClient httpClient = HttpClients.createDefault();

        String url = "https://test.y.qq.com/common/api/msg_open/mock/get_code";

        // 创建HttpPost对象，并设置URL
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头，如果需要的话
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("x-tme-appid", "1047");
        httpPost.setHeader("x-tme-timestamp", "1706005375");
        httpPost.setHeader("x-tme-signature-method", "HMAC-SHA256");
        httpPost.setHeader("x-tme-signature-version", "1.0");
        httpPost.setHeader("x-tme-signature", "5724deead1cd4c8ea501ff6930a9f473560a96542f2e4ac51aa08b64150094e6");

        // 设置请求体，这里使用JSON格式的请求体
        String requestBody = "{\n" +
                "    \"game_id\": 1047001,\n" +
                "    \"app\": \"mlive\",\n" +
                "    \"room_id\": 9,\n" +
                "    \"code\": \"mlive_JSIiJMJ5\"\n" +
                "}";
        StringEntity requestEntity = new StringEntity(requestBody);
        httpPost.setEntity(requestEntity);

        // 发送POST请求
        HttpResponse response = httpClient.execute(httpPost);

        // 获取响应实体
        HttpEntity responseEntity = response.getEntity();

        // 将响应实体转换为字符串
        String responseString = EntityUtils.toString(responseEntity);

        System.out.println(responseString);
    }

}
