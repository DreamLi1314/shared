package com.geoviswtx.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jack Li
 * @date 2022/5/25 上午11:49
 * @description
 */
@Slf4j
public class BaseJobHandler {

   private final RestTemplate restTemplate;
   private final RedisTemplate redisTemplate;

   public BaseJobHandler(RedisTemplate redisTemplate,
                         RestTemplate restTemplate)
   {
      this.redisTemplate = redisTemplate;
      this.restTemplate = restTemplate;
   }

}
