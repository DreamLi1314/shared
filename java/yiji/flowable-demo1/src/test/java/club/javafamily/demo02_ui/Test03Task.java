package club.javafamily.demo02_ui;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author Jack Li
 * @date 2022/10/11 下午4:21
 * @description
 */
public class Test03Task {

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
   void testQueryTask() {
      final ProcessEngine processEngine = configuration.buildProcessEngine();

      final TaskService taskService = processEngine.getTaskService();

      final List<Task> list = taskService.createTaskQuery()
         .processDefinitionKey("MyHolidayUi")
         .taskAssignee("user2") // 查询指定处理人的所有任务
         .list();

      for (Task task : list) {
         // holidayRequest:1:12503
         System.out.println(task.getProcessDefinitionId());
         // 同意或者拒绝请假
         System.out.println(task.getName());
         // liyiyi
         System.out.println(task.getAssignee());
         // null
         System.out.println(task.getDescription());
         // 15008
         System.out.println(task.getId());

         System.out.println("---------------------");
      }
   }

   @Test
   void testRejectTask() {
      final ProcessEngine processEngine = configuration.buildProcessEngine();

      final TaskService taskService = processEngine.getTaskService();

      Map<String, Object> vars = new HashMap<>();

      // 拒绝请假审批
      vars.put("approved", false);

      taskService.complete("30003", vars);
   }

}
