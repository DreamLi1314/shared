package org.example.yiji.dolphin.config.datasource;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author wanglin
 * @date 2020/5/21 16:07
 */
@Configuration
@Slf4j
public class DataSourceConfig {

  @Bean(name = "primaryDataSource")
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.primary")
  public DataSource primaryDataSource() {
    log.info("-------------------- primary init ---------------------");
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "secondaryDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.secondary")
  public DataSource secondaryDataSource() {
    log.info("-------------------- secondary init ---------------------");
    return DataSourceBuilder.create().build();
  }

  /******配置事务管理********/

  @Bean
  public PlatformTransactionManager bfTransactionManager(@Qualifier("primaryDataSource")DataSource prodDataSource) {
    return new DataSourceTransactionManager(prodDataSource);
  }

  @Bean
  public PlatformTransactionManager bfscrmTransactionManager(@Qualifier("secondaryDataSource")DataSource sitDataSource) {
    return new DataSourceTransactionManager(sitDataSource);
  }

}
