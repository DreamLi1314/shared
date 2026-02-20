package com.geoviswtx.conf;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * jpa 配置类
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
   basePackages = {"com.geoviswtx.dao"},
   entityManagerFactoryRef = "entityManagerFactory",
   transactionManagerRef = "transactionManager"
)
public class SpringDataJpaConfig {

   @Value("${spring.jpa.hibernate.primary-dialect}")
   private String primaryDialect;

   @Value("${spring.jpa.primary.database}")
   private Database databaseType;

   private final JpaProperties jpaProperties;
   private final HibernateProperties hibernateProperties;
   private final DataSource primaryDataSource;

   public SpringDataJpaConfig(JpaProperties jpaProperties,
                              DataSource primaryDataSource,
                              HibernateProperties hibernateProperties)
   {
      this.hibernateProperties = hibernateProperties;
      this.jpaProperties = jpaProperties;
      this.primaryDataSource = primaryDataSource;
   }

   @Bean
   @Primary
   public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder)
   {
      Map<String, String> map = new HashMap<>();
      map.put("hibernate.dialect", primaryDialect);
      map.put("hibernate.format_sql", "true");
      jpaProperties.setProperties(map);
      jpaProperties.setDatabase(databaseType);
      Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
         jpaProperties.getProperties(), new HibernateSettings());

      return builder
         .dataSource(primaryDataSource)
         .properties(properties)
         .packages("com.geoviswtx.entity") //设置实体类所在位置
         .persistenceUnit("primaryPersistenceUnit")
         .build();
   }

   @Bean
   @Primary
   public EntityManager entityManagerPrimary(LocalContainerEntityManagerFactoryBean bean) {
      return bean.getObject().createEntityManager();
   }

   @Bean
   @Primary
   public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean bean) {
      return new JpaTransactionManager(bean.getObject());
   }

}
