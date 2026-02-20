package com.mlog.blog.tasks;

import com.mlog.blog.entity.BlogCountDto;
import com.mlog.blog.service.IBlogStatisticService;
import com.mlog.blog.service.impl.DingTalkNotifyService;
import com.mlog.utils.cma.enums.TimeTypeEnum;
import com.mlog.utils.date.DateUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jack Li
 * @date 2022/2/5 10:17 下午
 * @description
 */
@Component
public class StatisticalJobHandler {

   @Value("${mlog.dingtalk.webhook.blog-url}")
   private String blogUrl;

   private static final String DingTalkNotifyMsg = "(${unit}度):\n" +
      "    ${unit}度博客之星: ${blogStar}\n" +
      "    统计时间: ${startTime}--至今\n" +
      "    博客统计: 本${unit}博文共计 ${totalCount} 篇${typeCountMsg}\n" +
      "    详情请看: ${blogUrl}";

   private static final String DingTalkPromptMsg = "-月中提醒服务:\n" +
      "    提醒服务: 本月已过半, 目前博文共计发布 ${totalCount} 篇${typeCountMsg}, 还没完成的小哥哥小姐姐尽快准备咯, 感谢大家的分享!\n" +
      "    详情请看: ${blogUrl}";

   private final IBlogStatisticService blogStatisticService;
   private final DingTalkNotifyService notifyService;

   public StatisticalJobHandler(IBlogStatisticService blogStatisticService,
                                DingTalkNotifyService notifyService)
   {
      this.blogStatisticService = blogStatisticService;
      this.notifyService = notifyService;
   }

   @XxlJob("monthPromptJobHandler")
   public void monthPromptJobHandler() throws Exception {
      final Instant startInstant = Instant.now();
      XxlJobHelper.log("Start monthPromptJobHandler at {}", startInstant);
      TimeTypeEnum timeTypeEnum = TimeTypeEnum.MONTH;

      try{
         Map<String, String> values = buildCountValuesMap(timeTypeEnum);

         final String content
            = StringSubstitutor.replace(DingTalkPromptMsg, values);

         notifyService.notifyText(content);
      }
      finally {
         XxlJobHelper.log("Completed monthPromptJobHandler. make {} s",
            startInstant.until(Instant.now(), ChronoUnit.MILLIS));
      }
   }

   @XxlJob("statisticalJobHandler")
   public void statisticalJobHandler() throws Exception {
      final Instant startInstant = Instant.now();
      XxlJobHelper.log("Start statisticalJobHandler at {}", startInstant);
      final String jobParam = XxlJobHelper.getJobParam();
      TimeTypeEnum timeTypeEnum = TimeTypeEnum.MONTH;

      try{
         if(StringUtils.hasText(jobParam)) {
            XxlJobHelper.log("Job param is: ", jobParam);
            timeTypeEnum = TimeTypeEnum.valueOf(jobParam);
         }

         Map<String, String> values = buildCountValuesMap(timeTypeEnum);

         final String content
            = StringSubstitutor.replace(DingTalkNotifyMsg, values);

         notifyService.notifyText(content);
      }
      finally {
         XxlJobHelper.log("Completed statisticalJobHandler. make {} s",
            startInstant.until(Instant.now(), ChronoUnit.MILLIS));
      }
   }

   private Map<String, String> buildCountValuesMap(TimeTypeEnum timeTypeEnum) {
      Date startTime = getStartDate(timeTypeEnum);

      String blogStar = "暂无博文发表(请相关责任人及时提醒团队成员!!!)";
      Integer totalCount = 0;

      final List<BlogCountDto> countDtos
         = blogStatisticService.findCountByCreateTime(startTime);

      if(!CollectionUtils.isEmpty(countDtos)) {
         int maxCount = countDtos.stream()
            .map(BlogCountDto::getCount)
            .max(Comparator.naturalOrder())
            .orElse(0);
         List<String> blogStarList = new ArrayList<>();

         for (BlogCountDto countDto : countDtos) {
            totalCount += countDto.getCount();

            if(countDto.getCount() == maxCount) {
               blogStarList.add(countDto.getNickname());
            }
         }

         if(blogStarList.size() < 3) {
            blogStar = String.join(",", blogStarList);
         }
         else {
            final List<String> subList = blogStarList.subList(0, 3);
            subList.add("...");
            blogStar = String.join(",", subList);
         }
      }

      Map<String, String> values = new HashMap<>();

      values.put("unit", getUnitString(timeTypeEnum));
      values.put("blogStar", blogStar);
      values.put("startTime", DateUtil.formatOnlyNormalDate(startTime));
      values.put("totalCount", totalCount + "");
      values.put("blogUrl", blogUrl + getUnitIndex(timeTypeEnum));

      final List<BlogCountDto> typeCountDtos
         = blogStatisticService.findTypeCountByCreateTime(startTime);
      String typeCountMsg = "";

      if(!CollectionUtils.isEmpty(typeCountDtos)) {
         typeCountMsg += ", 其中 ";

         typeCountMsg += typeCountDtos.stream()
            .map(typeCountDto -> typeCountDto.getNickname()
               + ": " + typeCountDto.getCount() + " 篇")
            .collect(Collectors.joining(", "));
      }

      values.put("typeCountMsg", typeCountMsg);
      return values;
   }

   private Date getStartDate(TimeTypeEnum timeTypeEnum) {
      Date now = new Date();

      switch (timeTypeEnum) {
         case QUARTER:
            return DateUtil.getQuarterStartTime(now);
         case YEAR:
            return DateUtil.getYearStartTime(now);
         default:
            return DateUtil.getMonthStartTime(now);
      }
   }

   private String getUnitString(TimeTypeEnum timeTypeEnum) {
      switch (timeTypeEnum) {
         case QUARTER:
            return "季";
         case YEAR:
            return "年";
         default:
            return "月";
      }
   }

   private Integer getUnitIndex(TimeTypeEnum timeTypeEnum) {
      switch (timeTypeEnum) {
         case QUARTER:
            return 2;
         case YEAR:
            return 3;
         default:
            return 1;
      }
   }
}
