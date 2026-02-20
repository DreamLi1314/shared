package com.mlog.datacenter.query;

import cma.music.RetArray2D;
import cma.music.client.DataQueryClient;
import com.mlog.datacenter.properties.CmadaasProperties;
import com.mlog.datacenter.util.ClibUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

/**
 * 按时间段查询 国家站-时值-气温(平均气温, 最高气温, 最低气温)
 */
@SpringBootTest
public class NationalHourTemQueryTests {

   @Autowired
   private DataQueryClient client;

   @Autowired
   private CmadaasProperties properties;

   @Test
   void testQuery() {
      String userId = properties.getUserName();
      String pwd = properties.getPassword();

      /* 2.2  接口ID */
      String interfaceId = "getSurfEleByTimeRange" ;
      /* 2.3  接口参数，多个参数间无顺序 */
      HashMap<String, String> params = new HashMap<String, String>();
      //必选参数
      params.put("dataCode", "SURF_CHN_MUL_HOR_N") ; //资料代码
      params.put("elements", "Station_ID_C,TEM,AVG_TEM") ;//检索要素：站号、站名、小时降水、气压、相对湿度、能见度、2分钟平均风速、2分钟风向
      params.put("timeRange", "(20190601000000,20190601010000]") ; //检索时间
      //可选参数
      params.put("orderby", "Station_ID_C:ASC") ; //排序：按照站号从小到大
      params.put("staLevels", "011,012") ; //排序：按照站号从小到大
      params.put("limitCnt", "100") ; //返回最多记录数：100
      /* 2.4 返回对象 */
      RetArray2D retArray2D = new RetArray2D() ;

      /* 3. 调用接口 */
      try {
         //初始化接口服务连接资源
         client.initResources() ;
         //调用接口
         int rst = client.callAPI_to_array2D(userId, pwd, interfaceId, params, retArray2D) ;
         //输出结果
         if(rst == 0) { //正常返回
            ClibUtil clibUtil = new ClibUtil() ;
            clibUtil.outputRst( retArray2D ) ;
         } else { //异常返回
            System.out.println( "[error] StaElemSearchAPI_CLIB_callAPI_to_array2D." ) ;
            System.out.printf( "\treturn code: %d. \n", rst ) ;
            System.out.printf( "\terror message: %s.\n", retArray2D.request.errorMessage ) ;
         }
      } catch (Exception e) {
         //异常输出
         e.printStackTrace() ;
      } finally {
         //释放接口服务连接资源
         client.destroyResources() ;
      }
   }
}
