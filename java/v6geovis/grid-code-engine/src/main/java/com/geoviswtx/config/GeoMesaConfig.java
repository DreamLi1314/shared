package com.geoviswtx.config;

import com.geoviswtx.geo.GisUtils;
import org.geotools.api.data.*;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.*;

/**
 * @author Jack Li
 * @date 2025/4/27 18:26
 * @description
 */
@Configuration
@EnableConfigurationProperties(CassandraProperties.class)
public class GeoMesaConfig {

   private final CassandraProperties cassandraProperties;
   public final static String TABLE_NAME = "t_flying";

   public GeoMesaConfig(CassandraProperties cassandraProperties) {
      this.cassandraProperties = cassandraProperties;
   }

   @Bean
   public DataStore geoMesaDataStore() throws IOException {
      // 显式注册CassandraDataStoreFactory
      DataAccessFinder.scanForPlugins();

      Map<String, String> params = new HashMap<>();
//      params.put("geomesa.cassandra.contact.point", String.join(",", cassandraProperties.getContactPoints()));
      params.put("geomesa.cassandra.contact.point", "10.1.109.140:9042");
      params.put("geomesa.cassandra.keyspace", cassandraProperties.getKeyspaceName());
      params.put("cassandra.catalog", TABLE_NAME);

      params.put("cassandra.batch.size", "100");
      params.put("cassandra.query.fetch-size", "1000");

      DataStore dataStore = DataStoreFinder.getDataStore(params);

      createSchema(dataStore);

      return dataStore;
   }

   public void createSchema(DataStore dataStore) throws IOException {
      // 创建空间类型
      SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
      builder.setName(TABLE_NAME);
      builder.add("uas_id", String.class);
      builder.add("uas_model", String.class);
      builder.add("uas_name", String.class);
      builder.add("geom", Point.class, GisUtils.getGeometryFactory().getSRID()); // 几何字段（WGS84坐标）
      builder.add("height", Float.class);      // 属性字段
      builder.add("dtg", Date.class);   // 时间字段 - 数据时间

      builder.setDefaultGeometry("geom");
      // 设置默认时间字段（用于时空索引）
      builder.userData("geomesa.index.dtg", "dtg");

      // Cassandra 特定配置
      builder.userData("geomesa.table.sharding", "true");  // 启用分片
      builder.userData("geomesa.indices.enabled", "z3");   // 使用Z3索引

      SimpleFeatureType sft = builder.buildFeatureType();
      dataStore.createSchema(sft);
   }

}
