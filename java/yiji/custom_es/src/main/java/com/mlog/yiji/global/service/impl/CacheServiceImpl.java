package com.mlog.yiji.global.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mlog.yiji.global.service.CacheService;
import com.mlog.yiji.global.vo.GeoVo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;


@Service
public class CacheServiceImpl implements CacheService {

    private Cache<String,GeoVo> commonCache=null;
    @PostConstruct//代理此bean时会首先执行该初始化方法
    public void init(){
        commonCache= CacheBuilder.newBuilder()
                //设置缓存容器的初始化容量为200（可以存10个键值对）
                .initialCapacity(200)
                //最大缓存容量是1000,超过1000后会安装LRU策略-最近最少使用
                .maximumSize(1000)
                //设置写入缓存后1分钟后过期
                .expireAfterWrite(60, TimeUnit.SECONDS).build();
    }


    @Override
    public void setCommonCache(String key, GeoVo value) {
        commonCache.put(key,value);
    }

    @Override
    public GeoVo getCommonCache(String key) {
        return commonCache.getIfPresent(key);
    }
}
