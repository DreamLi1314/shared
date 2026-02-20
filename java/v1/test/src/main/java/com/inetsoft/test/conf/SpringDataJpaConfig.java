package com.inetsoft.test.conf;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.*;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
   basePackages = {"com.inetsoft.test.dao"}
)
public class SpringDataJpaConfig {

   @Value("${spring.jpa.hibernate.primary-dialect}")
   private String primaryDialect;

   @Value("${spring.jpa.database}")
   private Database databaseType;

   private final JpaProperties jpaProperties;
   private final HibernateProperties hibernateProperties;
   private final DataSource primaryDataSource;

   public SpringDataJpaConfig(JpaProperties jpaProperties,
                              @Qualifier("cmaDatasource") DataSource primaryDataSource,
                              HibernateProperties hibernateProperties) {
      this.hibernateProperties = hibernateProperties;
      this.jpaProperties = jpaProperties;
      this.primaryDataSource = primaryDataSource;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder) {
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
         .packages("com.inetsoft.test.domain") //设置实体类所在位置
         .persistenceUnit("primaryPersistenceUnit")
         .build();
   }

   @Primary
   @Bean
   public EntityManager entityManagerPrimary(EntityManagerFactoryBuilder builder) {
      return entityManagerFactory(builder).getObject().createEntityManager();
   }

   @Primary
   @Bean("mainTransactionManager")
   public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
      return new JpaTransactionManager(entityManagerFactory(builder).getObject());
   }

}
