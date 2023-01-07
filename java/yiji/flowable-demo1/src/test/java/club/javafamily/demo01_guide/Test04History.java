package club.javafamily.demo01_guide;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Jack Li
 * @date 2022/10/11 下午4:21
 * @description
 */
public class Test04History {

   ProcessEngineConfiguration configuration;

   @BeforeEach
   public void init() {
      configuration = new StandaloneProcessEngineConfiguration();

      // 配置数据库
      configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
      configuration.setJdbcUsername("root");
      configuration.setJdbcPassword("Yiji0713!");
      configuration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/db_flowable1?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true");

      configuration.setDatabaseSchemaUpdate(
         ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
   }

   @Test
   void testQueryHistory() {
      final ProcessEngine processEngine = configuration.buildProcessEngine();

      final HistoryService historyService = processEngine.getHistoryService();

      final List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
         .processDefinitionId("holidayRequest:1:12503")
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
