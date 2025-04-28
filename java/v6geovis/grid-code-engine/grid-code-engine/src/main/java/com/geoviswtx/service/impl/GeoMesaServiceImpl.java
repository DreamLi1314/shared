package com.geoviswtx.service.impl;

import com.geoviswtx.geo.GisUtils;
import com.geoviswtx.service.GeoMesaService;
import org.geotools.api.data.DataStore;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.locationtech.geomesa.utils.interop.SimpleFeatureTypes;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Jack Li
 * @date 2025/4/28 17:00
 * @description
 */
@Service
public class GeoMesaServiceImpl implements GeoMesaService {

   private final DataStore dataStore;

   public GeoMesaServiceImpl(DataStore dataStore) {
      this.dataStore = dataStore;
   }

   @Override
   public void createSchema(String typeName) throws Exception {
      // 创建空间类型
      SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
      builder.setName(typeName);
      builder.add("uas_id", String.class);      // 属性字段 - 网格编码
      builder.add("geom", Point.class, GisUtils.getGeometryFactory().getSRID()); // 几何字段（WGS84坐标）
      builder.add("height", Float.class);      // 属性字段
      builder.add("datetime", Date.class);   // 时间字段 - 数据时间

      // 设置默认时间字段（用于时空索引）
      builder.setDefaultGeometry("geom");
      builder.userData(SimpleFeatureTypes.DEFAULT_DATE_KEY, "datetime");

      // Cassandra 特定配置
      builder.userData("geomesa.table.sharding", "true");  // 启用分片
      builder.userData("geomesa.indices.enabled", "z3");   // 使用Z3索引

      SimpleFeatureType sft = builder.buildFeatureType();
      dataStore.createSchema(sft);
   }

   @Override
   public String ingestData(String typeName, String jsonData) throws Exception {
      return "";
   }

   @Override
   public String queryByBoundingBox(String typeName, double minX, double minY, double maxX, double maxY) throws Exception {
      return "";
   }
}
