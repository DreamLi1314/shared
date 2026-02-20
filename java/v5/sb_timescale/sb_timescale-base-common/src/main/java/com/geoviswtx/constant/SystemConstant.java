package com.geoviswtx.constant;

/**
 * @author Jack Li
 * @date 2022/3/30 10:53 下午
 * @description
 */
public interface SystemConstant {

   String session_user = "user";

   String POINT_INFO_SPLIT = "-";

   String TMP_BUCKET = "tmp";

   String SYS_BUCKET = "hsl-integrate";

   /**
    * common flag
    */
   String FLAG = "__^^__";
   /**
    * 数据分割符
    */
   String DATA_SPLIT_FLAG = ",";
   /**
    * 临时文件分隔符
    */
   String TEMP_FILE_FLAG = "-temp-";

   /**
    * request id
    */
   String REQUEST_ID = "requestId";

   /**
    * xxl job admin url
    */
   String XXL_JOB_URL = "https://";

   /**
    * 预报数据时次分隔符
    */
   String UPLOAD_FILE_INFO_SPLIT = "_";

   /**
    * 灰度图文件名分隔符
    */
   String GREY_IMG_SPLIT_FLAG = "___";

   /**
    * 灰度图文件后缀
    */
   String GREY_IMG_TYPE_SUFFIX = ".png";

   /**
    * 没有高度层的要素默认高度层
    */
   double NO_Z_ELEM_Z = 0;

   /**
    * 默认灰度图的上下转换策略
    */
   boolean DEFAULT_UPDOWN_INVERSION = true;
   /**
    * 单位 size 转换
    */
   double UNIT_SIZE = 1024D;
   /**
    * mb 的字节数
    */
   int MB_BYTES = 1024 * 1024;

   /**
    * gb 的字节数
    */
   long GB_BYTES = MB_BYTES * 1024;

   /**
    * MB
    */
   String UNIT_MB = "MB";
   /**
    * GB
    */
   String UNIT_GB = "GB";
   /**
    * TB
    */
   String UNIT_TB = "TB";
   /**
    * PB
    */
   String UNIT_PB = "PB";
   /**
    * 数据量单位
    */
   String[] SIZE_UNIT = new String[] { UNIT_MB,UNIT_GB, UNIT_TB, UNIT_PB };
   /**
    * netcdf 文件时间属性名称
    */
   String NETCDF_TIME_UNITS_ATTR = "units";

}
