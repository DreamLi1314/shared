package club.javafamily.sbflowable.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Jack Li
 * @date 2021/9/15 11:21 上午
 * @description
 */
@Configuration
public class DatasourceConfig {

   @Bean(name = "primaryDataSource")
   @ConditionalOnMissingBean
   @ConfigurationProperties(prefix = "spring.datasource.primary")
   public DataSource primaryDataSource() {
      return DataSourceBuilder.create().build();
   }

//   @Bean(name = "secondDataSource")
//   @ConfigurationProperties(prefix = "spring.datasource.second")
//   public DataSource dataSourceSecond() {
//      return DataSourceBuilder.create().build();
//   }
}
