package org.example.service;

import com.mlog.utils.Tool;
import com.mlog.utils.common.MessageException;
import org.example.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author Jack Li
 * @date 2022/7/2 下午1:58
 * @description Redis 分布式锁实现
 * 使用:
   <code>
      final String requestId
         = DistributedLock.getInstance().lockMust(userId);

      try {
         log.info("获取到锁了");
      }
      finally {
         DistributedLock.getInstance().unlock(userId, requestId);
      }
   </code>
 */
@Component
public class DistributedLock {

   /**
    * 分布式锁的 key 前缀
    */
   private static final String LOCK_KEY_PREFIX = "lock-";

   private static final String LUA_LOCK =
      "if redis.call('setNx',KEYS[1],ARGV[1]) then\n" +
         "    if redis.call('get',KEYS[1])==ARGV[1] then\n" +
         "        return redis.call('expire',KEYS[1],ARGV[2])\n" +
         "    else\n" +
         "        return 0\n" +
         "    end\n" +
         "end\n";

   private static final String LUA_UN_LOCK =
      "if redis.call('get',KEYS[1]) == ARGV[1] then\n" +
         "    return redis.call('del',KEYS[1])\n" +
         "else\n" +
         "    return 0\n" +
         "end";

   private final RedisTemplate redisTemplate;

   @SuppressWarnings("all")
   @Autowired
   private DistributedLock thisService;

   private static DistributedLock instance;

   /**
    * 获取分布式锁的超时时间, 默认 5s
    */
   @Value("${mlog.redis.lock.timeout:5000}")
   private long lockTimeout;

   public DistributedLock(RedisTemplate redisTemplate)
   {
      this.redisTemplate = redisTemplate;
   }

   @PostConstruct
   public void init() {
      DistributedLock.instance = this.thisService;
   }

   public static DistributedLock getInstance() {
      return instance;
   }

   /**
    * 强制加分布式锁
    * @param lockKey key
    * @return 是否加锁成功
    * @throws MessageException 加锁超时
    */
   public String lockMust(Object lockKey) {
      final long millis = System.currentTimeMillis();
      final String requestId = getRequestId();

      try {
         do {
            if(lock(lockKey, requestId)) {
               return requestId;
            }

            TimeUnit.MILLISECONDS.sleep(500);
         }
         while(System.currentTimeMillis() - millis < lockTimeout);
      } catch (InterruptedException ignore) {
      }

      throw new MessageException("服务器忙, 请稍后再试!");
   }

   /**
    * 获取 requestId.
    * @return 当前线程操作的唯一标识
    * 1. UUID
    * 2. Snowflake.nextIdStr
    * 3. ...
    */
   private String getRequestId() {
      // 通过 ThreadLocal 使分布式锁变为可重入分布式锁
      return RequestInfoHolder.getRequestId();
   }

   /**
    * 获取分布式锁
    * @param lockKey key
    * @param value 唯一编号, 释放锁时需要比对
    * @return 是否加锁成功
    */
   public boolean lock(Object lockKey, String value) {
      return lock(lockKey, value, RedisConstant.REDIS_LOCK_KEY_EXPIRE_TIME);
   }

   /**
    * 获取分布式锁
    * @param lockKey key
    * @param requestId 唯一编号, 释放锁时需要比对
    * @param expireTime：单位-秒
    * @return 是否加锁成功
    */
   public boolean lock(Object lockKey, String requestId, int expireTime) {
      RedisScript<Long> redisScript
         = new DefaultRedisScript<>(LUA_LOCK, Long.class);

      Object result = redisTemplate.execute(redisScript,
         new StringRedisSerializer(),
         new StringRedisSerializer(),
         Collections.singletonList(LOCK_KEY_PREFIX + Tool.toString(lockKey)),
         requestId,
         expireTime + "");

      return RedisConstant.SUCCESS.equals(result);
   }

   /**
    * 释放锁
    * @param lockKey key
    * @param requestId 唯一编号, 比对一致才能释放
    * @return 是否释放锁成功
    */
   public boolean unlock(Object lockKey, String requestId) {
      RedisScript<Long> redisScript
         = new DefaultRedisScript<>(LUA_UN_LOCK, Long.class);

      Object result = redisTemplate.execute(redisScript,
         new StringRedisSerializer(),
         new StringRedisSerializer(),
         Collections.singletonList(LOCK_KEY_PREFIX + Tool.toString(lockKey)),
         requestId);

      return RedisConstant.SUCCESS.equals(result);
   }

}
