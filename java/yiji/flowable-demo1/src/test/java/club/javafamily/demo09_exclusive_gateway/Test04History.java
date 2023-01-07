package club.javafamily.demo09_exclusive_gateway;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Jack Li
 * @date 2022/10/11 下午4:21
 * @description
 */
public class Test04History {

   ProcessEngine processEngine;

   @BeforeEach
   public void init() {
      processEngine = ProcessEngines.getDefaultProcessEngine();
   }

   @Test
   void testQueryHistory() {
      final HistoryService historyService = processEngine.getHistoryService();

      final List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
         .processDefinitionId("holiday-candidate:1:4")
         .finished() // 查询的历史记录的状态是已经完成的
         .orderByHistoricActivityInstanceEndTime().asc()
         .list();

      for (HistoricActivityInstance history : list) {
         System.out.println(history.getActivityId());
         System.out.println(history.getAssignee());
         System.out.println(history.getActivityName());
         System.out.println(history.getDurationInMillis());

         System.out.println("-------------------");
      }
   }

}
