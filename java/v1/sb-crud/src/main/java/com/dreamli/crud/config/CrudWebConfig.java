package com.dreamli.crud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Configuration
public class CrudWebConfig implements WebMvcConfigurer {

   public CrudWebConfig() {
      if(LOGGER.isDebugEnabled()) {
         LOGGER.debug("create CrudWebConfig...");
      }
   }

   @Bean
   public LocaleResolver getLocaleResolver() {

      if(LOGGER.isDebugEnabled()) {
         LOGGER.debug("getLocaleResolver.....");
      }

      return new LocaleResolver() {

         @Override
         public Locale resolveLocale(HttpServletRequest request) {
            if(LOGGER.isDebugEnabled()) {
               LOGGER.debug("prepare create local.....");
            }

            String reqLocale = request.getParameter("locale");

            Locale locale = Locale.getDefault();

            if(StringUtils.isEmpty(reqLocale)) {
               reqLocale = request.getHeader("Accept-Language");
            }

            if(!StringUtils.isEmpty(reqLocale)) {
               Locale tmp = StringUtils.parseLocale(reqLocale);
               locale = tmp != null ? tmp : locale;
            }

            return locale;
         }

         @Override
         public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

         }
      };
   }

   /**
    * 第一种配置 ViewController 的方式
    * @param registry
    */
   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/").setViewName("index");
      registry.addViewController("/index.html").setViewName("index");
   }

   /**
    * 第二种配置 ViewController 的方式. 其实相当于又创建了一个 WebMvcConfigurer 配置文件.
    * @return
    */
//   @Bean
//   public WebMvcConfigurer getWebMvcConfigurer() {
//      return new WebMvcConfigurer() {
//         @Override
//         public void addViewControllers(ViewControllerRegistry registry) {
//            registry.addViewController("/index.html").setViewName("index");
//         }
//      };
//   }


   private static final Logger LOGGER = LoggerFactory.getLogger(CrudWebConfig.class);
}
