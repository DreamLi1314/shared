package com.geoviswtx.constant;

/**
 * @author Jack Li
 * @date 2022/4/22 10:50 上午
 * @description
 */
public interface RedisConstant {

   /**
    * key flag
    */
   String KEY_FLAG = ".";

   /**
    * 加锁/释放锁成功标志
    */
   Long SUCCESS = 1L;

   /**
    * 默认的分布式锁过期释放时间: 10s
    */
   int REDIS_LOCK_KEY_EXPIRE_TIME = 10;

}
