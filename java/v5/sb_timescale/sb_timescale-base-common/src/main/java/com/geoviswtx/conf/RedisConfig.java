/*
 * Copyright (c) 2020, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to JavaFamily Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */
package com.geoviswtx.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.io.Serializable;
import java.time.Duration;

/**
 * @author Jack Li
 * @date 2022/7/28 上午11:23
 * @description Redis 配置
 */
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class RedisConfig {
   @Autowired
   CacheProperties cacheProperties;
   @Bean("redisTemplate")
   public RedisTemplate<String, Serializable> redisTemplate(
      RedisConnectionFactory redisConnectionFactory)
   {
      RedisTemplate<String, Serializable> template = new RedisTemplate<>();
      template.setConnectionFactory(redisConnectionFactory);

/*      Jackson2JsonRedisSerializer<Serializable> jackson2JsonRedisSerializer
         = new Jackson2JsonRedisSerializer<>(Serializable.class);
      ObjectMapper om = new ObjectMapper();
      om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
      om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
      jackson2JsonRedisSerializer.setObjectMapper(om);*/

      GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

      StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
      // key using StringSerializer
      template.setKeySerializer(stringRedisSerializer);
      // key of hash using StringSerializer
      template.setHashKeySerializer(stringRedisSerializer);

      // value using jackson serializer
      template.setValueSerializer(serializer);
      template.setHashValueSerializer(serializer);

      template.afterPropertiesSet();

      return template;
   }

   @Bean
   public RedisCacheKeyGenerator redisCacheKeyGenerator() {
      return new RedisCacheKeyGenerator();
   }

   /***
    *  默认无限时间
    * @param factory
    * @return {@link CacheManager}
    */
   @Bean
   @Primary
   public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisConnectionFactory factory) {
      RedisCacheConfiguration config = instanceConfig(null);

      return RedisCacheManager.builder(factory)
              .cacheDefaults(config)
              .transactionAware()
              .build();
   }

   /**
    * 过期时间设置为1天
    *
    * @param connectionFactory
    * @Return:
    */
   @Bean
   public RedisCacheManager cacheManager1d(RedisConnectionFactory connectionFactory) {
      RedisCacheConfiguration config = instanceConfig(3600 * 24 * 1L);
      return RedisCacheManager.builder(connectionFactory)
              .cacheDefaults(config)
              .transactionAware()
              .build();
   }

   /**
    * 过期时间设置为1小时
    *
    * @param connectionFactory
    * @Return:
    */
   @Bean
   public RedisCacheManager cacheManager1h(RedisConnectionFactory connectionFactory) {
      RedisCacheConfiguration config = instanceConfig(60 * 60L);
      return RedisCacheManager.builder(connectionFactory)
              .cacheDefaults(config)
              .transactionAware()
              .build();
   }

   /**
    * 过期时间设置为5分钟
    *
    * @param connectionFactory
    * @Return:
    */
   @Bean
   public RedisCacheManager cacheManager5min(RedisConnectionFactory connectionFactory) {
      RedisCacheConfiguration config = instanceConfig(60 * 5L);
      return RedisCacheManager.builder(connectionFactory)
              .cacheDefaults(config)
              .transactionAware()
              .build();
   }

   private RedisCacheConfiguration instanceConfig(Long ttl) {

      //spring 的page没有无参构造，不能使用jackson的反序列化，因此采用Jdk的。
      RedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
      RedisCacheConfiguration config = RedisCacheConfiguration
              .defaultCacheConfig()
//            .entryTtl(Duration.ofSeconds(ttl))
//            .disableCachingNullValues()
              .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializationRedisSerializer));
      CacheProperties.Redis redisProperties = cacheProperties.getRedis();  //读取配置文件的redis cache
      if (redisProperties.getTimeToLive() != null) {
         config = config.entryTtl(redisProperties.getTimeToLive());
      }
      if (ttl != null) { //用ttl替换timeToLive
         config = config.entryTtl(Duration.ofSeconds(ttl));
      }
      if (redisProperties.getKeyPrefix() != null) {
         config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
      }
      if (!redisProperties.isCacheNullValues()) {
         config = config.disableCachingNullValues();
      }
      if (!redisProperties.isUseKeyPrefix()) {
         config = config.disableKeyPrefix();
      }
      return config;

   }
}
