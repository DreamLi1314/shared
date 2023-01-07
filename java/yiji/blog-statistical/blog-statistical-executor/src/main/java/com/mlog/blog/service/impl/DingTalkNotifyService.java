package com.mlog.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jack Li
 * @date 2022/2/7 11:42 下午
 * @description
 */
@Service
public class DingTalkNotifyService {

   @Value("${mlog.dingtalk.webhook.url}")
   private String url;

   @Value("${mlog.dingtalk.webhook.keyword}")
   private String keyword;

   private final RestTemplate restTemplate;

   public DingTalkNotifyService(RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
   }

   public ResponseEntity<JSONObject> notifyText(String content) {
      content = "{'msgtype': 'text','text': {'content':'" + keyword + "" + content + "'}}";

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
      HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);

      return restTemplate.postForEntity(url, httpEntity, JSONObject.class);
   }

}
