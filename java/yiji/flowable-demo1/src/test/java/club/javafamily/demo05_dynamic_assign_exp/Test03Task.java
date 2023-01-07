package club.javafamily.demo05_dynamic_assign_exp;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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
         .processDefinitionKey("business-trip")
         .taskAssignee("部门经理") // 查询指定处理人的所有任务
         .list();

      for (Task task : list) {
         // business-trip:1:52504
         System.out.println(task.getProcessDefinitionId());
         // 创建请假流程
         System.out.println(task.getName());
         // 普通员工
         System.out.println(task.getAssignee());
         // null
         System.out.println(task.getDescription());
         // 55009
         System.out.println(task.getId());

         System.out.println("---------------------");
      }
   }

   @Test
   void testCompleteTask() {
      final ProcessEngine processEngine = configuration.buildProcessEngine();

      final TaskService taskService = processEngine.getTaskService();

      final Task task = taskService.createTaskQuery()
         .processDefinitionKey("business-trip")
         .taskAssignee("普通员工 2 号") // 查询指定处理人的所有任务
         .singleResult();

      final Map<String, Object> variables = task.getProcessVariables();

      variables.put("num", 6);

      taskService.complete(task.getId(), variables, true);
   }

}
