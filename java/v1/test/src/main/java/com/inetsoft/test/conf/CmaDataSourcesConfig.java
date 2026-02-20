package com.inetsoft.test.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class CmaDataSourcesConfig {

   @Bean("cmaDatasource")
   @ConfigurationProperties(prefix = "spring.datasource.primary")
   public DataSource dataSource(DataSourceProperties properties) {
      return DataSourceBuilder.create().build();
   }

   @Bean
   public JdbcTemplate cmaJdbcTemplate(@Qualifier("cmaDatasource") DataSource dataSource,
                                       JdbcProperties properties)
   {
      JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
      JdbcProperties.Template template = properties.getTemplate();
      jdbcTemplate.setFetchSize(template.getFetchSize());
      jdbcTemplate.setMaxRows(template.getMaxRows());

      if (template.getQueryTimeout() != null) {
         jdbcTemplate.setQueryTimeout((int)template.getQueryTimeout().getSeconds());
      }

      return jdbcTemplate;
   }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource prodDataSource) {
        return new DataSourceTransactionManager(prodDataSource);
    }

}
