package org.example.yiji.dolphin.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.*;
import java.util.Collections;

/**
 * @author wanglin
 * @date 2019/11/21 14:33
 */
@Configuration
public class MlogWebConfig implements WebMvcConfigurer {

  public static final String IDENTITY_URL = "/identity/**";
  public static final String ALL_URL = "/**";

  //跨域
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods(HttpMethod.GET.name(),HttpMethod.POST.name(),HttpMethod.PUT.name(),HttpMethod.DELETE.name(),HttpMethod.OPTIONS.name())
        .allowCredentials(true)
        .allowedHeaders("*")
    ;
  }

  @Bean
  public MlogInterceptor getMlogInterceptor(){
    return new MlogInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry)
  {
    registry.addInterceptor(getMlogInterceptor()).addPathPatterns("/**")
        .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**",
            "/swagger-ui.html/**","/actuator/**")
        .excludePathPatterns("/user/find-admin", "/user/find-by-ids",
           "/user/find-by-dept", "/export/**")
        .excludePathPatterns(IDENTITY_URL);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
  }

  @Bean
  public ServletContextInitializer servletContextInitializer1() {
    return new ServletContextInitializer() {
      @Override
      public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE) );
      }
    };
  }

}
