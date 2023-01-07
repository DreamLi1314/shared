package com.mlog.blog;

import com.alibaba.fastjson.JSONObject;
import com.mlog.blog.service.IBlogStatisticService;
import com.mlog.blog.service.impl.DingTalkNotifyService;
import com.mlog.utils.date.DateUtil;
import org.apache.commons.text.StringSubstitutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.*;

@SpringBootTest
class CropsApplicationTests {

   private static final String DingTalkNotifyMsg = "(${unit}度):\n" +
      "    博客之星(${unit}度): ${blogStar}\n" +
      "    统计时间: ${startTime}--至今\n" +
      "    博客统计: 本${unit}博文共计 ${totalCount} 篇, ${typeCountMsg}\n" +
      "    详情请看: http://localhost:4200/blog-statistic/2";

   @Autowired
   private DingTalkNotifyService notifyService;

   @Autowired
   private IBlogStatisticService blogStatisticService;

   @Test
   void contextLoads() {
      final Date startTime = DateUtil.getYearStartTime(new Date());

      Map<String, String> values = new HashMap<>();

      values.put("unit", "年");
      values.put("blogStar", "李一一");
      values.put("startTime", DateUtil.formatOnlyNormalDate(startTime));
      values.put("totalCount", "6");
      values.put("typeCountMsg", "其中 后端技术: 10 篇, 前端技术: 15 篇, 产品技术: 15 篇");

      final String content
         = StringSubstitutor.replace(DingTalkNotifyMsg, values);

      System.out.println(content);

      final ResponseEntity<JSONObject> responseEntity = notifyService.notifyText(content);

      System.out.println(responseEntity);
   }

}
