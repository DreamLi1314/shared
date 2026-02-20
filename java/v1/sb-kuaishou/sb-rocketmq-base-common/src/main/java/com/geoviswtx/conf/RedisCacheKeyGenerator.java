package com.geoviswtx.conf;

import com.geoviswtx.common.Tool;
import com.geoviswtx.constant.RedisConstant;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.StringJoiner;

/**
 * @author Jack Li
 * @date 2022/7/26 下午7:16
 * @description
 */
public class RedisCacheKeyGenerator implements KeyGenerator {
   @Override
   public Object generate(Object target, Method method, Object... params) {
      StringJoiner sj = new StringJoiner(RedisConstant.KEY_FLAG);

      for (Object param : params) {
         sj.add(Tool.toString(param));
      }

      sj.add(method.getName());

      return sj.toString();
   }
}
