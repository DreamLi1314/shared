package com.mlog.datacenter;

import cma.music.RetArray2D;
import cma.music.client.DataQueryClient;
import com.mlog.datacenter.properties.CmadaasProperties;
import com.mlog.datacenter.util.ClibUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class CloudUpdateTimeTests {

   @Autowired
   private DataQueryClient client;

   @Autowired
   private CmadaasProperties properties;

   @Test
   void queryCloudTimeTest() {
      String userId = properties.getUserName();
      String pwd = properties.getPassword();

      /* 2.2  接口ID */
      String interfaceId = "getSateLatestTime";
      /* 2.3  接口参数，多个参数间无顺序 */
      HashMap<String, String> params = new HashMap<String, String>();
      //必选参数
      params.put("dataCode", "SATE_FY4A_STA_L1"); //资料代码
      params.put("latestTime", "2");
//      params.put("elements", "V_FILETIME,FILE_NAME,Datetime") ; //检索时间

      /* 2.4 返回对象 */
      RetArray2D retArray2D = new RetArray2D();

      /* 3. 调用接口 */
      try {
         //初始化接口服务连接资源
         client.initResources();
         //调用接口
         int rst = client.callAPI_to_array2D(userId, pwd, interfaceId, params, retArray2D);

         //输出结果
         if (rst == 0) { //正常返回
            System.out.println("-------------------------------");

            ClibUtil clibUtil = new ClibUtil();
            clibUtil.outputRst(retArray2D);

            System.out.println("-------------------------------");
         } else { //异常返回
            System.out.println("[error] StaElemSearchAPI_CLIB_callAPI_to_array2D.");
            System.out.printf("\treturn code: %d. \n", rst);
            System.out.printf("\terror message: %s.\n", retArray2D.request.errorMessage);
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
