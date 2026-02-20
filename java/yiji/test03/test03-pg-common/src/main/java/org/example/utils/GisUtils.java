package org.example.utils;

import org.locationtech.jts.geom.*;

/**
 * @author Jack Li
 * @date 2022/4/26 10:44 上午
 * @description
 */
public final class GisUtils {
   private GisUtils() {
   }

   /**
    * 4326 坐标系 Factory
    */
   private static final GeometryFactory geometryFactory
      = new GeometryFactory(new PrecisionModel(), 4326);

   public static GeometryFactory getGeometryFactory() {
      return geometryFactory;
   }

   /**
    * 点转字符串
    * @param point
    * @return
    */
   public static String toLatlon(Point point) {
      if(point == null) {
         return null;
      }

      return point.getY() + "°N," + point.getX() + "°E";
   }

   public static Point createPoint(
      double x, double y)
   {
      return geometryFactory.createPoint(new Coordinate(x, y));
   }

   public static Polygon createCircle(
      double x, double y, final double radius)
   {
      //圆上面的点个数
      final int SIDES = 32;

      Coordinate[] coords = new Coordinate[SIDES+1];

      for( int i = 0; i < SIDES; i++){
         double angle = ((double) i / (double) SIDES) * Math.PI * 2.0;
         double dx = Math.cos( angle ) * radius;
         double dy = Math.sin( angle ) * radius;
         coords[i] = new Coordinate(x + dx, y + dy);
      }

      coords[SIDES] = coords[0];
      LinearRing ring = geometryFactory.createLinearRing(coords);

      return geometryFactory.createPolygon(ring, null);
   }

}
