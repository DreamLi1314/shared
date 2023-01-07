package com.mlog.yiji.global.util;

import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;

public class ThreadPoolUtil {

   /**
    * Get the optimal pool size for keeping the processors at the desired utilization.
    * @param entriesCount the number of assets need to transform.
    */
   public static int getThreadNumber(int entriesCount) {
      if(entriesCount < 5) {
         return 1;
      }

      int Ncpu = Runtime.getRuntime().availableProcessors();

      return (int) (Ncpu * 0.5 * (1 + entriesCount / 100));
   }

   /**
    * Return the entries list to process in the target thread.
    * @param threadNo  the thread no.
    * @param perCount  the assets number to process in a thread.
    * @param entries   the total assets need to process.
    */
   public static List<SearchHit> getThreadHits(int threadNo, int perCount,
                                           SearchHit[] entries)
   {
      List<SearchHit> list = new ArrayList<>();

      if(threadNo * perCount >= entries.length) {
         return list;
      }

      for(int i = threadNo * perCount, n = 0; i < entries.length && n < perCount; i++, n++) {
         list.add(entries[i]);
      }

      return list;
   }
}
