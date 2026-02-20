//package com.geoviswtx.conf;
//
//import com.github.benmanes.caffeine.cache.Caffeine;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import org.springframework.cache.Cache;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.caffeine.CaffeineCache;
//import org.springframework.cache.support.SimpleCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
///**
// * @title: CacheConfig
// * @description:
// */
//@Configuration
//public class CacheConfig {
//
//  /********************************
//   *  @function  : 生成缓存管理器
//   *  @parameter : []
//   *  @return    : org.springframework.cache.CacheManager
//   ********************************/
//  @Primary
//  @Bean("customCacheManager")
//  public CacheManager customCacheManager() {
//    SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
//    List<Cache> cacheList = new ArrayList<>();
//    cacheList.add(customCache());
//    simpleCacheManager.setCaches(cacheList);
//    return simpleCacheManager;
//  }
//
//  /********************************
//   *  @function  : 生成自定义缓存容器
//   *  @parameter : []
//   *  @return    : org.springframework.cache.Cache
//   ********************************/
//  public Cache customCache() {
//    return new CaffeineCache("customCache", Caffeine.newBuilder()
//        .maximumSize(100)
//        .initialCapacity(100)
//        .expireAfterWrite(10, TimeUnit.MINUTES)
//        .recordStats()
//        .build(),
//        true);
//  }
//
//}
