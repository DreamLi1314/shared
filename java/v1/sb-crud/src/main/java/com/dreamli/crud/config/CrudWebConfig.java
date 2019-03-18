package com.dreamli.crud.config;

import com.dreamli.crud.controller.BaseController;
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
    * @param registry ViewControllerRegistry
    */
   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/").setViewName("index");
      registry.addViewController("/index.html").setViewName("index");
      registry.addViewController("/dashboard").setViewName("dashboard");
   }

   /**
    * 第二种配置 ViewController 的方式. 其实相当于又创建了一个 WebMvcConfigurer 配置文件.
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
            boolean hasLogin = request.getSession(false) != null &&
                    request.getSession(false).getAttribute(BaseController.ACTIVE_USE_FLAG) != null;
            boolean requestIndex = false;

            String contextPath = request.getContextPath();
            String uri = request.getRequestURI();

            LOGGER.debug("=============contextPath: " + contextPath);
            LOGGER.debug("=============uri: " + uri);

            for (String indexUri : INDEXS_PAGE_URI) {
               if(uri.equals(contextPath + indexUri)) {
                  requestIndex = true;
                  break;
               }
            }

            if(hasLogin && requestIndex) {
               // 已经登录, 且访问登录页面, 跳转到 Dashboard 页面
                request.getRequestDispatcher("/dashboard")
                          .forward(request, response);
                  return false;
            }
            else if(hasLogin || !hasLogin && requestIndex) {
               // TODO: Should redirect to dashboard page when user has login and to access error target.
               return true;
            }

            // 没有登录, 切访问其他资源, 提示用户信息, 并跳转到登录页面
            request.setAttribute("errorMsg",
               messageSource.getMessage("crud.login.noLogin", null,
               LocaleContextHolder.getLocale()));
            request.getRequestDispatcher("/index.html").forward(request, response);

            return false;
         }
      }).addPathPatterns("/**") // 设置拦截的请求 uri
        // 在拦截的 uri 中排除参数指定的 uri, 注意这里将静态资源的映射路径排除了.
//        .excludePathPatterns("/", "/index.html", "/login", "/static/**");
        // 拦截登录页面, 判断当用户已经登录就跳转到 dashboard 页面
        .excludePathPatterns("/static/**", "/test-error");
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
    * @return LocaleResolver
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
               && request.getSession().getAttribute(LOCALE_FLAG) != null)
            {
               locale = (Locale) request.getSession().getAttribute(LOCALE_FLAG);
               LOGGER.debug("Use Session Locale: " + locale);
            }
            else if(StringUtils.isEmpty(reqLocale)
               && request.getHeader("Accept-Language") != null)
            {
               locale = request.getLocale();
               LOGGER.debug("Use Browser Locale: " + locale);
            }
            else if(!StringUtils.isEmpty(reqLocale)) {
               Locale tmp = StringUtils.parseLocale(reqLocale);

               if(tmp != null) {
                  locale = tmp;
                  request.getSession().setAttribute(LOCALE_FLAG, tmp);
               }
            }

            return locale;
         }

         @Override
         public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
         }
      };
   }

   private static final String LOCALE_FLAG = "curd-locale";
   private static final String[] INDEXS_PAGE_URI = {
           "/",
           "/index.html",
           "/login"
   };

   private final MessageSource messageSource;

   private static final Logger LOGGER = LoggerFactory.getLogger(CrudWebConfig.class);
}
