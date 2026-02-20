package com.mlog.datacenter;

import cma.music.RetFilesInfo;
import cma.music.client.DataQueryClient;
import com.mlog.datacenter.properties.CmadaasProperties;
import com.mlog.datacenter.util.ClibUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class CloudImgDataTests {

   @Autowired
   private DataQueryClient client;

   @Autowired
   private CmadaasProperties properties;

   @Test
   void queryCloudTimeTest() {
      String userId = properties.getUserName();
      String pwd = properties.getPassword();

      /* 2.2  接口ID */
      String interfaceId = "getSateFileByTimeRange";
      /* 2.3  接口参数，多个参数间无顺序 */
      HashMap<String, String> params = new HashMap<String, String>();
      //必选参数
      params.put("dataCode", "SATE_FY4A_STA_L1"); //资料代码
      params.put("timeRange", "[20210623000000,20210624000000)");
      // 一级和二级产品
      params.put("elements", "Datetime,V_FILETIME,FILE_NAME,File_URL") ; //检索时间
      // 三级产品(没数据)
//      params.put("elements", "Datetime,FILE_NAME,File_URL") ;


      /* 2.4 返回对象 */
      RetFilesInfo retFilesInfo = new RetFilesInfo() ;

      /* 3. 调用接口 */
      try {
         //初始化接口服务连接资源
         client.initResources();
         //调用接口
         int rst = client.callAPI_to_fileList(userId, pwd, interfaceId, params, retFilesInfo);

         //输出结果
         if (rst == 0) { //正常返回
            System.out.println("-------------------------------");

            ClibUtil clibUtil = new ClibUtil() ;
            clibUtil.outputRst( retFilesInfo ) ;

            System.out.println("-------------------------------");
         } else { //异常返回
            System.out.println("[error] StaElemSearchAPI_CLIB_callAPI_to_array2D.");
            System.out.printf("\treturn code: %d. \n", rst);
            System.out.printf("\terror message: %s.\n", retFilesInfo.request.errorMessage);
         }
      } catch (Exception e) {
         //异常输出
         e.printStackTrace();
      } finally {
         //释放接口服务连接资源
         client.destroyResources();
      }
   }
}
