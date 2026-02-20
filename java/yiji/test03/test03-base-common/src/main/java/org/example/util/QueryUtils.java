package org.example.util;

import com.mlog.utils.cma.models.DateRange;
import com.mlog.utils.date.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Jack Li
 * @date 2022/7/20 上午10:54
 * @description
 */
public final class QueryUtils {

   /**
    * 查询从今天 00 点开始未来 7 天的时间范围
    * @return DateRange
    */
   public static DateRange queryFuture7Day() {
      final Calendar calendar = DateUtil.getTodayStartCalendar();

      return QueryUtils.queryFuture7Day(calendar.getTime());
   }

   /**
    * 查询未来 7 天的时间范围
    * @return DateRange
    */
   public static DateRange queryFuture7Day(Date startTime) {
      final Calendar calendar = DateUtil.getCalendar(startTime);
      calendar.add(Calendar.DAY_OF_MONTH, 7);
      final Date endTime = calendar.getTime();

      return new DateRange(startTime, endTime);
   }

}
