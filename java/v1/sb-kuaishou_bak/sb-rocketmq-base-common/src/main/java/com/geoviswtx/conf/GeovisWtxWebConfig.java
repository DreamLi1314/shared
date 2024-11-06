package com.geoviswtx.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author Jack Li
 * @date 2021/8/2 11:55 上午
 * @description
 */
@Configuration
public class GeovisWtxWebConfig implements WebMvcConfigurer {

   private final LogInterceptor logInterceptor;
   private final GeoVisWtxInterceptor mLogInterceptor;
   private final ObjectMapper objectMapper;

   public GeovisWtxWebConfig(LogInterceptor logInterceptor,
                             GeoVisWtxInterceptor mLogInterceptor,
                             ObjectMapper objectMapper)
   {
      this.logInterceptor = logInterceptor;
      this.mLogInterceptor = mLogInterceptor;
      this.objectMapper = objectMapper;
   }

   /**
    * 跨域
    */
   @Override
   public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
         .allowedOrigins("*")
         .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name())
         .allowCredentials(true)
         .allowedHeaders("*");
   }

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      String[] excludePaths = new String[] {
         "/",
         "/swagger-resources/**",
         "/webjars/**",
         "/v2/**", "/swagger-ui.html/**",
         "/csrf",
         "*.svg", "*.ico",
         "/error",
         "/dict/**",
         "/public/**",
         // "/**" // debug
      };

      registry.addInterceptor(mLogInterceptor)
         .addPathPatterns("/**")
         .excludePathPatterns(excludePaths);

      registry.addInterceptor(logInterceptor)
         .addPathPatterns("/**")
         .excludePathPatterns(excludePaths);
   }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
   }

   @Bean
   public ServletContextInitializer servletContextInitializer1() {
      return (ServletContext servletContext)
         -> servletContext.setSessionTrackingModes(
            Collections.singleton(SessionTrackingMode.COOKIE));
   }

   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
   {
      MappingJackson2HttpMessageConverter jackson2HttpMessageConverter =
         new MappingJackson2HttpMessageConverter();

      SimpleModule simpleModule = new SimpleModule();
      simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
      simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
      simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

      objectMapper.registerModule(simpleModule);
      objectMapper.registerModule(new JavaTimeModule());

      jackson2HttpMessageConverter.setObjectMapper(objectMapper);
      converters.add(jackson2HttpMessageConverter);
      converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
   }

}
