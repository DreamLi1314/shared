package org.example.yiji.dolphin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author Jack Li
 * @date 2022/5/21 下午4:13
 * @description 分布式 Session 配置
 * maxInactiveIntervalInSeconds: 设置 Session 失效时间
 * 使用 Redis Session 之后，原 Spring Boot 中的 server.session.timeout 属性不再生效。
 */
@Configuration
@EnableRedisHttpSession
public class SessionConfig {

  /**
   * 新版本的spring web 默认启用了 SameSite,不设置为null会让前端获取不到jsessionId
   */
  @Bean
  public CookieSerializer httpSessionIdResolver() {
    DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
    cookieSerializer.setSameSite(null);
    cookieSerializer.setCookiePath("/");
//    cookieSerializer.setUseHttpOnlyCookie(false);

    return cookieSerializer;
  }

}
