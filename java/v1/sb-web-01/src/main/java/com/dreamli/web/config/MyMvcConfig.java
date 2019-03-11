package com.dreamli.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

   /**
    * 自定义 LocaleResolver 实现通过传递参数修改 Locale
    * @return LocaleResolver
    */
//   @Bean
   public LocaleResolver localeResolver() {
      return new MyLocaleResolver();
   }

   public static class MyLocaleResolver implements LocaleResolver {

      @Override
      public Locale resolveLocale(HttpServletRequest request) {
         String l = request.getParameter("l"); // 获取login前台页面传过来参数（l自定义的）
         Locale locale = Locale.getDefault(); // 初始化是默认的

         if (!StringUtils.isEmpty(l)) { // 判断是否为null
            String[] spilt = l.split("_"); // 由于格式是en_us的格式，所以以_分割。
            locale = new Locale(spilt[0], spilt[1]); // 截取之后把新的属性封装给local
         }

         return locale;
      }

      @Override
      public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
      }
   }
}
