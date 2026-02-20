package mlog.demo.client.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

//@Configuration
public class DruidDataSourceConfig {

   //将初始化配置加载进数据源配置中
   @ConfigurationProperties("spring.datasource")
   @Bean("tempDs")
   public DataSource tempDs() {
      return new DruidDataSource();
   }

   @Bean
   @Primary
   public DataSource dataSource(DataSource tempDs) throws SQLException {
      return tempDs.unwrap(DruidDataSource.class);
   }

   //配置druid的监控
   //1、配置一个管理后台的Servlet
   @Bean
   public ServletRegistrationBean statViewServlet() {
      ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
      Map<String, String> initParams = new HashMap<>();
//      initParams.put("loginUsername", "admin");
//      initParams.put("loginPassword", "123456");
      initParams.put("allow", "");//默认就是允许所有访问
      initParams.put("deny", "192.168.99.49");
      bean.setInitParameters(initParams);
      return bean;
   }

   //2、配置一个web监控filter
   @Bean
   public FilterRegistrationBean webStatFilter() {
      FilterRegistrationBean bean = new FilterRegistrationBean();
      bean.setFilter(new WebStatFilter());

      Map<String, String> initParams = new HashMap<>();
      initParams.put("exclusions", "*.js,*.css,/druid/*");
      bean.setInitParameters(initParams);
      bean.setUrlPatterns(Arrays.asList("/*"));
      return bean;
   }

}
