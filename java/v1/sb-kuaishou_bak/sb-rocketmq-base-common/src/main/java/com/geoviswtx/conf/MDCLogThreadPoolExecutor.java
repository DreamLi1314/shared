package com.geoviswtx.conf;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author Jack Li
 * @date 2022/7/2 下午3:17
 * @description MDC 的坑
 * Q: 主线程中，如果使用了线程池，会导致线程池中丢失 MDC 信息；
 * 解决办法：需要我们自己重写线程池，在调用线程跳动run之前，获取到主线程的MDC信息，重新put到子线程中的。
 */
public class MDCLogThreadPoolExecutor extends ThreadPoolExecutor {

   public MDCLogThreadPoolExecutor(int corePoolSize,
                                   int maximumPoolSize,
                                   long keepAliveTime,
                                   TimeUnit unit,
                                   BlockingQueue<Runnable> workQueue)
   {
      super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
   }

   @Override
   public void execute(Runnable command) {
      super.execute(MDCLogThreadPoolExecutor.executeRunnable(
          command, MDC.getCopyOfContextMap()));
   }

   @Override
   public Future<?> submit(Runnable task) {
      return super.submit(MDCLogThreadPoolExecutor.executeRunnable(
         task, MDC.getCopyOfContextMap()));
   }

   @Override
   public <T> Future<T> submit(Callable<T> callable) {
      return super.submit(MDCLogThreadPoolExecutor.submitCallable(
         callable,MDC.getCopyOfContextMap()));
   }

   public static Runnable executeRunnable(Runnable runnable,
                                          Map<String,String> mdcContext)
   {
      return () -> {
         if (mdcContext == null) {
            MDC.clear();
         } else {
            MDC.setContextMap(mdcContext);
         }

         try {
            runnable.run();
         } finally {
            MDC.clear();
         }
      };
   }

   private static <T> Callable<T> submitCallable(
      Callable<T> callable,
      Map<String, String> context)
   {
      return () -> {
         if (context == null) {
            MDC.clear();
         } else {
            MDC.setContextMap(context);
         }

         try {
            return callable.call();
         } finally {
            MDC.clear();
         }
      };
   }


}
