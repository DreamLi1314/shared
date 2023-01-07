package club.javafamily.sbredissentinel.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jack Li
 * @date 2022/5/8 下午11:43
 * @description
 */
@RestController
public class RedisTestController {

   private final RedisTemplate<String, Object> redisTemplate;

   public RedisTestController(RedisTemplate<String, Object> redisTemplate) {
      this.redisTemplate = redisTemplate;
   }

   @GetMapping("/write")
   public String write(@RequestParam("key") String key,
                       @RequestParam("value") String val)
   {
      redisTemplate.opsForValue().set(key, val);

      return (String) redisTemplate.opsForValue().get(key);
   }

}
