package com.dreamli.crud.config;

import com.dreamli.crud.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@ConditionalOnClass(MessageSource.class)
@Configuration
public class CrudWebConfig implements WebMvcConfigurer {

   @Autowired
   public CrudWebConfig(MessageSource messageSource) {
      this.messageSource = messageSource;
   }

   /**
    * 第一种配置 ViewController 的方式
    * @param registry
    */
   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/").setViewName("index");
      registry.addViewController("/index.html").setViewName("index");
      registry.addViewController("/dashboard").setViewName("dashboard");
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

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
   //      registry.addInterceptor(localeInterceptor());
      // 注册登录拦截器, 当用户没有登录访问主页以外的页面时, 重定向到主页
      registry.addInterceptor(new HandlerInterceptor() {
         @Override
         public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Object user = request.getSession().getAttribute(LoginController.ACTIVE_USE_FLAG);

            if(user != null) {
               // 已经登录
               return true;
            }

            // 没有登录, 提示用户信息, 并跳转到登录页面
            request.setAttribute("errorMsg",
               messageSource.getMessage("crud.login.noLogin", null,
               LocaleContextHolder.getLocale()));
            request.getRequestDispatcher("/index.html").forward(request, response);

            return false;
         }
      }).addPathPatterns("/**") // 设置拦截的请求 uri
        // 在拦截的 uri 中排除参数指定的 uri, 注意这里将静态资源的映射路径排除了.
        .excludePathPatterns("/", "/index.html", "/login", "/static/**");
   }

//   @Bean
//   public HandlerInterceptor localeInterceptor() {
//      LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//      localeChangeInterceptor.setParamName(LOCALE_FLAG);
//
//      return localeChangeInterceptor;
//   }

   /**
    * 1. 自定义 LocaleResolver 必须将 LocaleResolver 放入 IOC 中.
    * 2. 放入 IOC 中的 bean id(下面方法名) 必须为 localeResolver.
    * @return
    */
   @Bean
   public LocaleResolver localeResolver() {
      return new LocaleResolver() {

         @Override
         public Locale resolveLocale(HttpServletRequest request) {
            Locale locale = Locale.getDefault();

            String reqLocale = request.getParameter(LOCALE_FLAG);

            LOGGER.debug("User Locale: " + reqLocale);

            if(StringUtils.isEmpty(reqLocale)
                    && request.getHeader("Accept-Language") != null)
            {
               locale = request.getLocale();
               LOGGER.debug("Use Browser Locale: " + locale);
            }
            else if(!StringUtils.isEmpty(reqLocale)) {
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

   private static final String LOCALE_FLAG = "curd-locale";

   private final MessageSource messageSource;

   private static final Logger LOGGER = LoggerFactory.getLogger(CrudWebConfig.class);
}
