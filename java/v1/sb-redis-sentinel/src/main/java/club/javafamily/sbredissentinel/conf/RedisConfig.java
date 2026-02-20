package club.javafamily.sbredissentinel.conf;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Li
 * @date 2022/5/8 下午6:42
 * @description Redis 哨兵配置
 */
@Configuration
public class RedisConfig {

   private final RedisProperties redisProperties;

   public RedisConfig(RedisProperties redisProperties) {
      this.redisProperties = redisProperties;
   }

   /**
    * 创建 Redis 连接池配置
    */
   @Bean
   public GenericObjectPoolConfig poolConfig() {
      GenericObjectPoolConfig config = new GenericObjectPoolConfig();
      config.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
      config.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
      config.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
      config.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis());

      return config;
   }

   /**
    * 声明哨兵配置, 将哨兵信息放到配置中
    */
   @Bean
   public RedisSentinelConfiguration sentinelConfig() {
      RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
      redisConfig.setMaster(redisProperties.getSentinel().getMaster());
      redisConfig.setPassword(redisProperties.getPassword());

      if(redisProperties.getSentinel().getNodes()!=null) {
         List<RedisNode> sentinelNode = new ArrayList<RedisNode>();

         for(String sen : redisProperties.getSentinel().getNodes()) {
            String[] arr = sen.split(":");
            sentinelNode.add(new RedisNode(arr[0],Integer.parseInt(arr[1])));
         }

         redisConfig.setSentinels(sentinelNode);
      }

      return redisConfig;
   }

   /**
    * 创建连接工厂 LettuceConnectionFactory
    */
   @Bean("lettuceConnectionFactory")
   public LettuceConnectionFactory lettuceConnectionFactory(
      @Qualifier("poolConfig") GenericObjectPoolConfig config,
      @Qualifier("sentinelConfig") RedisSentinelConfiguration sentinelConfig)
   {
      LettuceClientConfiguration clientConfiguration
         = LettucePoolingClientConfiguration.builder()
         .poolConfig(config)
         .build();

      return new LettuceConnectionFactory(sentinelConfig, clientConfiguration);
   }

   /**
    * 创建 RedisTemplate
    */
   @Bean("stringObjectRedisTemplate")
   public RedisTemplate<String, Object> stringObjectRedisTemplate(
      @Qualifier("lettuceConnectionFactory") LettuceConnectionFactory connectionFactory)
   {
      RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
      redisTemplate.setConnectionFactory(connectionFactory);

      StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
      GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer
         = new GenericJackson2JsonRedisSerializer();

      //设置序列化器
      redisTemplate.setKeySerializer(stringRedisSerializer);
      redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
      redisTemplate.setHashKeySerializer(stringRedisSerializer);
      redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
      redisTemplate.afterPropertiesSet();

      return redisTemplate;
   }
}
