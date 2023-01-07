package org.example.yiji.dolphin.config.datasource;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wanglin
 * @date 2020/5/21 16:15
 */
@Configuration
@EnableTransactionManagement
//entityManagerFactoryRef：指定实体管理器工厂，transactionManagerRef：指定事务管理器
//basePackages：指定该数据源的repository所在包路径
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactorySecondary",
    transactionManagerRef = "transactionManagerSecondary",
    basePackages = {"org.example.yiji.dolphin.dao.secondary"})
public class SecondaryConfig {

  @Value("${spring.jpa.hibernate.secondary-dialect}")
  private String primaryDialect;// 获取对应的数据库方言

  @Resource
  private JpaProperties jpaProperties;
  @Resource
  private HibernateProperties hibernateProperties;

  @Resource(name = "secondaryDataSource")
  private DataSource secondaryDataSource;

  /**
   * 配置第二数据源实体管理工厂的bean
   * @param builder EntityManagerFactoryBuilder
   * @return LocalContainerEntityManagerFactoryBean
   */
  @Bean(name = "entityManagerFactorySecondary")
  public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary(
      EntityManagerFactoryBuilder builder) {
    return builder.dataSource(secondaryDataSource)
        //指定组合jpaProperties和hibernateProperties配置的map对象
        .properties(getVendorProperties())
        //指定该数据源的实体类所在包路径
        .packages("org.example.yiji.dolphin.model.secondary")
        .persistenceUnit("secondaryPersistenceUnit")
        .build();
  }

  public Map<String, Object> getVendorProperties() {
    Map<String,String> map = new HashMap<>();
    map.put("hibernate.dialect",primaryDialect);// 设置对应的数据库方言
    jpaProperties.setProperties(map);
    return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
  }

  /**
   * 配置第二数据源实体管理器
   * @param builder EntityManagerFactoryBuilder
   * @return EntityManager
   */
  @Bean(name = "entityManagerSecondary")
  public EntityManager entityManagerSecondary(EntityManagerFactoryBuilder builder) {
    return entityManagerFactorySecondary(builder).getObject().createEntityManager();
  }

  /**
   * 配置第二数据源事务管理器
   * @param builder EntityManagerFactoryBuilder
   * @return PlatformTransactionManager
   */
  @Bean(name = "transactionManagerSecondary")
  public PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder) {
    return new JpaTransactionManager(entityManagerFactorySecondary(builder).getObject());
  }

}
