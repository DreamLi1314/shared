package com.geoviswtx.conf;

import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = {"com.geoviswtx.dao"})
public class CassandraConfig extends AbstractCassandraConfiguration {

  private final CassandraProperties properties;

  public CassandraConfig(CassandraProperties properties) {
    this.properties = properties;
  }

  @Override
  public String getKeyspaceName() {
    return properties.getKeyspaceName();
  }

  @Override
  protected String getLocalDataCenter() {
    return properties.getLocalDatacenter();
  }

  @Override
  public String getContactPoints() {
    return String.join(",", properties.getContactPoints());
  }

  @Override
  protected int getPort() {
    return properties.getPort();
  }

  @Override
  public CqlSessionFactoryBean cassandraSession() {
    CqlSessionFactoryBean cqlSessionFactoryBean = super.cassandraSession();
    cqlSessionFactoryBean.setPassword(properties.getPassword());
    cqlSessionFactoryBean.setUsername(properties.getUsername());

    return cqlSessionFactoryBean;
  }

  /*
   * Automatically creates a Keyspace if it doesn't exist
   */
  @Override
  protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
    CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
        .createKeyspace(properties.getKeyspaceName()).ifNotExists()
        .with(KeyspaceOption.DURABLE_WRITES, true)
       .withSimpleReplication(1);

    return Collections.singletonList(specification);
  }

  @Override
  protected SessionBuilderConfigurer getSessionBuilderConfigurer() {
    return (cqlSessionBuilder) -> cqlSessionBuilder
       .withConfigLoader(DriverConfigLoader.programmaticBuilder()
          .withDuration(DefaultDriverOption.REQUEST_TIMEOUT,
             Duration.ofSeconds(60))
          .build());
  }

  /*
   * Automatically configure a table if doesn't exist
   */
  @Override
  public SchemaAction getSchemaAction() {
    return SchemaAction.CREATE_IF_NOT_EXISTS;
  }

  /*
   * Get the entity package (where the entity class has the @Table annotation)
   */
  @Override
  public String[] getEntityBasePackages() {
    return new String[] { "com.geoviswtx.entity.cassandra" };
  }

}
