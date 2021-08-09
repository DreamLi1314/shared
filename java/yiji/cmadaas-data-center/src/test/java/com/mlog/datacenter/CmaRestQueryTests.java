package com.mlog.datacenter;

import com.mlog.datacenter.util.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.UUID;

public class CmaRestQueryTests {

   @Test
   void testRestQuery() {
      // 服务节点
      String serviceNodeId = "NMIC_MUSIC_CMADAAS";
      /* 1. 调用方法的参数定义，并赋值 */
      String params ="serviceNodeId=" + serviceNodeId
//         + "&method=callAPI_to_array2D"
         + "&userId=USR_XIAN_DATA" /* 1.1 用户名 */
         + "&interfaceId=statSurfPre" /* 1.2 接口ID */
         + "&elements=Station_ID_C" //要素：站号、经度、纬度
         + "&timeRange=[20190601000000,20190601060000]" //检索时间
//         + "&orderby=SUM_PRE_1h:desc" //排序：按照站号从小到大
         + "&limitCnt=10" //返回最多记录数：10
         + "&dataFormat=json" ; /* 1.4 序列化格式 */
      //拼接timestamp、nonce
      String timestamp = String.valueOf(System.currentTimeMillis());
      String nonce = UUID.randomUUID().toString();
      params += "&timestamp=" + timestamp;
      params += "&nonce=" + nonce;
      // 由参数列表生成sign
      HashMap<String, String> signParam = new HashMap<String, String>();
      signParam.put("serviceNodeId", serviceNodeId);
//      signParam.put("method", "callAPI_to_array2D");
      signParam.put("userId", "USR_XIAN_DATA");
      signParam.put("interfaceId", "statSurfPre");
      signParam.put("elements", "Station_ID_C");
      signParam.put("timeRange", "[20190601000000,20190601060000]");
//      signParam.put("orderby", "SUM_PRE_1h:desc");
      signParam.put("dataFormat", "json");
      signParam.put("timestamp", timestamp);
      signParam.put("limitCnt", "10");
      signParam.put("nonce", nonce);
      signParam.put("pwd", "XIANdata369");
      String sign = SignGenUtil.getSign(signParam);
      params += "&sign=" + sign;

      /* 2. 调用接口 */
      RestUtil restUtil = new RestUtil() ;
      String rstData = restUtil.getRestData( params ) ;

      /* 3.  输出结果 */
      FormatUtil formatUtil = new FormatUtil() ;
      formatUtil.outputRstJson( rstData ) ;
   }
}
